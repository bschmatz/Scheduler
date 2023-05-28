//RoomUtil.java
//This class is used for database operations regarding rooms
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RoomUtil {

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
