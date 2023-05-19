//RegisterController.java
//Represents the controller for the register view. It is responsible for checking the register credentials and switching back to the login view.
//It also adds the new user to the database.
//Author: Benedikt Schmatz
//Last changed: 19.05.2023

package com.example.javafxscheduler;

import com.example.javafxscheduler.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.tool.schema.Action;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField mailField;
    @FXML
    private TextField passwordField;
    @FXML
    private ChoiceBox roleMenu;
    ObservableList<String> roleList = FXCollections.observableArrayList("Admin", "Assistant", "Student");

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize() {
        roleMenu.setItems(roleList);
    }

    public void register() {
        if (nameField.getText().isEmpty() || mailField.getText().isEmpty() || passwordField.getText().isEmpty() || roleMenu.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not all fields filled out");
            alert.setContentText("Please fill out all fields before submitting");
            alert.showAndWait();
            return;
        }

        String name = nameField.getText();
        String mail = mailField.getText();
        String password = passwordField.getText();
        String role = roleMenu.getValue().toString();
        System.out.println(name + " " + mail + " " + password + " " + role);

        User user = new User(name, mail, password, role);
        user.save();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registration successful");
        alert.showAndWait();

    }

    public void switchToIntroduction(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Introduction.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Welcome!");
        stage.show();
    }

}
