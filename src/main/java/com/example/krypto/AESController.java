package com.example.krypto;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.UnsupportedEncodingException;

public class AESController {
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
}