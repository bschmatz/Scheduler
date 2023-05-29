open module com.example.javafxscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;

    exports com.example.javafxscheduler;
    exports com.example.javafxscheduler.controllers;
    exports com.example.javafxscheduler.entities;
    exports com.example.javafxscheduler.util;

}