//UserViewController.java
//Represents the controller for the user agenda. It is responsible for displaying the events of the user.
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.util.TimeUtil;
import javafx.fxml.FXML;
import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.User;
import com.example.javafxscheduler.util.EventRegistrationUtil;
import com.example.javafxscheduler.util.EventUtil;
import com.example.javafxscheduler.util.UserUtil;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

public class UserViewController {

    @FXML
    private DatePicker datePicker;
    @FXML
    private ListView<Event> eventList = new ListView<>();
    @FXML
    private Button enlistButton;
    @FXML
    private Button signOutButton;
    @FXML
    private ChoiceBox courseBox;

    private static User currentUser;

    public void initialize() {
        courseBox.setItems(FXCollections.observableArrayList("Maths", "Programming", "English", "Algorithms", "Databases"));
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;

        eventList.setItems(FXCollections.observableArrayList(EventUtil.getEventsByUser(currentUser)));
        System.out.println(eventList.getItems().toString());
    }

    public void refreshEvents() {
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getEventsByUser(currentUser)));
    }

    public void enlist() {

        if (courseBox.getValue() == null) {
            return;
        }

        //checks if the user has already been registered for the event
        if (EventRegistrationUtil.userRegistered(courseBox.getValue().toString(), UserUtil.getUserId(currentUser))) {
            return;
        }

        Event[] userEvents = EventUtil.getEventsByUser(currentUser);
        Event[] EventsByCourse = EventUtil.getEventsByCourse(courseBox.getValue().toString());

        //checks if the user has already been registered for an event at the same time
        for (Event userEvent : userEvents) {
            for (Event eventByCourse : EventsByCourse) {
                if (TimeUtil.dateOverlapping(userEvent.getEventDate(), eventByCourse.getEventDate()) && TimeUtil.timeOverlapping(userEvent.getEventStartTime(), userEvent.getEventEndTime(), eventByCourse.getEventStartTime(), eventByCourse.getEventEndTime())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlapping events!");
                    alert.setHeaderText("You are already registered for an event at this time!");
                    alert.setContentText("Please choose another event or time!");
                    alert.showAndWait();
                    return;
                }
            }
        }

        EventRegistrationUtil.saveEventRegistration(courseBox.getValue().toString(), UserUtil.getUserId(currentUser));
        refreshEvents();
    }

    public void signOut() {

        if (!EventRegistrationUtil.userRegistered(courseBox.getValue().toString(), UserUtil.getUserId(currentUser))) {
            return;
        }

        EventRegistrationUtil.deleteEventRegistration(courseBox.getValue().toString(), UserUtil.getUserId(currentUser));
        refreshEvents();
    }

}
