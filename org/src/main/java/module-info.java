module com.example.org {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.org to javafx.fxml;
    exports com.example.org;
}