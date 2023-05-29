//AssistantViewController.java
//Represents the controller for the assistant view.
//Author: Benedikt Schmatz
//Last changed: 29.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.*;
import com.example.javafxscheduler.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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

    @FXML
    private ListView<Event> eventList;
    @FXML
    private ChoiceBox<Course> eventCourse;

    //Initializes the assistant view
    public void initialize() {
        courseField.setItems(FXCollections.observableArrayList(CourseUtil.getAllCourses()));
        roomField.setItems(FXCollections.observableArrayList(RoomUtil.getAllRooms()));
        startHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR - 1)));
        startMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
        endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));

        eventCourse.setItems(FXCollections.observableArrayList(CourseUtil.getAllCourses()));
    }

    public void setUser(User user) {
        this.user = user;
        NotificationUtil.notificationCheck(user);
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getEventsByUser(user)));
    }

    //creates a new wish from the input fields
    private Wish createWishFromFields() {
        if (!allFieldsFilled()) {
            notAllFieldsError();
            return null;
        } else if (!TimeUtil.validDate(dateField.getValue())) {
            invalidDateError();
            return null;
        }

        Course course = courseField.getValue();
        Room room = roomField.getValue();
        Date date = java.sql.Date.valueOf(dateField.getValue());
        Time start = Time.valueOf(startHours.getValue() + ":" + startMinutes.getValue() + ":00");
        Time end = Time.valueOf(endHours.getValue() + ":" + endMinutes.getValue() + ":00");

        return new Wish(user.getName(), date, start, end, course, room);
    }

    //saves a wish if all fields are filled
    public void submit(ActionEvent e) {
        Wish wish;

        if ((wish = createWishFromFields()) == null) {
            return;
        }

        WishUtil.saveWish(wish);
    }

    //checks if all fields are filled
    public boolean allFieldsFilled() {
        return startHours.getValue() != null
                && startMinutes.getValue() != null
                && endHours.getValue() != null
                && endMinutes.getValue() != null
                && courseField.getValue() != null
                && dateField.getValue() != null
                && roomField.getValue() != null;
    }

    //checks the times and adjusts the options accordingly
    public void refreshTime(MouseEvent e) {
        if (startHours.getValue() == null) {
            return;
        }

        endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(Integer.parseInt(startHours.getValue()), END_HOUR)));
    }

    //checks the minutes and adjusts the options accordingly
    public void checkMinutes(MouseEvent e) {

        if (startMinutes.getValue() == null) {
            return;
        }

        if (startHours.getValue().equals(endHours.getValue())) {
            endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(Integer.parseInt(startMinutes.getValue()))));
        } else {
            endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
        }
    }

    //refreshes the list of wishes when the tab is changed in JavaFX
    public void refreshWishList() {
        wishList.setItems(FXCollections.observableArrayList(WishUtil.observableList(WishUtil.getWishesByName(user.getName()))));
    }

    //enlist the user for an event
    public void enlist() {

        if (eventCourse.getValue() == null) {
            return;
        }

        //checks if the user has already been registered for the event
        if (EventRegistrationUtil.userRegistered(eventCourse.getValue().toString(), UserUtil.getUserId(user))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Already registered!");
            alert.setHeaderText("You are already registered for this event!");
            alert.setContentText("Please choose another event or contact an administrator!");
            alert.showAndWait();
            return;
        }

        Event[] userEvents = EventUtil.getEventsByUser(user);
        Event[] eventsByCourse = EventUtil.getEventsByCourse(eventCourse.getValue().toString());

        for (Event event : userEvents){
            for (Event courseEvent : eventsByCourse){
                if (EventUtil.eventsOverlap(event, courseEvent)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlapping events!");
                    alert.setHeaderText("One or more events are overlapping!");
                    alert.setContentText("Please choose another event or contact an administrator!");
                    alert.showAndWait();
                    return;
                }
            }
        }

        EventRegistrationUtil.saveEventRegistration(eventCourse.getValue().toString(), UserUtil.getUserId(user));
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getEventsByUser(user)));
    }

    //signs the user out of an event
    public void signOut() {

        if (!EventRegistrationUtil.userRegistered(eventCourse.getValue().toString(), UserUtil.getUserId(user))) {
            return;
        }

        EventRegistrationUtil.deleteEventRegistration(eventCourse.getValue().toString(), UserUtil.getUserId(user));
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getEventsByUser(user)));
    }

    //switches to the login view
    public void switchToLogin(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.show();
    }

    //error if not all fields are filled
    private void notAllFieldsError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Not all fields filled out");
        alert.setContentText("Please fill out all fields before submitting");
        alert.showAndWait();
    }

    //error if the date is invalid
    private void invalidDateError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid date");
        alert.setContentText("Please enter a date after today");
        alert.showAndWait();
    }


}
