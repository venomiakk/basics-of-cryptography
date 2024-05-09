package com.example.krypto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DSAController implements Initializable {
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

    private String plainText;
    private String signatureText;

    //private dsaAlgorithm DSA = new dsaAlgorithm();
    private String path = "../";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("inicjalizacja");
        textArea.setWrapText(true);
        dsaAlgorithm obj = new dsaAlgorithm();
        dsaAlgorithm.generateQ();
        dsaAlgorithm.generateP();
        dsaAlgorithm.generateH();
        dsaAlgorithm.generateAandB();
        qAndGField.textProperty().setValue(dsaAlgorithm.q.toString() + dsaAlgorithm.h.toString());
        publicYKeyField.textProperty().setValue(dsaAlgorithm.b.toString());
        privateXKeyField.textProperty().setValue(dsaAlgorithm.a.toString());
        modPField.textProperty().setValue(dsaAlgorithm.p.toString());
        //System.out.println(dsaAlgorithm.p);
        //dsaAlgorithm.signature();
        //dsaAlgorithm.checkSignature();
    }

    @FXML
    protected void onGenerateKeyButtonClick() {
        System.out.println("generuje klucz");
        dsaAlgorithm.generateQ();
        dsaAlgorithm.generateP();
        dsaAlgorithm.generateH();
        dsaAlgorithm.generateAandB();
        qAndGField.textProperty().setValue(dsaAlgorithm.q.toString() + dsaAlgorithm.h.toString());
        publicYKeyField.textProperty().setValue(dsaAlgorithm.b.toString());
        privateXKeyField.textProperty().setValue(dsaAlgorithm.a.toString());
        modPField.textProperty().setValue(dsaAlgorithm.p.toString());
        System.out.println(dsaAlgorithm.q);
    }

    @FXML
    protected void onsaveCipherButtonClick() {
        System.out.println("zapisujesz szyfr");

    }

    @FXML
    protected void onsaveTextButtonClick() {
        System.out.println("zapisujesz tekst");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try( FileWriter writer = new FileWriter(selectedFile);)  {
                if (textArea.textProperty().get() != null && !textArea.textProperty().get().isEmpty()) {
                    writer.write(textArea.getText());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    protected void onopenTextFileButtonClick() throws FileNotFoundException {
        System.out.println("otwierasz plik z tekstem");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            Scanner reader = new Scanner(selectedFile);
            StringBuilder data = new StringBuilder();
            while (reader.hasNextLine()) {
                data.append(reader.nextLine()).append("\n");
            }
            reader.close();
            textArea.textProperty().setValue(data.toString());
        }
    }

    @FXML
    protected void onopenCipherButtonClick() {
        System.out.println("otwierasz plik z szyfrem");

    }

    @FXML
    protected void onsaveKeyFileButtonClick() {
        System.out.println("zapiszujesz plik z klucze");
        dsaKeys keys = new dsaKeys(dsaAlgorithm.q, dsaAlgorithm.h, dsaAlgorithm.a, dsaAlgorithm.b, dsaAlgorithm.p);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
                oos.writeObject(keys);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    protected void onloadKeyFileButtonClick() {
        System.out.println("otwierasz plik z klucze");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
                dsaKeys keys = (dsaKeys) ois.readObject();
                dsaAlgorithm.p = keys.modp;
                dsaAlgorithm.h = keys.h;
                dsaAlgorithm.q = keys.q;
                dsaAlgorithm.a = keys.a;
                dsaAlgorithm.b = keys.b;
                qAndGField.textProperty().setValue(dsaAlgorithm.q.toString() + dsaAlgorithm.h.toString());
                publicYKeyField.textProperty().setValue(dsaAlgorithm.b.toString());
                privateXKeyField.textProperty().setValue(dsaAlgorithm.a.toString());
                modPField.textProperty().setValue(dsaAlgorithm.p.toString());

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void onencipherButtonClick() {
        System.out.println("podpis");
        if (textRadio.isSelected()) {
            System.out.println("tekst");
            plainText = textArea.getText();
            System.out.println(plainText);
            dsaAlgorithm.signatureText(plainText);
            System.out.println(dsaAlgorithm.s1);
            System.out.println(dsaAlgorithm.s2);
            //TODO i co wstawic w podpis? ktore to?
        } else {
            System.out.println("plik");
        }


    }

    @FXML
    protected void ondecipherButtonClick() {
        System.out.println("weryfikacja");
        if (textRadio.isSelected()) {
            System.out.println("tekst");
            plainText = textArea.getText();
            dsaAlgorithm.checkSignatureText(plainText);
            if (dsaAlgorithm.t.compareTo(dsaAlgorithm.s1) == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Dane są zgodne");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Dane nie są zgoden!");
                alert.showAndWait();
            }
        } else {
            System.out.println("plik");
        }
        
    }

    //po co to tu jest??
    public BigInteger generateP() {
        Random ran = new Random();
        int l = ran.nextInt(512, 1024);
        while (l % 64 != 0) {
            l = ran.nextInt(512, 1024);
        }
        return BigInteger.probablePrime(l, new Random());
    }

    public BigInteger generateQ(BigInteger p) {
        Random ran = new Random();
        BigInteger q = new BigInteger(160, new Random());
        while ((p.intValue() - 1) % q.intValue() != 0) {
            q = new BigInteger(160, new Random());
        }
        return q;
    }
}
