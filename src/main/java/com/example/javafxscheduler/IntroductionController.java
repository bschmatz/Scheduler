//IntroductionController.java
//Represents the controller of the introduction view. It is responsible for switching to the login and register view.
//Author: Benedikt Schmatz
//Last changed: 23.05.2023

package com.example.javafxscheduler;

import com.example.javafxscheduler.entities.User;
import com.example.javafxscheduler.util.UserUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class IntroductionController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList<User> userList = new ArrayList<User>();

    public void initialize() {
        userList = UserUtil.getAllUsers();
    }

    public void switchToLogin(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setUserList(userList);

        //root = FXMLLoader.load(getClass().getResource("UserView.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public void switchToRegister(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();
    }



}