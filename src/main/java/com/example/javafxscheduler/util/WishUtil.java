package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Wish;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class WishUtil {

    public static void saveWish(Wish wish) {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection established");

            String sql = "INSERT INTO wishes (assistant_name, date, start_time, end_time, event_name) VALUES ('" + wish.getAssistant() + "', '" + wish.getDate() + "', '" + wish.getStartTime() + "', '" + wish.getEndTime() + "', '" + wish.getCourse() + "')";
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
