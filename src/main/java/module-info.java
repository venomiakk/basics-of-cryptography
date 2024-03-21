module com.example.krypto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;


    opens com.example.krypto to javafx.fxml;
    exports com.example.krypto;
}