//UserViewController.java
//Represents the controller for the user agenda. It is responsible for displaying the events of the user.
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.Course;
import com.example.javafxscheduler.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UserViewController {

    @FXML
    private ListView<Event> eventList = new ListView<>();
    @FXML
    private ChoiceBox<Course> courseBox;

    private static User currentUser;

    public void initialize() {
        courseBox.setItems(FXCollections.observableArrayList(CourseUtil.getAllCourses()));
    }

    public void setCurrentUser(User user) {
        currentUser = user;

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Already registered!");
            alert.setHeaderText("You are already registered for this event!");
            alert.setContentText("Please choose another event or contact an administrator!");
            alert.showAndWait();
            return;
        }

        Event[] userEvents = EventUtil.getEventsByUser(currentUser);
        Event[] EventsByCourse = EventUtil.getEventsByCourse(courseBox.getValue().toString());

        for (Event e : userEvents){
            for (Event ev : EventsByCourse){
                if (EventUtil.eventsOverlap(e, ev)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlapping events!");
                    alert.setHeaderText("One or more events are overlapping!");
                    alert.setContentText("Please choose another event or contact an administrator!");
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

    public void switchToIntroduction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Introduction.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Welcome!");
        stage.centerOnScreen();
        stage.show();
    }
}
