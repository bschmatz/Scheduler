//EventRegistrationUtil.java
//This class is used for database operations regarding event registrations
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventRegistrationUtil {

    public static boolean userRegistered(String event_name, int userId){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "SELECT * FROM event_registrations WHERE event_name = '" + event_name + "' AND student_id = '" + userId + "'";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }

        }catch (Exception e){
            System.out.println("Connection failed");
        }
        return false;
    }

    public static boolean registrationExists(String event_name) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "SELECT * FROM event_registrations WHERE event_name = '" + event_name + "'";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }

        }catch (Exception e){
            System.out.println("Connection failed");
        }
        return false;
    }

    public static void saveEventRegistration(String event_name, int student_id) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "INSERT INTO event_registrations (event_name, student_id) VALUES ('" + event_name + "', '" + student_id + "')";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Event saved");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void deleteEventRegistration(String eventName, int userId) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "DELETE FROM event_registrations WHERE event_name = '" + eventName + "' AND student_id = '" + userId + "'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Event deleted");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
