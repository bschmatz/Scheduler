//EventUtil.java
//This class is used for database operations regarding events
//Author: Benedikt Schmatz
//Last changed: 29.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Course;
import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.Room;
import com.example.javafxscheduler.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class EventUtil {

    //saves an event to the database
    public static void saveEvent(Event event) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "INSERT INTO events (event_name, admin_id ,event_date, event_room, event_start_time, event_end_time) VALUES ('"
                    + event.getCourse()
                    + "', '"
                    + event.getAdminId()
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

    //deletes all events for a specific course
    public static void deleteEventsByCourse(Course course) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "DELETE FROM events WHERE event_name = '" + course.getCourseName() + "'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Events deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //deletes an event by its id
    public static void deleteEventById(int id) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "DELETE FROM events WHERE event_id = '" + id + "'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Event deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //deletes all events for a specific room
    public static void deleteEventsByRoom(Room room) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "DELETE FROM events WHERE event_room = '" + room.getRoomName() + "'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Events deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //gets all events for a specific user
    public static Event[] getEventsByUser(User user) {
        ArrayList<Event> events = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "SELECT events.event_name, " +
                    "admin_id, " +
                    "event_date, " +
                    "event_room, " +
                    "event_start_time, " +
                    "event_end_time " +
                    "FROM events INNER JOIN event_registrations ON events.event_name = event_registrations.event_name " +
                    "WHERE student_id = '" + UserUtil.getUserId(user) + "' " +
                    "ORDER BY event_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //String event = rs.getString("event_name") + " | " + rs.getDate("event_date") + " | " + rs.getString("event_room") + " | " + rs.getTime("event_start_time") + " | " + rs.getTime("event_end_time");
                Event event = new Event(new Room(rs.getString("event_room")),
                        rs.getInt("admin_id"),
                        new Course(rs.getString("event_name")),
                        rs.getDate("event_date"),
                        rs.getTime("event_start_time"),
                        rs.getTime("event_end_time"));

                events.add(event);
            }

        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return events.toArray(new Event[0]);
    }

    //returns all events for a specific room
    public static Event[] getEventsByRoom(String roomName) {
        ArrayList<Event> events = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "SELECT * FROM events WHERE event_room = '" + roomName + "' ORDER BY event_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event(new Room(rs.getString("event_room")),
                        rs.getInt("admin_id"),
                        new Course(rs.getString("event_name")),
                        rs.getDate("event_date"),
                        rs.getTime("event_start_time"),
                        rs.getTime("event_end_time"));

                events.add(event);
            }

        } catch (Exception e) {
            System.out.println("Connection failed");
        }

        return events.toArray(new Event[0]);
    }

    //returns all events for a specific course
    public static Event[] getEventsByCourse(String courseName) {
        ArrayList<Event> events = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "SELECT * FROM events WHERE event_name = '" + courseName + "' ORDER BY event_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event(new Room(rs.getString("event_room")),
                        rs.getInt("admin_id"),
                        new Course(rs.getString("event_name")),
                        rs.getDate("event_date"),
                        rs.getTime("event_start_time"),
                        rs.getTime("event_end_time"));

                events.add(event);
            }

        } catch (Exception e) {
            System.out.println("Connection failed");
        }

        return events.toArray(new Event[0]);
    }

    //returns all events
    public static Event[] getAllEvents() {
        ArrayList<Event> events = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "SELECT * FROM EVENTS ORDER BY event_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event(new Room(rs.getString("event_room")), 0, new Course(rs.getString("event_name")), rs.getDate("event_date"), rs.getTime("event_start_time"), rs.getTime("event_end_time"));
                event.setEventId(rs.getInt("event_id"));
                events.add(event);
            }

        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return events.toArray(new Event[0]);
    }

    //updates a specific event
    public static void updateEvent(Event event) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection successful");

            String sql = "UPDATE events SET event_room = '" + event.getEventRoom().getRoomName() + "', event_name = '" + event.getCourse().getCourseName() + "', event_date = '" + event.getEventDate() + "', event_start_time = '" + event.getEventStartTime() + "', event_end_time = '" + event.getEventEndTime() + "' WHERE event_id = '" + event.getEventId() + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Connection failed");
        }
    }

    //checks if two given events overlap
    public static boolean eventsOverlap(Event eventOne, Event eventTwo) {
        Date oneDate = eventOne.getEventDate();
        Date twoDate = eventTwo.getEventDate();
        Time oneStart = eventOne.getEventStartTime();
        Time oneEnd = eventOne.getEventEndTime();
        Time twoStart = eventTwo.getEventStartTime();
        Time twoEnd = eventTwo.getEventEndTime();

        return TimeUtil.dateOverlapping(oneDate, twoDate) && TimeUtil.timeOverlapping(oneStart, oneEnd, twoStart, twoEnd);

    }
}
