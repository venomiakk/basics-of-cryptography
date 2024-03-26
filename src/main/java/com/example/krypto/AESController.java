package com.example.krypto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javax.xml.bind.DatatypeConverter;

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
    private TextField saveTextFie;

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

    @FXML
    private Button useKeyBtn;

    private byte[][] key = {
            {0x0, 0x1, 0x2, 0x3},
            {0x4, 0x5, 0x6, 0x7},
            {0x8, 0x9, 0xa, 0xb},
            {0xc, 0xd, 0xe, 0xf}
    };
    private int numberOfRounds = 10;
    private int len = 4;
    private byte[] bytes;

    private byte[] cipher;
    private byte[] plainText;

    private String path = "C:\\Users\\Adrian\\Desktop\\krypto\\pliki";

    @FXML
    protected void onGenerateKeyButtonClick() throws UnsupportedEncodingException {
        System.out.println("generuje klucz");
        Random rand = new Random();
        if (bit256Radio.isSelected()) {
            System.out.println("256");
            len = 8;
            numberOfRounds = 14;
        } else if (bit192Radio.isSelected()) {
            System.out.println("192");
            len = 6;
            numberOfRounds = 12;
        } else {
            System.out.println("128");
            len = 4;
            numberOfRounds = 10;
        }
        key = new byte[len][4];
        StringBuilder hexKey = new StringBuilder();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 4; j++) {
                key[i][j] = (byte) rand.nextInt(0x10);
                hexKey.append(String.format("%02x", Byte.parseByte(String.valueOf(key[i][j]))));
            }
        }
        keyField.textProperty().setValue(hexKey.toString().replaceAll("\\s", ""));
        //System.out.println(Arrays.deepToString(new StringBuilder[]{hexKey}));

    }

    @FXML
    protected void onsaveCipherButtonClick() {
        System.out.println("zapisujesz szyfr");
        //TODO: Zapisywanie pliku zaszyfrowanego
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try (FileOutputStream fos = new FileOutputStream(selectedFile)) {
                if (cipher != null && cipher.length > 0) {
                    fos.write(cipher);

                    System.out.println(Arrays.toString(cipher));
                    System.out.println(cipher.length);

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void onsaveTextButtonClick() {
        System.out.println("zapisujesz tekst");
        //TODO: Zapisywanie pliku z plaintextem
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try (FileOutputStream fos = new FileOutputStream(selectedFile)) {
                if (plainText != null && plainText.length > 0) {
                    fos.write(plainText);

                    System.out.println(Arrays.toString(plainText));
                    System.out.println(plainText.length);

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void onopenTextFileButtonClick() {
        System.out.println("otwierasz plik z tekstem");
        FileChooser fileChooser = new FileChooser();
        //TODO: Zmienić ścieżkę
        fileChooser.setInitialDirectory(new File("C:\\Users\\Adrian\\Desktop\\krypto\\pliki"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println(selectedFile);
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
                plainText = new byte[(int) selectedFile.length()];
                fis.read(plainText);

                System.out.println(Arrays.toString(plainText));
                System.out.println(plainText.length);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            StringBuilder tmp = new StringBuilder();
            for (int i = 0; i < plainText.length; i++) {
                tmp.append((char) plainText[i]);
            }
            textArea.setText(tmp.toString());
        }
    }

    @FXML
    protected void onopenCipherButtonClick() {
        System.out.println("otwierasz plik z szyfrem");
        //TODO: Wczytywanie pliku zaszyfrowanego
        FileChooser fileChooser = new FileChooser();
        //TODO: Zmienić ścieżkę
        fileChooser.setInitialDirectory(new File("C:\\Users\\Adrian\\Desktop\\krypto\\pliki"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println(selectedFile);
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
                cipher = new byte[(int) selectedFile.length()];
                fis.read(cipher);

                System.out.println(Arrays.toString(cipher));
                System.out.println(cipher.length);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            StringBuilder tmp = new StringBuilder();
            for (int i = 0; i < cipher.length; i++) {
                tmp.append(String.format("%2x", Byte.parseByte(String.valueOf(cipher[i]))));
            }
            cipherArea.setText(tmp.toString().replaceAll("\\s", ""));
        }
    }

    @FXML
    protected void onsaveKeyFileButtonClick() {
        System.out.println("zapiszujesz plik z kluczem");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try (FileOutputStream fos = new FileOutputStream(selectedFile)) {
                if (key != null && key.length > 0) {
                    byte[] tmpKey = new byte[key.length * key[0].length];
                    //System.out.println(key.length * key[0].length);
                    int k = 0;
                    for (int i = 0; i < key.length; i++) {
                        for (int j = 0; j < key[0].length; j++) {
                            tmpKey[k++] = key[i][j];
                        }
                    }
                    System.out.println(Arrays.toString(tmpKey));
                    fos.write(tmpKey);

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    protected void onloadKeyFileButtonClick() {
        System.out.println("otwierasz plik z klucze");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Adrian\\Desktop\\krypto\\pliki"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println(selectedFile);
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
                key = new byte[(int) selectedFile.length() / 4][4];
                byte[] tmpKey = new byte[(int) selectedFile.length()];
                fis.read(tmpKey);

                if (tmpKey.length == 32){
                    len = 8;
                    numberOfRounds = 14;
                    bit256Radio.setSelected(true);
                } else if (tmpKey.length == 24) {
                    len = 6;
                    numberOfRounds = 12;
                    bit192Radio.setSelected(true);
                } else if (tmpKey.length == 16){
                    len = 4;
                    numberOfRounds = 10;
                    bit128Radio.setSelected(true);
                } else {
                    System.out.println("Niepoprawny klucz");
                    throw new Exception();
                }

                int k = 0;
                for (int i = 0; i < key.length; i++) {
                    for (int j = 0; j < key[0].length; j++) {
                        key[i][j] = tmpKey[k++];
                    }
                }
                System.out.println(Arrays.toString(tmpKey));
                System.out.println(Arrays.deepToString(key));
                StringBuilder showKey = new StringBuilder();
                for (int i = 0; i < tmpKey.length; i++) {
                    showKey.append(String.format("%02x", Byte.parseByte(String.valueOf(tmpKey[i]))));
                }
                keyField.textProperty().setValue(String.valueOf(showKey).replaceAll("\\s", ""));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    protected void onencipherButtonClick() {
        System.out.println("Szyfrowanie");

        if (textRadio.isSelected()) {
            String message = textArea.textProperty().get();
            plainText = message.getBytes();

            aesAlgorithm AES = new aesAlgorithm(key, numberOfRounds);
            cipher = AES.aesEncryption(plainText);
            String cipherStr = new String(bytesToHex(cipher));
            cipherArea.textProperty().setValue(cipherStr);

            System.out.println(plainText.length + " -> " + cipher.length);
            //System.out.println(Arrays.toString(cipher));
            //System.out.println(cipherStr);
        } else {
            if (plainText == null || plainText.length == 0) {
                System.out.println("Nie wczytano pliku");
            } else {
                aesAlgorithm AES = new aesAlgorithm(key, numberOfRounds);
                cipher = AES.aesEncryption(plainText);
                String cipherStr = new String(bytesToHex(cipher));
                cipherArea.textProperty().setValue(cipherStr);

                System.out.println(plainText.length + " -> " + cipher.length);
            }
        }

        //System.out.println(Arrays.toString(bytes));

    }

    @FXML
    protected void ondecipherButtonClick() {
        System.out.println("Deszyfrowanie");

        if (textRadio.isSelected()) {
            String message = cipherArea.textProperty().get();
            cipher = DatatypeConverter.parseHexBinary(message);

            System.out.println(Arrays.toString(cipher));
            aesAlgorithm AES = new aesAlgorithm(key, numberOfRounds);
            plainText = AES.aesDecryption(cipher);
            String plainStr = new String(plainText);
            textArea.textProperty().setValue(plainStr);

            System.out.println(cipher.length + " -> " + plainText.length);
            //System.out.println(Arrays.toString(plainText));
            //System.out.println(plainStr);

        } else {
            if (cipher == null || cipher.length == 0) {
                System.out.println("Nie wczytano pliku");
            } else {
                aesAlgorithm AES = new aesAlgorithm(key, numberOfRounds);
                plainText = AES.aesDecryption(cipher);
                String plainStr = new String(plainText);
                textArea.textProperty().setValue(plainStr);

                System.out.println(cipher.length + " -> " + plainText.length);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        ToggleGroup group1 = new ToggleGroup();

        textArea.setWrapText(true);

        plikRadio.setToggleGroup(group);
        textRadio.setSelected(true);
        textRadio.setToggleGroup(group);

        bit128Radio.setToggleGroup(group1);
        bit128Radio.setSelected(true);
        bit192Radio.setToggleGroup(group1);
        bit256Radio.setToggleGroup(group1);

        StringBuilder basicKey = new StringBuilder();
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key[0].length; j++) {
                basicKey.append(String.format("%02x", Byte.parseByte(String.valueOf(key[i][j]))));
            }
        }
        keyField.textProperty().setValue(String.valueOf(basicKey).replaceAll("\\s", ""));

    }

    private String bytesToHex(byte[] arr) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            temp.append(String.format("%02x", Byte.parseByte(String.valueOf(arr[i]))));
        }
        return temp.toString();
    }

    @FXML
    private void onUseKeyBtn() {
        //TODO: Wyjątki na nierówne długości klucza
        if (bit256Radio.isSelected()) {
            System.out.println("256");
            len = 8;
            numberOfRounds = 14;
        } else if (bit192Radio.isSelected()) {
            System.out.println("192");
            len = 6;
            numberOfRounds = 12;
        } else {
            System.out.println("128");
            len = 4;
            numberOfRounds = 10;
        }
        key = new byte[len][4];
        byte[] tempKey = DatatypeConverter.parseHexBinary(keyField.textProperty().get());
        int k = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 4; j++) {
                key[i][j] = tempKey[k++];
            }
        }
        System.out.println("Zapisany klucz");
        System.out.println(Arrays.deepToString(key));
    }
}