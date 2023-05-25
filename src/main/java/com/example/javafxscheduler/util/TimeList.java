package com.example.javafxscheduler.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.sql.Time;
import java.util.ArrayList;

public class TimeList {

    public static ChoiceBox getTimeBox(Time start, Time end) {
        ChoiceBox box;

        ArrayList<Time> timeList = new ArrayList<>();
        Time time = start;
        while (time.before(end)) {
            timeList.add(time);
            time = new Time(time.getTime() + 900000);
        }

        ObservableList<Time> observableList = FXCollections.observableList(timeList);
        box = new ChoiceBox(observableList);

        return box;
    }

    public static String[] getHours(int start, int end) {
        String[] hours = new String[end - start + 1];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = String.valueOf(start + i);
        }
        return hours;
    }

    public static String[] getMinutes() {
        String[] minutes = new String[4];
        minutes[0] = "00";
        minutes[1] = "15";
        minutes[2] = "30";
        minutes[3] = "45";
        return minutes;
    }
}
