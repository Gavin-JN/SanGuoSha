module com.example.org {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
    requires javafx.media;

    opens com.example.org.controller to javafx.fxml;  // 这行代码允许 javafx.fxml 模块访问控制器类
    opens com.example.org to javafx.fxml;
    exports com.example.org;
}