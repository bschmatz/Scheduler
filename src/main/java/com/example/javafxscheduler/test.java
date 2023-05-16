package com.example.javafxscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class test {
    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")) {
            System.out.println("Connection established");


        } catch (Exception ex) {
            System.out.println("Connection failed");
            ex.printStackTrace();
        }
    }

}
