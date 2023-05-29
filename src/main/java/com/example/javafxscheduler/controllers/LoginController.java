//LoginController.java
//Represents the controller for the login view. It is responsible for checking the login credentials and switching to the main view.
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.User;
import com.example.javafxscheduler.util.UserUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private User user;
    private ArrayList<User> userList;

    public void initialize() {
        userList = UserUtil.getAllUsers();
    }

    //Checks the login credentials and switches to the main view.
    public void loginCheck(ActionEvent e) throws IOException {

        String name = nameField.getText();
        String password = passwordField.getText();

        for (User u : userList) {
            if (u.getName().equals(name) && u.getPassword().equals(password)) {
                user = u;
            }
        }

        if (user != null) {
            System.out.println("Login successful");
            switch (user.getRole()) {
                case "Admin":
                    System.out.println("Admin logged in");
                    switchToAdminView(e);
                    return;
                case "Assistant":
                    System.out.println("Assistant logged in");
                    switchToAssistantView(e);
                    return;
                case "Student":
                    System.out.println("Student logged in");
                    switchToUserView(e);
                    return;
                default:
                    System.out.println("No valid role found");
                    break;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login failed");
            alert.setContentText("Please check your credentials");
            alert.showAndWait();
        }

    }

    public void switchToIntroduction(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Introduction.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Welcome!");
        stage.show();
    }

    public void switchToUserView(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
        root = loader.load();

        UserViewController userView = loader.getController();
        userView.setCurrentUser(user);

        //root = FXMLLoader.load(getClass().getResource("UserView.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MySchedule");
        stage.centerOnScreen();
        stage.show();
    }

    public void switchToAdminView(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
        root = loader.load();

        AdminViewController adminViewController = loader.getController();
        adminViewController.setUser(user);

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("AdminView");
        stage.centerOnScreen();
        stage.show();
    }

    public void switchToAssistantView(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AssistantView.fxml"));
        root = loader.load();

        AssistantViewController assistantViewController = loader.getController();
        assistantViewController.setUser(user);

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("AssistantView");
        stage.centerOnScreen();
        stage.show();
    }
}
