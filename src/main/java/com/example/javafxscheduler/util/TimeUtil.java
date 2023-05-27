//TimeUtil.java
//This class provides utility methods for time related operations
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.sql.Time;
import java.util.ArrayList;

public class TimeUtil {

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

    public static String[] getMinutes(int start) {
        ArrayList<String> minutes;

        switch (start){
            case 15:
                minutes = new ArrayList<>();
                minutes.add("15");
                minutes.add("30");
                minutes.add("45");
                break;
            case 30:
                minutes = new ArrayList<>();
                minutes.add("30");
                minutes.add("45");
                break;
            case 45:
                minutes = new ArrayList<>();
                minutes.add("45");
                break;
            default:
                minutes = new ArrayList<>();
                minutes.add("00");
                minutes.add("15");
                minutes.add("30");
                minutes.add("45");
                break;
        }


        return minutes.toArray(new String[0]);
    }
}
