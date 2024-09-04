module com.example.org {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.org to javafx.fxml;
    exports com.example.org;
}