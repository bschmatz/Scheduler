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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;


public class AdminViewController {

    @FXML
    private ChoiceBox courseField;
    @FXML
    private DatePicker eventDate;
    @FXML
    private ChoiceBox roomField;
    @FXML
    private ChoiceBox startHours;
    @FXML
    private ChoiceBox startMinutes;
    @FXML
    private ChoiceBox endHours;
    @FXML
    private ChoiceBox endMinutes;
    @FXML
    private ListView<Wish> wishList;
    @FXML
    private ListView<Event> eventList;

    private Stage stage;
    private Parent root;
    private Scene scene;

    private final int START_HOUR = 8;
    private final int END_HOUR = 23;

    private static User user;

    public void initialize() {
        wishList.setItems(FXCollections.observableArrayList(WishUtil.getAllWishes()));
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getAllEvents()));
        courseField.setItems(FXCollections.observableArrayList(CourseUtil.getAllCourses()));
        roomField.setItems(FXCollections.observableArrayList(RoomUtil.getAllRooms()));
        startHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        startMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
        endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
    }

    public void saveEvent(Event event){
        for (Event e : EventUtil.getEventsByCourse(event.getEventName())) {
            if (EventUtil.sameEvent(e, event)) {
                suggestionDialog(e, event);
                return;
            }
        }

        EventUtil.saveEvent(event, user);

        Wish wish;
        if ((wish = WishUtil.getWishByEvent(event)) != null) {
            WishUtil.deleteWish(wish);
            int assistantId = UserUtil.getUserId(UserUtil.getUserByName(wish.getAssistant()));
            EventRegistrationUtil.saveEventRegistration(event.getEventName(), assistantId);
        }

        wishList.setItems(FXCollections.observableArrayList(WishUtil.getAllWishes()));
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getAllEvents()));

    }

    public void saveEvent() {
        if (!allFieldsFilled()) {
            return;
        }

        String course = courseField.getValue().toString();
        Date date = Date.valueOf(eventDate.getValue());
        Time startTime = Time.valueOf(startHours.getValue().toString() + ":" + startMinutes.getValue().toString() + ":00");
        Time endTime = Time.valueOf(endHours.getValue().toString() + ":" + endMinutes.getValue().toString() + ":00");
        String room = roomField.getValue().toString();

        Event event = new Event(room, user.getUserId(), course, date, startTime, endTime);

        for (Event e : EventUtil.getEventsByCourse(course)) {
            if (EventUtil.sameEvent(e, event)) {
                suggestionDialog(e, event);
                return;
            }

        }

        EventUtil.saveEvent(event, user);

        Wish wish;
        if ((wish = WishUtil.getWishByEvent(event)) != null) {
            WishUtil.deleteWish(wish);
            int assistantId = UserUtil.getUserId(UserUtil.getUserByName(wish.getAssistant()));
            EventRegistrationUtil.saveEventRegistration(course, assistantId);
        }

        wishList.setItems(FXCollections.observableArrayList(WishUtil.getAllWishes()));
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getAllEvents()));

    }

    private boolean allFieldsFilled() {
        if (courseField.getValue() == null || eventDate.getValue() == null || roomField.getValue() == null || startHours.getValue() == null || startMinutes.getValue() == null || endHours.getValue() == null || endMinutes.getValue() == null) {
            return false;
        }
        return true;
    }

    public void transferData() {
        Wish wish = wishList.getSelectionModel().getSelectedItem();

        if (wish == null) {
            return;
        }

        courseField.setValue(wish.getCourse());
        Date date = (Date) wish.getDate();
        eventDate.setValue(date.toLocalDate());
        roomField.setValue(wish.getRoom());
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

    private void suggestionDialog(Event existing, Event toSave) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("suggestionDialog.fxml"));
            DialogPane suggestionDialogPane = loader.load();

            suggestionDialogController controller = loader.getController();

            //set all the necessary variables
            controller.setExistingEnd(existing.getEventEndTime());
            controller.setCurrentStartTime(toSave.getEventStartTime());
            controller.setCurrentEndTime(toSave.getEventEndTime());
            controller.updateCurrentTimes();
            controller.updateSuggestedTimes();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(suggestionDialogPane);
            dialog.setTitle("Overlapping Events");

            Optional<ButtonType> button = dialog.showAndWait();

            if (button.get() == ButtonType.YES){
                System.out.println("Selected YES");
                toSave.setEventStartTime(controller.getSuggestedStartTime());
                toSave.setEventEndTime(controller.getSuggestedEndTime());
                saveEvent(toSave);
            }else {
                return;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void setUser(User user) {
        this.user = user;
    }


}
