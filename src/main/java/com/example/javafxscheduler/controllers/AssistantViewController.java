//AssistantViewController.java
//Represents the controller for the assistant view.
//Author: Benedikt Schmatz
//Last changed: 25.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.User;
import com.example.javafxscheduler.entities.Wish;
import com.example.javafxscheduler.util.TimeList;
import com.example.javafxscheduler.util.WishUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Date;

public class AssistantViewController {
    private User user;
    private final int START_HOUR = 8;
    private final int END_HOUR = 23;

    @FXML
    private ChoiceBox courseField;
    @FXML
    private TextField roomField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ChoiceBox startHours;
    @FXML
    private ChoiceBox startMinutes;
    @FXML
    private ChoiceBox endHours;
    @FXML
    private ChoiceBox endMinutes;

    public void initialize(){

        courseField.setItems(FXCollections.observableArrayList("Maths", "Programming", "English", "Algorithms", "Databases"));
        startHours.setItems(FXCollections.observableArrayList(TimeList.getHours(START_HOUR, END_HOUR)));
        startMinutes.setItems(FXCollections.observableArrayList(TimeList.getMinutes()));
        endHours.setItems(FXCollections.observableArrayList(TimeList.getHours(START_HOUR, END_HOUR)));
        endMinutes.setItems(FXCollections.observableArrayList(TimeList.getMinutes()));
    }

    public void submit(ActionEvent e){
        if (!allFieldsFilled()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not all fields filled out");
            alert.setContentText("Please fill out all fields before submitting");
            alert.showAndWait();
        }

        String course = courseField.getValue().toString();
        String room = roomField.getText();
        Date date = java.sql.Date.valueOf(dateField.getValue());
        Time start = Time.valueOf(startHours.getValue().toString() + ":" + startMinutes.getValue().toString() + ":00");
        Time end = Time.valueOf(endHours.getValue().toString() + ":" + endMinutes.getValue().toString() + ":00");

        Wish wish = new Wish(user.getName(), date, start, end, course, room);
        WishUtil.saveWish(wish);

    }

    public boolean allFieldsFilled(){
        if (startHours.getValue() == null || startMinutes.getValue() == null || endHours.getValue() == null || endMinutes.getValue() == null || courseField.getValue() == null || dateField.getValue() == null){
            return false;
        }

        return true;
    }

    public void setUser(User user) {
        this.user = user;
    }




}
