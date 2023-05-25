open module com.example.javafxscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires jakarta.persistence;
    requires java.naming;

    exports com.example.javafxscheduler;
    exports com.example.javafxscheduler.controllers;

}