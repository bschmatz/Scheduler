//EventUtil.java
//This class is used for database operations regarding events
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.User;
import com.example.javafxscheduler.entities.Wish;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EventUtil {

    public static void saveEvent(Event event, User user) {

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "INSERT INTO events (event_name, admin_id ,event_date, event_room, event_start_time, event_end_time) VALUES ('"
                    + event.getEventName()
                    + "', '"
                    + UserUtil.getUserId(user)
                    + "', '"
                    + event.getEventDate()
                    + "', '"
                    + event.getEventRoom()
                    + "', '"
                    + event.getEventStartTime()
                    + "', '"
                    + event.getEventEndTime()
                    + "')";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Event saved");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<String> observableList(String[] events) {
        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList();
        observableList.addAll(Arrays.asList(events));
        return observableList;
    }

    public static Event[] getEventsByUser(User user){
        ArrayList<Event> events = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "SELECT events.event_name, event_date, event_room, event_start_time, event_end_time FROM events INNER JOIN event_registrations ON events.event_name = event_registrations.event_name WHERE student_id = '" + UserUtil.getUserId(user) + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                //String event = rs.getString("event_name") + " | " + rs.getDate("event_date") + " | " + rs.getString("event_room") + " | " + rs.getTime("event_start_time") + " | " + rs.getTime("event_end_time");
                Event event = new Event(rs.getString("event_room"), 0, rs.getString("event_name"), rs.getDate("event_date"), rs.getTime("event_start_time"), rs.getTime("event_end_time"));
                events.add(event);
            }


        } catch (Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return events.toArray(new Event[0]);
    }

}
