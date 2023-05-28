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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;


public class AdminViewController {

    //FIELDS FOR THE ADMIN VIEW
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

    //FIELDS FOR THE ASSISTANT VIEW
    @FXML
    private ChoiceBox<Course> wishCourse;
    @FXML
    private ChoiceBox<Room> wishRoom;
    @FXML
    private DatePicker wishDate;
    @FXML
    private ChoiceBox<String> wishStartHours;
    @FXML
    private ChoiceBox<String> wishStartMinutes;
    @FXML
    private ChoiceBox<String> wishEndHours;
    @FXML
    private ChoiceBox<String> wishEndMinutes;

    private final int START_HOUR = 8;
    private final int END_HOUR = 23;
    private String tab = "Admin";

    private User user;

    public void initialize() {
        //INITIALIZE FIELDS FOR THE ADMIN VIEW
        wishList.setItems(FXCollections.observableArrayList(WishUtil.getAllWishes()));
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getAllEvents()));
        courseField.setItems(FXCollections.observableArrayList(CourseUtil.getAllCourses()));
        roomField.setItems(FXCollections.observableArrayList(RoomUtil.getAllRooms()));
        startHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        startMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
        endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));

        //INITIALIZE FIELDS FOR THE ASSISTANT VIEW
        wishCourse.setItems(FXCollections.observableArrayList(CourseUtil.getAllCourses()));
        wishRoom.setItems(FXCollections.observableArrayList(RoomUtil.getAllRooms()));
        wishStartHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR - 1)));
        wishStartMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
        wishEndHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(START_HOUR, END_HOUR)));
        wishEndMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void switchToAdminTab() {
        tab = "Admin";
    }

    public void switchToAssistantTab() {
        tab = "Assistant";
    }

    private boolean allAdminFieldsFilled() {
        return courseField.getValue() != null
                && eventDate.getValue() != null
                && roomField.getValue() != null
                && startHours.getValue() != null
                && startMinutes.getValue() != null
                && endHours.getValue() != null
                && endMinutes.getValue() != null;
    }

    public void saveEvent() {

        if (!allAdminFieldsFilled()) {
            return;
        }

        String course = courseField.getValue().toString();
        Date date = Date.valueOf(eventDate.getValue());
        Time startTime = Time.valueOf(startHours.getValue() + ":" + startMinutes.getValue() + ":00");
        Time endTime = Time.valueOf(endHours.getValue() + ":" + endMinutes.getValue() + ":00");
        Room room = roomField.getValue();

        Event event = new Event(room, user.getUserId(), course, date, startTime, endTime);

        //Create a new wish based on the event. If no wish exists for the event, wish is null
        Wish wish;
        wish = WishUtil.getWishByEvent(event);

        //for every event in the same room, check if the event overlaps with the new event
        //suggest a new time if it does. If the user does not accept the suggestion, the event is not saved
        for (Event e : EventUtil.getEventsByRoom(event.getEventRoom().getRoomName())) {
            if (EventUtil.eventsOverlap(e, event)) {
                suggestionDialog(e, event, wish);
                return;
            }

        }

        //finally, save the event if it does not need to be modified
        EventUtil.saveEvent(event, user);


        //if the event was created from a wish, delete the wish and register the assistant for the event
        if (wish != null) {
            WishUtil.deleteWish(wish);
            int assistantId = UserUtil.getUserId(UserUtil.getUserByName(wish.getAssistant()));
            EventRegistrationUtil.saveEventRegistration(course, assistantId);
        }

        refreshLists();

    }

    //almost the same method as above, but used for recursive calling if another timeslot has to be found again
    //preserves the wish
    public void saveEvent(Event event, Wish wish){

        for (Event e : EventUtil.getEventsByRoom(event.getEventRoom().getRoomName())) {
            if (EventUtil.eventsOverlap(e, event)) {
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

    //used to refresh the data within a list
    private void refreshLists() {
        wishList.setItems(FXCollections.observableArrayList(WishUtil.getAllWishes()));
        eventList.setItems(FXCollections.observableArrayList(EventUtil.getAllEvents()));
    }

    //when a list item is clicked, the data is transferred to the fields
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

    //opens a dialog to suggest a new time for the event. If the user accepts the suggestion, the event is saved
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

            //if the user accepts the suggestion, save the event, otherwise delete the wish
            if (button.get() == ButtonType.YES){
                System.out.println("Selected YES");
                toSave.setEventStartTime(controller.getSuggestedStartTime());
                toSave.setEventEndTime(controller.getSuggestedEndTime());
                saveEvent(toSave, wish);
            } else if (button.get() == ButtonType.NO) {
                System.out.println("Selected NO");
                WishUtil.deleteWish(wish);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //METHODS USED FOR THE ASSISTANT VIEW
    public void submit(ActionEvent e) throws IOException {
        Wish wish = createWishFromFields();

        if (wish == null){
            return;
        }

        WishUtil.saveWish(wish);
        refreshLists();
    }

    private Wish createWishFromFields(){
        if (!allAssistantFieldsFilled()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Not all fields filled out");
            alert.setContentText("Please fill out all fields before submitting");
            alert.showAndWait();
            return null;
        } else if (!TimeUtil.validDate(wishDate.getValue())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid date");
            alert.setContentText("Please enter a date after today");
            alert.showAndWait();
            return null;
        }

        Course course = wishCourse.getValue();
        Room room = wishRoom.getValue();
        Date date = java.sql.Date.valueOf(wishDate.getValue());
        Time start = Time.valueOf(wishStartHours.getValue() + ":" + wishStartMinutes.getValue() + ":00");
        Time end = Time.valueOf(wishEndHours.getValue() + ":" + wishEndMinutes.getValue() + ":00");

        return new Wish(user.getName(), date, start, end, course, room);
    }

    private boolean allAssistantFieldsFilled(){
        return wishCourse.getValue() != null
                && wishDate.getValue() != null
                && wishRoom.getValue() != null
                && wishStartHours.getValue() != null
                && wishStartMinutes.getValue() != null
                && wishEndHours.getValue() != null
                && wishEndMinutes.getValue() != null;
    }

    //checks the wishTimes from start to and adjusts the list items accordingly
    public void refreshTime(MouseEvent e){

        //change the end time list items according to the current tab and the start time
        if (tab.equals("Admin")){
            if (startHours.getValue() == null){
                return;
            }

            endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(Integer.parseInt(startHours.getValue()), END_HOUR)));


        } else if (tab.equals("Assistant")){
            if (wishStartHours.getValue() == null){
                return;
            }

            wishEndHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(Integer.parseInt(wishStartHours.getValue()), END_HOUR)));
        }
    }

    public void checkMinutes(MouseEvent e){

        if (tab.equals("Admin")){

            if (startMinutes.getValue() == null){
                return;
            }

            if (startHours.getValue().equals(endHours.getValue())){
                endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(Integer.parseInt(startMinutes.getValue()))));
            } else {
                endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
            }


        } else if (tab.equals("Assistant")){
            if (wishStartMinutes.getValue() == null){
                return;
            }

            if (wishStartHours.getValue().equals(wishEndHours.getValue())){
                wishEndMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(Integer.parseInt(wishStartMinutes.getValue()))));
            } else {
                wishEndMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
            }
        }
    }


}
