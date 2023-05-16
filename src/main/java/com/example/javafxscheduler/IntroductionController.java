package com.example.javafxscheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class IntroductionController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;


    public void switchToLogin(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loginCheck(ActionEvent e) throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection established");
            String name = nameField.getText();
            String password = passwordField.getText();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            Thread.sleep(1000);

            while (rs.next()) {
                if (rs.getString("name").equals(name) && rs.getString("password").equals(password)) {
                    System.out.println("Login successful");
                }
            }

        } catch (Exception ex) {
            System.out.println("Connection failed");
            ex.printStackTrace();
        }

    }

}