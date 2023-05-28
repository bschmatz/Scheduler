//NotificationUtil.java
//This class is used for database operations regarding notifications
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.*;
import javafx.scene.control.Alert;

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

    public static void deleteNotification(Notification notification){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "DELETE FROM notifications WHERE notification_id = '" + notification.getNotificationId() + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Notification deleted");

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

    public static void notifCheck(User user){
        Notification[] notifs = NotificationUtil.getNotificationsByAssistant(user.getName());

        for (Notification n : notifs){

            //Delete the notification from the database
            NotificationUtil.deleteNotification(n);

            String text = "Old time || "+ n.getCourse() + " || " + n.getDate() + ": " + n.getStartTime() + " - " + n.getEndTime() +
                    "\nNew time || " + n.getCourse() + " || " + n.getDate() + ": " + n.getNewStartTime() + " - " + n.getNewEndTime();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Some of your wishes could not be fulfilled. Times have been adjusted by an admin");
            alert.setContentText(text);
            alert.showAndWait();
        }
    }
}


