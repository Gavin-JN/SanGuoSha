module com.example.org {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
    requires javafx.media;
    requires org.json;

    opens com.example.org.controller to javafx.fxml;  // 这行代码允许 javafx.fxml 模块访问控制器类
    opens com.example.org to javafx.fxml;
    exports com.example.org;

    // 导出包以允许 JavaFX 访问
    exports com.example.org.client;

    // 如果你使用了 FXML，还需要开放给 JavaFX
    opens com.example.org.client to javafx.fxml;
}