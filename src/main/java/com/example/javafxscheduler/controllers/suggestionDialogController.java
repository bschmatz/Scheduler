package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.util.TimeUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Time;

public class suggestionDialogController {

    @FXML
    private Label currentTime;
    @FXML
    private Label suggestedTime;

    private Time existingEnd;
    private Time currentStartTime;
    private Time currentEndTime;
    private Time suggestedStartTime;
    private Time suggestedEndTime;

    public void setCurrentStartTime(Time time) {
        currentStartTime = time;
    }

    public void setCurrentEndTime(Time time) {
        currentEndTime = time;
    }

    public void setExistingEnd(Time time){
        existingEnd = time;
    }

    public Time getSuggestedStartTime(){
        return this.suggestedStartTime;
    }

    public Time getSuggestedEndTime(){
        return this.suggestedEndTime;
    }

    public void updateCurrentTimes() {
        currentTime.setText(currentStartTime.toString() + " - " + currentEndTime.toString());
    }

    public void updateSuggestedTimes(){
        Time[] suggestedTimes = TimeUtil.getSuggestedTimes(existingEnd, currentStartTime, currentEndTime);
        suggestedTime.setText(suggestedTimes[0].toString() + " - " + suggestedTimes[1].toString());
        suggestedStartTime = suggestedTimes[0];
        suggestedEndTime = suggestedTimes[1];
    }

}
