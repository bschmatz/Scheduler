//RoomUtil.java
//This class is used for database operations regarding rooms
//Author: Benedikt Schmatz
//Last changed: 29.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RoomUtil {

    //saves a room to the database
    public static void saveRoom(Room room){

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "INSERT INTO rooms (room_name) VALUES ('" + room.getRoomName() + "')";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Room saved");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //deletes a room by its name
    public static void deleteRoom(Room room){
        EventUtil.deleteEventsByRoom(room);
        WishUtil.deleteWishesByRoom(room);

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "DELETE FROM rooms WHERE room_name = '" + room.getRoomName() + "'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Room deleted");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //returns all rooms from the database
    public static Room[] getAllRooms(){
        ArrayList<Room> rooms = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {

            String sql = "SELECT room_name FROM rooms";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Room room = new Room(rs.getString("room_name"));
                rooms.add(room);
            }

        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return rooms.toArray(new Room[0]);
    }
}
