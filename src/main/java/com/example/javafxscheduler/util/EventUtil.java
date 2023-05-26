//EventUtil.java
//This class is used for database operations regarding events
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.User;

import java.sql.*;

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



}
