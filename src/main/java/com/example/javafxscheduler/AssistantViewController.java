package com.example.javafxscheduler;

import com.example.javafxscheduler.entities.User;
import com.example.javafxscheduler.util.TimeList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;


public class AssistantViewController {
    private User user;

    @FXML
    private ChoiceBox startHours;
    @FXML
    private ChoiceBox startMinutes;
    @FXML
    private ChoiceBox endHours;
    @FXML
    private ChoiceBox endMinutes;



    public void setUser(User user) {
        this.user = user;
    }



}
