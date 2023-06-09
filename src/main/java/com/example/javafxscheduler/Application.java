//Application.java
//Represents the entry point of the application.
//Author: Benedikt Schmatz
//Last changed: 29.05.2023

package com.example.javafxscheduler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("controllers/Introduction.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}