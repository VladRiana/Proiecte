module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}