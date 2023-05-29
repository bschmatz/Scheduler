//WishUtil.java
//This class is used for database interaction regarding the wishes
//Author: Benedikt Schmatz
//Last changed: 29.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Course;
import com.example.javafxscheduler.entities.Event;
import com.example.javafxscheduler.entities.Room;
import com.example.javafxscheduler.entities.Wish;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class WishUtil {

    // Inserts a new wish into the database with the provided wish object's attributes (assistant, date, start time, end time, course, room)
    public static void saveWish(Wish wish) {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection established");

            String sql = "INSERT INTO wishes (assistant_name, date, start_time, end_time, event_name, room) VALUES ('" + wish.getAssistant() + "', '" + wish.getDate() + "', '" + wish.getStartTime() + "', '" + wish.getEndTime() + "', '" + wish.getCourse() + "', '" + wish.getRoom() + "')";
            PreparedStatement statement = con.prepareStatement(sql);

            try {
                statement.executeUpdate();
                System.out.println("Insert successful");
            } catch (Exception e) {
                System.out.println("Insert failed");
            }

        } catch (Exception ex) {
            System.out.println("Connection failed");
            ex.printStackTrace();
        }

    }

    // Deletes a wish from the database based on the provided wish object's attributes (assistant, date, start time, end time, course, room).
    public static void deleteWish(Wish wish) {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection established");

            String sql = "DELETE FROM wishes WHERE assistant_name = '" + wish.getAssistant() + "' AND date = '" + wish.getDate() + "' AND start_time = '" + wish.getStartTime() + "' AND end_time = '" + wish.getEndTime() + "' AND event_name = '" + wish.getCourse() + "' AND room = '" + wish.getRoom() + "'";
            PreparedStatement ps = con.prepareStatement(sql);

            try {
                ps.executeUpdate();
                System.out.println("Delete successful");
            } catch (Exception e) {
                System.out.println("Delete failed");
            }

        } catch (Exception e) {
            System.out.println("Connection failed");
        }

    }

    // Deletes all wishes from the database associated with the specified room.
    public static void deleteWishesByRoom(Room room){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "DELETE FROM wishes WHERE room = '" + room.getRoomName() + "'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Wishes deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Deletes all wishes from the database associated with the specified course
    public static void deleteWishByCourse(Course course){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "DELETE FROM wishes WHERE event_name = '" + course.getCourseName() + "'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Wishes deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retrieves a wish from the database based on the provided event object's attributes (course, date, start time, end time, room).
    public static Wish getWishByEvent(Event e){
        Wish wish = null;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "SELECT * FROM wishes WHERE event_name = '" + e.getCourse() + "' AND date = '" + e.getEventDate() + "' AND start_time = '" + e.getEventStartTime() + "' AND end_time = '" + e.getEventEndTime() + "' AND room = '" + e.getEventRoom() + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                wish = new Wish(rs.getString("assistant_name"),
                        rs.getDate("date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        new Course(rs.getString("event_name")),
                        new Room(rs.getString("room")));
                wish.setWishId(rs.getInt("wish_id"));

                break;
            }

        } catch (Exception ex){
            System.out.println("Connection failed");
        }

        return wish;
    }

    // Converts an array of wishes into an observable list of strings.
    public static ObservableList<String> observableList(Wish[] wishes) {
        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList();
        for (Wish wish : wishes) {
            observableList.add(wish.toString());
        }
        return observableList;
    }

    // Retrieves all wishes from the database and returns them as an array of wish objects.
    public static Wish[] getAllWishes() {
        ArrayList<Wish> wishes = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "SELECT * FROM wishes ORDER BY date , start_time;";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                wishes.add(new Wish(rs.getString("assistant_name"),
                        rs.getDate("date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        new Course(rs.getString("event_name")),
                        new Room(rs.getString("room"))));
            }

        }catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return wishes.toArray(new Wish[0]);
    }

    // Retrieves all wishes associated with the specified username from the database and returns them as an array of wish objects.
    public static Wish[] getWishesByName(String username) {
        ArrayList<Wish> wishes = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "SELECT * FROM wishes WHERE assistant_name = '" + username + "' ORDER BY date , start_time;";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                wishes.add(new Wish(rs.getString("assistant_name"),
                        rs.getDate("date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        new Course(rs.getString("event_name")),
                        new Room(rs.getString("room"))));
            }

        }catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return wishes.toArray(new Wish[0]);
    }
}
