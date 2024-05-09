package com.example.krypto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class DSAController implements Initializable
{
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
    private TextField qAndGField;

    @FXML
    private TextField publicYKeyField;

    @FXML
    private TextField privateXKeyField;

    @FXML
    private TextField modPField;

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

    private dsaAlgorithm DSA = new dsaAlgorithm();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("inicjalizacja");
        
    }

    @FXML
    protected void onGenerateKeyButtonClick() {
        System.out.println("generuje klucz");

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
        System.out.println("podpis");
        if (textRadio.isSelected()){
            System.out.println("tekst");
        }
        else {
            System.out.println("plik");
        }

    }

    @FXML
    protected void ondecipherButtonClick() {
        System.out.println("weryfikacja");
        if (textRadio.isSelected()){
            System.out.println("tekst");
        }
        else {
            System.out.println("plik");
        }

    }

    public BigInteger generateP()
    {
        Random ran = new Random();
        int l = ran.nextInt(512,1024);
        while( l % 64 != 0)
        {
            l = ran.nextInt(512,1024);
        }
        return BigInteger.probablePrime(l, new Random());
    }

    public BigInteger generateQ(BigInteger p)
    {
        Random ran = new Random();
        BigInteger q = new BigInteger(160, new Random());
        while ((p.intValue()-1) % q.intValue()!=0)
        {
            q = new BigInteger(160, new Random());
        }
        return q;
    }
}
