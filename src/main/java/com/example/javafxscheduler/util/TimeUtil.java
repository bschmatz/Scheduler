//TimeUtil.java
//This class provides utility methods for time related operations
//Author: Benedikt Schmatz
//Last changed: 29.05.2023

package com.example.javafxscheduler.util;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimeUtil {

    //checks if a date is valid, i.e. not in the past
    public static boolean validDate(LocalDate date){
        return !date.isBefore(LocalDate.now());
    }

    //checks if a date is overlapping with another date
    public static boolean dateOverlapping(Date date1, Date date2){
        return date1.equals(date2);
    }

    //checks if a time is overlapping with another time
    public static boolean timeOverlapping(Time start1, Time end1, Time start2, Time end2){
        return start1.before(end2) && end1.after(start2);
    }

    //gets all hours for a specific start and end time
    public static String[] getHours(int start, int end) {
        String[] hours = new String[end - start + 1];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = String.valueOf(start + i);
        }
        return hours;
    }

    //gets all minutes for a specific start time
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

    //returns new suggested times for a specific start and end time, based on an existing end time
    public static Time[] getSuggestedTimes(Time existingEnd, Time start, Time end){

        LocalTime startLocal = start.toLocalTime();
        LocalTime endLocal = end.toLocalTime();

        Duration duration = Duration.between(startLocal, endLocal);

        LocalTime newStart = existingEnd.toLocalTime();
        LocalTime newEnd = newStart.plus(duration);

        start = Time.valueOf(newStart);
        end = Time.valueOf(newEnd);

        Time[] suggestedTimes = {start, end};
        return suggestedTimes;
    }
}
