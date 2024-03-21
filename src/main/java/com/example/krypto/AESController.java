package com.example.krypto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.BreakIterator;
import java.util.*;

public class AESController implements Initializable {
    @FXML
    private Button saveCipherButton;

    @FXML
    private Button saveTextButton;

    @FXML
    private Button openTextFileButton;

    @FXML
    private Button openCipherButton;

    @FXML
    private Button saveKeyFileButton;

    @FXML
    private Button loadKeyFileButton;

    @FXML
    private Button generateKeyButton;

    @FXML
    private Button encipherButton;

    @FXML
    private Button decipherButton;

    @FXML
    private TextField keyField;

    @FXML
    private TextField saveKeyFileField;

    @FXML
    private TextField loadKeyFileField;

    @FXML
    private TextField textNameField;

    @FXML
    private TextField openCipherFileField;

    @FXML
    private TextField saveTextField;

    @FXML
    private TextField saveCipherField;

    @FXML
    private TextArea textArea;

    @FXML
    private TextArea cipherArea;

    @FXML
    private RadioButton plikRadio;

    @FXML
    private RadioButton textRadio;

    @FXML
    private RadioButton bit128Radio;

    @FXML
    private RadioButton bit192Radio;

    @FXML
    private RadioButton bit256Radio;

    private Stage stage;

    private Path path;




    private byte[][] key = {
            {0x0, 0x1, 0x2, 0x3},
            {0x4, 0x5, 0x6, 0x7},
            {0x8, 0x9, 0xa, 0xb},
            {0xc, 0xd, 0xe, 0xf}
    };
    private int numberOfRounds = 10;

    private byte[] cipher;
    private ArrayList<byte> plainText = new ArrayList<byte>();

    @FXML
    protected void onGenerateKeyButtonClick() throws UnsupportedEncodingException {
        System.out.println("generuje klucz");
        Random rand = new Random();
        int len = 4;
        if (bit256Radio.isSelected()){
            System.out.println("256");
            len = 8;
            numberOfRounds = 14;
        } else if (bit192Radio.isSelected()) {
            System.out.println("192");
            len = 6;
            numberOfRounds = 12;
        }
        else {
            System.out.println("128");
        }
        key = new byte[len][4];
        StringBuilder hexKey = new StringBuilder();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 4; j++) {
                key[i][j] = (byte) rand.nextInt(0x10);
                hexKey.append(String.format("%02x", Byte.parseByte(String.valueOf(key[i][j]))));
            }
        }
        keyField.textProperty().setValue(hexKey.toString());
        System.out.println(Arrays.deepToString(key));

    }

    @FXML
    protected void onsaveCipherButtonClick() {
        final Path path = Path.of(saveCipherField.getText());

        try (
                final BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        ) {
            writer.write(Arrays.toString(cipher));
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    protected void onsaveTextButtonClick() {
        final Path path = Path.of(saveTextField.getText());

        try (
                final BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        ) {
            for (int i = 0; i < plainText.size() ; i++) {
                writer.write(plainText.get(i));
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    protected void onopenTextFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            byte dane=0;
            path = file.toPath();
            textNameField.setText(path.toString());
            String text = "";
            try
            {
                FileInputStream plik = new FileInputStream(path.toFile());
                BufferedInputStream bufor = new BufferedInputStream (plik);
                DataInputStream dana= new DataInputStream (bufor);

                try
                {
                    while (true)
                    {
                        dane=dana.readByte();
                        text+=String.valueOf((char)dane);
                    }

                } catch (EOFException eof)
                {
                    bufor.close();
                } //zamknięcie bufora przez obsługę wyjątku od czytania poza plikiem
                textArea.setText(text);
            }
            catch (IOException e) {
                System.out.println("Blad odczytu pliku bajtowego" + e);
            }

        }


    }

    @FXML
    protected void onopenCipherButtonClick() {
        System.out.println("otwierasz plik z szyfrem");

    }

    @FXML
    protected void onsaveKeyFileButtonClick() {
        System.out.println("zapiszujesz plik z klucze");

    }

    @FXML
    protected void onloadKeyFileButtonClick() {
        System.out.println("otwierasz plik z klucze");

    }

    @FXML
    protected void onencipherButtonClick() {
        System.out.println("szufrun");
        plainText = new ArrayList<byte>();
        if (textRadio.isSelected()){
            for (int i = 0; i < textArea.getText().getBytes().length; i++) {
                plainText.add(textArea.getText().getBytes()[i]) ;
            }

        }
        aesAlgorithm AES = new aesAlgorithm(key, numberOfRounds);
        byte[] interim = new byte[plainText.size()];
        for (int i = 0; i < plainText.size(); i++) {
            interim[i] = plainText.get(i);
        }
        cipher = AES.aesEncryption(interim);
        String cipherStr = new String(bytesToHex(cipher));
        cipherArea.textProperty().setValue(cipherStr);
        System.out.println(cipherStr);


    }

    @FXML
    protected void ondecipherButtonClick() {
        System.out.println("deszyfruj");

        if (textRadio.isSelected()){
            if (cipher.length != 0){
                aesAlgorithm AES = new aesAlgorithm(key, numberOfRounds);
                byte[] interim = AES.aesDecryption(cipher);
                String plainStr = new String(interim);
                textArea.textProperty().setValue(plainStr);
                plainText = new ArrayList<byte>();
                for (int i = 0; i < interim.length; i++) {
                    plainText.add(interim[i]);
                }

            }
        } else {
            //    TODO: PLIK
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        ToggleGroup group1 = new ToggleGroup();
        plikRadio.setToggleGroup(group);
        textRadio.setSelected(true);
        textRadio.setToggleGroup(group);

        bit128Radio.setToggleGroup(group1);
        bit128Radio.setSelected(true);
        bit192Radio.setToggleGroup(group1);
        bit256Radio.setToggleGroup(group1);


    }

    private String bytesToHex(byte[] arr){
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            temp.append(String.format("%02x", Byte.parseByte(String.valueOf(arr[i]))));
        }
        return temp.toString();
    }
}