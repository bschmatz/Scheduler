//NotificationUtil.java
//This class is used for database operations regarding notifications
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NotificationUtil {

    public static void saveNotification(Notification notification) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "INSERT INTO notifications (assistant_name, date, start_time, end_time, new_start_time, new_end_time, event_name, room) VALUES ('" + notification.getAssistant() + "', '" + notification.getDate() + "', '" + notification.getStartTime() + "', '" + notification.getEndTime() + "', '" + notification.getNewStartTime() + "', '" + notification.getNewEndTime() + "', '" + notification.getCourse() + "', '" + notification.getRoom() + "')";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.executeUpdate();
            System.out.println("Notification saved");

        } catch (Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public void deleteNotification(Notification notification){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "DELETE FROM notifications WHERE notification_id = '" + notification.getNotificationId() + "'";

        } catch (Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public static Notification[] getNotificationsByAssistant(String name){
        ArrayList<Notification> notifications = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "SELECT * FROM notifications WHERE assistant_name = '" + name + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Notification notification = new Notification(rs.getString("assistant_name"), rs.getDate("date"), rs.getTime("start_time"), rs.getTime("end_time"), rs.getTime("new_start_time"), rs.getTime("new_end_time"), new Course(rs.getString("event_name")), new Room(rs.getString("room")));
                notification.setNotificationId(rs.getInt("notification_id"));
                notifications.add(notification);
            }


        } catch (Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return notifications.toArray(new Notification[0]);
    }
}


