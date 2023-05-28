package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.Course;
import com.example.javafxscheduler.entities.Room;
import com.example.javafxscheduler.util.CourseUtil;
import com.example.javafxscheduler.util.RoomUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddDialogController {
    @FXML
    private ChoiceBox<String> typeBox;
    @FXML
    private TextField elementName;

    public void initialize() {
        typeBox.setItems(FXCollections.observableArrayList("Course", "Room"));
    }

    public void save(){
        if (typeBox.getValue().toString().equals("Course") && elementName.getText() != null){
            CourseUtil.saveCourse(new Course(elementName.getText()));
        } else if (typeBox.getValue().toString().equals("Room") && elementName.getText() != null){
            RoomUtil.saveRoom(new Room(elementName.getText()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
        }
    }
}
