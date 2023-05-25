//LoginController.java
//Represents the controller for the login view. It is responsible for checking the login credentials and switching to the main view.
//Author: Benedikt Schmatz
//Last changed: 23.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.User;
import com.example.javafxscheduler.util.UserUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void loginCheck(ActionEvent e) throws IOException {

        String name = nameField.getText();
        String password = passwordField.getText();

        for (User currentUser : userList) {
            if (currentUser.getName().equals(name) && currentUser.getPassword().equals(password)) {
                user = currentUser;
                break;
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
                    System.out.println("Student logged in");;
                    switchToUserAgenda(e);
                    return;
                default:
                    System.out.println("No role found");
                    break;
            }
        } else {
            System.out.println("Login failed");
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

    public void switchToUserAgenda(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
        root = loader.load();

        UserViewController userView = loader.getController();
        userView.setCurrentUser(user);

        //root = FXMLLoader.load(getClass().getResource("UserView.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MySchedule");
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
        stage.show();
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
