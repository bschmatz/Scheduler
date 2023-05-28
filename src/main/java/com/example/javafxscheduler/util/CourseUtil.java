//CourseUtil.java
//Adds some utility functions for the course entity
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CourseUtil {

    public static Course[] getAllCourses(){
        ArrayList<Course> courses = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "SELECT course_name FROM courses";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Course course = new Course(rs.getString("course_name"));
                courses.add(course);
            }

        } catch (Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return courses.toArray(new Course[0]);
    }
}
