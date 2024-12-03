module com.example.gamestoremanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;

    opens com.example.gamestoremanagement to javafx.fxml;
    exports com.example.gamestoremanagement;
    exports com.example.gamestoremanagement.controller;
    opens com.example.gamestoremanagement.controller to javafx.fxml;
    exports com.example.gamestoremanagement.model;
    opens com.example.gamestoremanagement.model to javafx.fxml;
    exports com.example.gamestoremanagement.dao;
    opens com.example.gamestoremanagement.dao to javafx.fxml;
}