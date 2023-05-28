//UserUtil.java
//Represents a utility class for the user entity. It is responsible for the communication with the database.
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.util;


import com.example.javafxscheduler.entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserUtil {

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection established");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"));

                user.setUserId(rs.getInt("user_id"));
                users.add(user);
            }

        } catch (Exception ex) {
            System.out.println("Connection failed");
            ex.printStackTrace();
        }

        return users;
    }

    public static int getUserId(User user){
        int userId = 0;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            PreparedStatement ps = con.prepareStatement("SELECT user_id FROM users WHERE name = '" + user.getName() + "' AND email = '" + user.getEmail() + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                userId = rs.getInt("user_id");
                break;
            }

        }catch (Exception e){
            System.out.println("Connection failed");
        }

        return userId;
    }

    public static User getUserByName(String name){
        User user = null;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE name = '" + name + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                user = new User(rs.getString("name"), rs.getString("email") ,rs.getString("password"), rs.getString("role"));
                break;
            }


        }catch (Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return user;
    }

    public static void save(User user) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection established");
            String name = user.getName();
            String email = user.getEmail();
            String password = user.getPassword();
            String role = user.getRole();

            String sql = "INSERT INTO users (name, email, password, role) VALUES ('" +
                    name +
                    "', '" + email +
                    "', '" + password +
                    "', '" + role + "');";

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

}