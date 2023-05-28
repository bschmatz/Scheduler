//AssistantViewController.java
//Represents the controller for the assistant view.
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.*;
import com.example.javafxscheduler.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Time;
import java.sql.Date;

public class AssistantViewController {
    private User user;
    int START_HOUR = 8;
    private final int END_HOUR = 23;

    @FXML
    private ChoiceBox<Course> courseField;
    @FXML
    private ChoiceBox<Room> roomField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ChoiceBox<String> startHours;
    @FXML
    private ChoiceBox<String> startMinutes;
    @FXML
    private ChoiceBox<String> endHours;
    @FXML
    private ChoiceBox<String> endMinutes;
    @FXML
    private ListView<String> wishList;

    public void initialize(){
        courseField.setItems(FXCollections.observableArrayList(CourseUtil.getAllCourses()));
        roomField.setItems(FXCollections.observableArrayList(RoomUtil.getAllRooms()));
        startHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR - 1)));
        startMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
        endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
    }

    private void notifCheck(){
        Notification[] notifs = NotificationUtil.getNotificationsByAssistant(user.getName());

        for (Notification n : notifs){
            String text = "Old time || "+ n.getCourse() + " || " + n.getDate() + ": " + n.getStartTime() + " - " + n.getEndTime() +
                    "\nNew time || " + n.getCourse() + " || " + n.getDate() + ": " + n.getNewStartTime() + " - " + n.getNewEndTime();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Some of your wishes could not be fulfilled. Times have been adjusted by an admin");
            alert.setContentText(text);
            alert.showAndWait();
        }
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

        Course course = courseField.getValue();
        Room room = roomField.getValue();
        Date date = java.sql.Date.valueOf(dateField.getValue());
        Time start = Time.valueOf(startHours.getValue() + ":" + startMinutes.getValue() + ":00");
        Time end = Time.valueOf(endHours.getValue() + ":" + endMinutes.getValue() + ":00");

        Wish wish = new Wish(user.getName(), date, start, end, course, room);
        WishUtil.saveWish(wish);

    }

    public boolean allFieldsFilled(){
        return startHours.getValue() != null
                && startMinutes.getValue() != null
                && endHours.getValue() != null
                && endMinutes.getValue() != null
                && courseField.getValue() != null
                && dateField.getValue() != null
                && roomField.getValue() != null;
    }

    public boolean validDate(){
        if (dateField.getValue().isBefore(java.time.LocalDate.now())){
            return false;
        }

        return true;
    }

    //checks the times and adjusts the options accordingly
    public void checkTimes(){
        if (startHours.getValue() != null){
            endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(Integer.parseInt(startHours.getValue()), END_HOUR)));
        }

        checkMinutes();
    }

    public void checkMinutes(){

        if (endHours.getValue() != null && Integer.parseInt(endHours.getValue()) == END_HOUR){
            endMinutes.setItems(FXCollections.observableArrayList("00"));
        }

        if (startMinutes.getValue() != null){

            if (startHours.getValue().equals(endHours.getValue())){
                endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(Integer.parseInt(startMinutes.getValue()))));
            } else {
                endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
            }


        }


    }

    //refreshes the list of wishes when the tab is changed in JavaFX
    public void refreshWishList(){
        wishList.setItems(FXCollections.observableArrayList(WishUtil.observableList(WishUtil.getWishesByName(user.getName()))));
    }

    public void printWish(){
        System.out.println(wishList.getSelectionModel().getSelectedItem());
    }

    public void setUser(User user) {
        this.user = user;
        notifCheck();
    }




}
