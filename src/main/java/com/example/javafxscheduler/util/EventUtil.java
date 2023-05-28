//EventUtil.java
//This class is used for database operations regarding events
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.Room;
import com.example.javafxscheduler.entities.User;

import java.sql.*;
import java.util.ArrayList;

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

    public static Event[] getEventsByUser(User user){
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

            while (rs.next()){
                //String event = rs.getString("event_name") + " | " + rs.getDate("event_date") + " | " + rs.getString("event_room") + " | " + rs.getTime("event_start_time") + " | " + rs.getTime("event_end_time");
                Event event = new Event(new Room(rs.getString("event_room")),
                        rs.getInt("admin_id"),
                        rs.getString("event_name"),
                        rs.getDate("event_date"),
                        rs.getTime("event_start_time"),
                        rs.getTime("event_end_time"));

                events.add(event);
            }


        } catch (Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return events.toArray(new Event[0]);
    }

    public static boolean eventsOverlap(Event[] userEvents, Event[] eventsByCourse){
        for (Event userEvent : userEvents) {
            for (Event eventByCourse : eventsByCourse) {
                if (TimeUtil.dateOverlapping(userEvent.getEventDate(), eventByCourse.getEventDate()) && TimeUtil.timeOverlapping(userEvent.getEventStartTime(), userEvent.getEventEndTime(), eventByCourse.getEventStartTime(), eventByCourse.getEventEndTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Event[] getEventsByRoom(String roomName){
        ArrayList<Event> events = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password") ){

            String sql = "SELECT * FROM events WHERE event_room = '" + roomName + "' ORDER BY event_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Event event = new Event(new Room(rs.getString("event_room")),
                        rs.getInt("admin_id"),
                        rs.getString("event_name"),
                        rs.getDate("event_date"),
                        rs.getTime("event_start_time"),
                        rs.getTime("event_end_time"));

                events.add(event);
            }

        } catch (Exception e){
            System.out.println("Connection failed");
        }

        return events.toArray(new Event[0]);
    }

    public static Event[] getEventsByCourse(String courseName){
        ArrayList<Event> events = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password") ){

            String sql = "SELECT * FROM events WHERE event_name = '" + courseName + "' ORDER BY event_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Event event = new Event(new Room(rs.getString("event_room")),
                        rs.getInt("admin_id"),
                        rs.getString("event_name"),
                        rs.getDate("event_date"),
                        rs.getTime("event_start_time"),
                        rs.getTime("event_end_time"));

                events.add(event);
            }

        } catch (Exception e){
            System.out.println("Connection failed");
        }

        return events.toArray(new Event[0]);
    }

    public static Event[] getAllEvents() {
        ArrayList<Event> events = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "SELECT * FROM EVENTS ORDER BY event_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Event event = new Event(new Room(rs.getString("event_room")), 0, rs.getString("event_name"), rs.getDate("event_date"), rs.getTime("event_start_time"), rs.getTime("event_end_time"));
                events.add(event);
            }

        }catch (Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return events.toArray(new Event[0]);
    }

    public static boolean sameEvent(Event eventOne, Event eventTwo){
        Date oneDate = eventOne.getEventDate();
        Date twoDate = eventTwo.getEventDate();
        Time oneStart = eventOne.getEventStartTime();
        Time oneEnd= eventOne.getEventEndTime();
        Time twoStart = eventTwo.getEventStartTime();
        Time twoEnd = eventTwo.getEventEndTime();

        if (TimeUtil.dateOverlapping(oneDate, twoDate) && TimeUtil.timeOverlapping(oneStart, oneEnd, twoStart, twoEnd)){
            return true;
        }

        return false;


    }

}
