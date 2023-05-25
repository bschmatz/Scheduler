//UserAgendaController.java
//Represents the controller for the user agenda. It is responsible for displaying the events of the user.
//Author: Benedikt Schmatz
//Last changed: 19.05.2023

package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class UserViewController {
    private static User currentUser;
    private static ArrayList<Event> events = new ArrayList<>();

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static void getEvents() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection established");
            String sql = "SELECT user_id FROM users WHERE name = '" + currentUser.getName() + "' AND email = '" + currentUser.getEmail() + "';";

            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int id = rs.getInt("user_id");

            sql = "SELECT * FROM event_registrations er, events e WHERE student_id = '" + id + "' AND er.event_id = e.event_id;";
            statement = con.prepareStatement(sql);
            rs = statement.executeQuery();

            while (rs.next()) {
                Event event = new Event(rs.getInt("room_id"), rs.getInt("admin_id"), rs.getString("event_name"), rs.getDate("event_date"), rs.getTime("event_start_time"), rs.getTime("event_end_time"));
                event.setEventId(getEventId(event, con));
                System.out.println(event);
                events.add(event);

            }

        } catch (Exception ex) {
            System.out.println("Connection failed");
            ex.printStackTrace();
        }
    }

    private static int getEventId(Event event, Connection con) throws Exception{
        String sql = "SELECT event_id FROM events WHERE event_name = '" + event.getEventName() + "' AND event_date = '" + event.getEventDate() + "' AND event_start_time = '" + event.getEventStartTime() + "' AND event_end_time = '" + event.getEventEndTime() + "';";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt("event_id");
    }

    public void enlist() {

    }

    public static void main(String[] args) {
        currentUser = new User("Gamal", "gamal@gmail.com", "gamal123", "Student");
        getEvents();
    }

}
