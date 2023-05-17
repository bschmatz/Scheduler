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
        String name = nameField.getText();
        String mail = mailField.getText();
        String password = passwordField.getText();
        String role = roleMenu.getValue().toString();
        System.out.println(name + " " + mail + " " + password + " " + role);
        User user = new User(name, mail, password, role);
        user.save();
    }

    public void switchToIntroduction(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Introduction.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
