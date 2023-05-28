//AdminViewController.java
//This class handles the admin view, where admins can create new events
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;


public class AdminViewController {

    @FXML
    private ChoiceBox<Course> courseField;
    @FXML
    private DatePicker eventDate;
    @FXML
    private ChoiceBox<Room> roomField;
    @FXML
    private ChoiceBox<String> startHours;
    @FXML
    private ChoiceBox<String> startMinutes;
    @FXML
    private ChoiceBox<String> endHours;
    @FXML
    private ChoiceBox<String> endMinutes;
    @FXML
    private ListView<Wish> wishList;
    @FXML
    private ListView<Event> eventList;

    private final int START_HOUR = 8;
    private final int END_HOUR = 23;

    private User user;

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



    public void saveEvent() {

        if (!allFieldsFilled()) {
            return;
        }

        String course = courseField.getValue().toString();
        Date date = Date.valueOf(eventDate.getValue());
        Time startTime = Time.valueOf(startHours.getValue() + ":" + startMinutes.getValue() + ":00");
        Time endTime = Time.valueOf(endHours.getValue() + ":" + endMinutes.getValue() + ":00");
        Room room = roomField.getValue();

        Event event = new Event(room, user.getUserId(), course, date, startTime, endTime);

        Wish wish;
        wish = WishUtil.getWishByEvent(event);
        for (Event e : EventUtil.getEventsByRoom(event.getEventRoom().getRoomName())) {
            if (EventUtil.sameEvent(e, event)) {
                suggestionDialog(e, event, wish);
                return;
            }

        }

        EventUtil.saveEvent(event, user);


        if (wish != null) {
            WishUtil.deleteWish(wish);
            int assistantId = UserUtil.getUserId(UserUtil.getUserByName(wish.getAssistant()));
            EventRegistrationUtil.saveEventRegistration(course, assistantId);
        }

        refreshLists();

    }

    public void saveEvent(Event event, Wish wish){

        for (Event e : EventUtil.getEventsByRoom(event.getEventRoom().getRoomName())) {
            if (EventUtil.sameEvent(e, event)) {
                suggestionDialog(e, event, wish);
                return;
            }
        }

        EventUtil.saveEvent(event, user);

        if (wish != null){
            Notification notification = new Notification(wish, event);

            WishUtil.deleteWish(wish);
            NotificationUtil.saveNotification(notification);
            int assistantId = UserUtil.getUserId(UserUtil.getUserByName(wish.getAssistant()));
            EventRegistrationUtil.saveEventRegistration(event.getEventName(), assistantId);
        }

        refreshLists();

    }

    private boolean allFieldsFilled() {
        return courseField.getValue() != null
                && eventDate.getValue() != null
                && roomField.getValue() != null
                && startHours.getValue() != null
                && startMinutes.getValue() != null
                && endHours.getValue() != null
                && endMinutes.getValue() != null;
    }

    private void refreshLists() {
        wishList.setItems(FXCollections.observableArrayList(WishUtil.getAllWishes()));
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getAllEvents()));
    }

    public void transferData() {
        Wish wish = wishList.getSelectionModel().getSelectedItem();

        if (wish == null) {
            return;
        }

        courseField.setValue(wish.getCourse());
        Date date = wish.getDate();
        eventDate.setValue(date.toLocalDate());
        roomField.setValue(wish.getRoom());
        startHours.setValue(String.valueOf(wish.getStartTime().toLocalTime().getHour()));
        startMinutes.setValue(String.valueOf(wish.getStartTime().toLocalTime().getMinute()));
        endHours.setValue(String.valueOf(wish.getEndTime().toLocalTime().getHour()));
        endMinutes.setValue(String.valueOf(wish.getEndTime().toLocalTime().getMinute()));

    }

    public void switchToLogin(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    private void suggestionDialog(Event existing, Event toSave, Wish wish) {

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
                saveEvent(toSave, wish);
            } else if (button.get() == ButtonType.NO) {
                System.out.println("Selected NO");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setUser(User user) {
        this.user = user;
    }


}
