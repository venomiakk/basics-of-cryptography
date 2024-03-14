package com.example.krypto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.UnsupportedEncodingException;
import java.net.URL;
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



    @FXML
    protected void onGenerateKeyButtonClick() throws UnsupportedEncodingException {
        System.out.println("generuje klucz");
        aesAlgorithm AES = new aesAlgorithm();

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

    }

    @FXML
    protected void ondecipherButtonClick() {
        System.out.println("deszyfruj");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        ToggleGroup group1 = new ToggleGroup();
        plikRadio.setToggleGroup(group);
        plikRadio.setSelected(true);
        textRadio.setToggleGroup(group);

        bit128Radio.setToggleGroup(group1);
        bit192Radio.setToggleGroup(group1);
        bit256Radio.setToggleGroup(group1);
    }
}