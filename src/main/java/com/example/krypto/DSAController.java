package com.example.krypto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

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
        dsaSignatures sigs = new dsaSignatures(dsaAlgorithm.s1, dsaAlgorithm.s2);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
                oos.writeObject(sigs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onsaveTextButtonClick() {
        System.out.println("zapisujesz tekst");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            if (textRadio.isSelected()) {
                try (FileWriter writer = new FileWriter(selectedFile);) {
                    if (textArea.textProperty().get() != null && !textArea.textProperty().get().isEmpty()) {
                        writer.write(textArea.getText());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try (FileOutputStream fos = new FileOutputStream(selectedFile)) {
                    List<Byte> fileBytes = new ArrayList<>();
                    String txt = textArea.getText();
                    for (int i = 0; i < txt.length(); i += 3) {
                        String substr = txt.substring(i, Math.min(i + 3, txt.length()));
                        fileBytes.add((byte) Integer.parseInt(substr));
                    }
                    byte[] output = new byte[fileBytes.size()];
                    for (int i = 0; i < fileBytes.size(); i++) {
                        output[i] = fileBytes.get(i);
                    }
                    fos.write(output);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @FXML
    protected void onopenTextFileButtonClick() throws FileNotFoundException {
        System.out.println("otwierasz plik z tekstem");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            if (textRadio.isSelected()) {
                Scanner reader = new Scanner(selectedFile);
                StringBuilder data = new StringBuilder();
                while (reader.hasNextLine()) {
                    data.append(reader.nextLine()).append("\n");
                }
                reader.close();
                textArea.textProperty().setValue(data.toString());
            } else {
                try (FileInputStream fis = new FileInputStream(selectedFile)) {
                    byte[] fileBytes = new byte[(int) selectedFile.length()];
                    int[] fileInts = new int[(int) selectedFile.length()];
                    fis.read(fileBytes);
                    System.out.println(Arrays.toString(fileBytes));
                    for (int i = 0; i < fileBytes.length; i++) {
                        fileInts[i] = fileBytes[i] & 0xFF;
                    }

                    StringBuilder tmp = new StringBuilder();
                    for (int i = 0; i < fileInts.length; i++) {
                        tmp.append(String.format("%3d", fileInts[i]));
                    }
                    textArea.setText(tmp.toString().replaceAll("\\s", "0"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    protected void onopenCipherButtonClick() {
        System.out.println("otwierasz plik z szyfrem");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile))) {
                dsaSignatures sigs = (dsaSignatures) ois.readObject();
                dsaAlgorithm.s1 = sigs.s1;
                dsaAlgorithm.s2 = sigs.s2;
                cipherArea.textProperty().setValue(String.valueOf(dsaAlgorithm.s1) + '\n' + String.valueOf(dsaAlgorithm.s2));

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
        plainText = textArea.getText();
        System.out.println(plainText);
        dsaAlgorithm.signatureText(plainText);
        System.out.println(dsaAlgorithm.s1);
        System.out.println(dsaAlgorithm.s2);

        cipherArea.textProperty().setValue(String.valueOf(dsaAlgorithm.s1) + '\n' + String.valueOf(dsaAlgorithm.s2));

    }

    @FXML
    protected void ondecipherButtonClick() {
        System.out.println("weryfikacja");
        String[] sigs = cipherArea.getText().split("\n");
        dsaAlgorithm.s1 = new BigInteger(sigs[0]);
        dsaAlgorithm.s2 = new BigInteger(sigs[1]);
        System.out.println(dsaAlgorithm.s1);
        System.out.println(dsaAlgorithm.s2);

        plainText = textArea.getText();
        dsaAlgorithm.checkSignatureText(plainText);
        if (dsaAlgorithm.t.compareTo(dsaAlgorithm.s1) == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Dane są zgodne");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Dane nie są zgodne!");
            alert.showAndWait();
        }


    }

}
