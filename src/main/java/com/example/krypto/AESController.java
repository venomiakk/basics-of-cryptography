package com.example.krypto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

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

    private byte[][] key = {
            {0x0, 0x1, 0x2, 0x3},
            {0x4, 0x5, 0x6, 0x7},
            {0x8, 0x9, 0xa, 0xb},
            {0xc, 0xd, 0xe, 0xf}
    };
    private int numberOfRounds = 10;
    private byte[] bytes;

    private byte[] cipher;
    private byte[] plainText;

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
        System.out.println("zapisujesz szyfr");

    }

    @FXML
    protected void onsaveTextButtonClick() {
        System.out.println("zapisujesz tekst");

    }

    @FXML
    protected void onopenTextFileButtonClick() {
        System.out.println("otwierasz plik z tekstem");

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

        if (textRadio.isSelected()){
            String message = textArea.textProperty().get();
            plainText = message.getBytes();
            aesAlgorithm AES = new aesAlgorithm(key, numberOfRounds);
            cipher = AES.aesEncryption(plainText);
            String cipherStr = new String(bytesToHex(cipher));
            cipherArea.textProperty().setValue(cipherStr);
            System.out.println(Arrays.toString(cipher));
            System.out.println(cipherStr);
        } else {
        //    TODO: PLIK
        }

        //System.out.println(Arrays.toString(bytes));

    }

    @FXML
    protected void ondecipherButtonClick() {
        System.out.println("deszyfruj");

        if (textRadio.isSelected()){
            if (cipher.length != 0){
                aesAlgorithm AES = new aesAlgorithm(key, numberOfRounds);
                plainText = AES.aesDecryption(cipher);
                String plainStr = new String(plainText);
                textArea.textProperty().setValue(plainStr);
                System.out.println(Arrays.toString(plainText));
                System.out.println(plainStr);
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