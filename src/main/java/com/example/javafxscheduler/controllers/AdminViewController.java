//AdminViewController.java
//This class handles the admin view, where admins can create new events
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.User;
import com.example.javafxscheduler.entities.Wish;
import com.example.javafxscheduler.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;


public class AdminViewController {

    @FXML
    ChoiceBox courseField;
    @FXML
    DatePicker eventDate;
    @FXML
    TextField eventRoom;
    @FXML
    ChoiceBox startHours;
    @FXML
    ChoiceBox startMinutes;
    @FXML
    ChoiceBox endHours;
    @FXML
    ChoiceBox endMinutes;
    @FXML
    ListView<Wish> wishList;

    private Stage stage;
    private Parent root;
    private Scene scene;

    private final int START_HOUR = 8;
    private final int END_HOUR = 23;

    private static User user;

    public void initialize(){
        wishList.setItems(FXCollections.observableArrayList(WishUtil.getAllWishes()));
        courseField.setItems(FXCollections.observableArrayList("Maths", "Programming", "English", "Algorithms", "Databases"));
        startHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        startMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes()));
        endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes()));
    }

    public void saveEvent() {
        if (!allFieldsFilled()){
            return;
        }

        String course = courseField.getValue().toString();
        Date date = Date.valueOf(eventDate.getValue());
        Time startTime = Time.valueOf(startHours.getValue().toString() + ":" + startMinutes.getValue().toString() + ":00");
        Time endTime = Time.valueOf(endHours.getValue().toString() + ":" + endMinutes.getValue().toString() + ":00");
        String room = eventRoom.getText();

        Event event = new Event(room, user.getUserId(), course, date, startTime, endTime);

        EventUtil.saveEvent(event, user);

        Wish wish;
        if ((wish = WishUtil.getWishByEvent(event)) != null) {
            WishUtil.deleteWish(wish);
            int assistantId = UserUtil.getUserId(UserUtil.getUserByName(wish.getAssistant()));
            EventRegistrationUtil.saveEventRegistration(course, assistantId);
        }

        wishList.setItems(FXCollections.observableArrayList(WishUtil.getAllWishes()));

    }

    private boolean allFieldsFilled() {
        if (courseField.getValue() == null || eventDate.getValue() == null || eventRoom.getText().isEmpty() || startHours.getValue() == null || startMinutes.getValue() == null || endHours.getValue() == null || endMinutes.getValue() == null) {
            return false;
        }
        return true;
    }

    public void transferData(){
        Wish wish = wishList.getSelectionModel().getSelectedItem();

        if (wish == null){
            return;
        }

        courseField.setValue(wish.getCourse());
        Date date = (Date) wish.getDate();
        eventDate.setValue(date.toLocalDate());
        eventRoom.setText(wish.getRoom());
        startHours.setValue(wish.getStartTime().toLocalTime().getHour());
        startMinutes.setValue(wish.getStartTime().toLocalTime().getMinute());
        endHours.setValue(wish.getEndTime().toLocalTime().getHour());
        endMinutes.setValue(wish.getEndTime().toLocalTime().getMinute());

    }

        public void switchToLogin(ActionEvent e) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        }


    public void setUser(User user) {
        this.user = user;
    }


}
