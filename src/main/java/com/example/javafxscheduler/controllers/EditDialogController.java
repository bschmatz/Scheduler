package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.Course;
import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.Room;
import com.example.javafxscheduler.util.CourseUtil;
import com.example.javafxscheduler.util.EventUtil;
import com.example.javafxscheduler.util.RoomUtil;
import com.example.javafxscheduler.util.TimeUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.sql.Date;
import java.sql.Time;

public class EditDialogController {
    @FXML
    private ChoiceBox<Course> courseField;
    @FXML
    private DatePicker dateField;
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

    private Event event;
    private int eventId;

    public void initialize(){
        courseField.setItems(FXCollections.observableArrayList(CourseUtil.getAllCourses()));
        roomField.setItems(FXCollections.observableArrayList(RoomUtil.getAllRooms()));
        startHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(8, 23)));
        startMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
        endHours.setItems(FXCollections.observableArrayList(TimeUtil.getHours(8, 23)));
        endMinutes.setItems(FXCollections.observableArrayList(TimeUtil.getMinutes(0)));
    }

    public void setEvent(Event event) {
        this.event = event;
        this.eventId = event.getEventId();

        courseField.setValue(event.getCourse());
        dateField.setValue(event.getEventDate().toLocalDate());
        roomField.setValue(event.getEventRoom());
        startHours.setValue(event.getEventStartTime().toString().substring(0, 2));
        startMinutes.setValue(event.getEventStartTime().toString().substring(3, 5));
        endHours.setValue(event.getEventEndTime().toString().substring(0, 2));
        endMinutes.setValue(event.getEventEndTime().toString().substring(3, 5));
    }

    //Updates the event
    public void update() {
        event.setEventName(courseField.getValue());
        event.setEventDate(Date.valueOf(dateField.getValue()));
        event.setRoom(roomField.getValue());
        event.setEventStartTime(Time.valueOf(startHours.getValue() + ":" + startMinutes.getValue() + ":00"));
        event.setEventEndTime(Time.valueOf(endHours.getValue() + ":" + endMinutes.getValue() + ":00"));

        EventUtil.updateEvent(event);
    }

    //Deletes the event
    public void delete() {
        EventUtil.deleteEventById(eventId);
    }
}
