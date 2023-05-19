package com.example.javafxscheduler;

import com.example.javafxscheduler.entities.User;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private User user;


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
                    user = new User(rs.getString("name"), rs.getString("email") ,rs.getString("password"), rs.getString("role"));
                    System.out.println("Login successful");

                    switch (user.getRole()) {
                        case "Admin":
                            System.out.println("Admin logged in");
                            return;
                        case "Assistant":
                            System.out.println("Assistant logged in");
                            return;
                        case "Student":
                            System.out.println("Student logged in");;
                            switchToUserAgenda(e);
                            return;
                        default:
                            System.out.println("No role found");
                            break;
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println("Connection failed");
            ex.printStackTrace();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAgenda.fxml"));
        root = loader.load();

        UserAgendaController userAgendaController = loader.getController();
        userAgendaController.setCurrentUser(user);

        //root = FXMLLoader.load(getClass().getResource("UserAgenda.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MySchedule");
        stage.show();
    }

}
