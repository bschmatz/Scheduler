//AdminViewController.java
//This class handles the admin view, where admins can create new events
//Author: Benedikt Schmatz
//Last changed: 19.05.2023

package com.example.javafxscheduler;

import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.*;

public class AdminViewController {

    @FXML
    TextField eventName;
    @FXML
    DatePicker eventDate;
    @FXML
    TextField eventRoom;
    @FXML
    TextField eventStart;
    @FXML
    TextField eventEnd;

    private static User user;

    public void saveEvent() {

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            if (eventName.getText().isEmpty() || eventDate.getValue() == null || eventRoom.getText().isEmpty() || eventStart.getText().isEmpty() || eventEnd.getText().isEmpty()) {
                System.out.println("Please fill out all fields");
                return;
            }

            String sql = "SELECT user_id FROM users WHERE name = '" + user.getName() + "' AND email = '" + user.getEmail() + "';";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int id = rs.getInt("user_id");

            String name = eventName.getText();
            Date date = Date.valueOf(eventDate.getValue());
            String room = eventRoom.getText();
            Time start = Time.valueOf(eventStart.getText());
            Time end = Time.valueOf(eventEnd.getText());

            sql = "INSERT INTO events (event_name, admin_id ,event_date, event_room, event_start_time, event_end_time) VALUES ('" + name + "', '"  + id + "', '" + date + "', '" + room + "', '" + start + "', '" + end + "');";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Event saved");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setUser(User user) {
        this.user = user;
    }


}
