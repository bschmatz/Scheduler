//AssistantViewController.java
//Represents the controller for the assistant view.
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.User;
import com.example.javafxscheduler.entities.Wish;
import com.example.javafxscheduler.util.TimeUtil;
import com.example.javafxscheduler.util.WishUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

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
    @FXML
    private ListView<String> wishList;

    public void initialize(){
        courseField.setItems(FXCollections.observableArrayList("Maths", "Programming", "English", "Algorithms", "Databases"));
        startHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        startMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes()));
        endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes()));
    }

    public void submit(ActionEvent e){
        if (!allFieldsFilled()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Not all fields filled out");
            alert.setContentText("Please fill out all fields before submitting");
            alert.showAndWait();
            return;
        } else if (!validDate()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid date");
            alert.setContentText("Please enter a valid date");
            alert.showAndWait();
            return;
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
        if (startHours.getValue() == null || startMinutes.getValue() == null || endHours.getValue() == null || endMinutes.getValue() == null || courseField.getValue() == null || dateField.getValue() == null || roomField.getText().isEmpty()){
            return false;
        }

        return true;
    }

    public boolean validDate(){
        if (dateField.getValue().isBefore(java.time.LocalDate.now())){
            return false;
        }

        return true;
    }

    public void timeCheck(MouseEvent e){
        endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(Integer.parseInt(startHours.getValue().toString()), END_HOUR)));
    }

    public void minuteCheck(MouseEvent e){
        if (endHours.getValue() == null){
            return;
        } else if (Integer.parseInt((String) endHours.getValue()) == END_HOUR){
            endMinutes.setItems(FXCollections.observableArrayList("00"));
        }else {
            endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes()));
        }
    }

    //refreshes the list of wishes when the tab is changed in JavaFX
    public void refreshWishList(){
        wishList.setItems(FXCollections.observableArrayList(WishUtil.observableList(WishUtil.getWishesByName(user.getName()))));
    }

    public void printWish(MouseEvent e){
        System.out.println(wishList.getSelectionModel().getSelectedItem());
    }

    public void setUser(User user) {
        this.user = user;
    }




}
