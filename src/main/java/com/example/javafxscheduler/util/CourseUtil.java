//CourseUtil.java
//Adds some utility functions for the course entity
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Course;
import com.example.javafxscheduler.entities.EventRegistration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CourseUtil {

    public static void saveCourse(Course course) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "INSERT INTO courses (course_name) VALUES ('" + course.getCourseName() + "')";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Course saved");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void deleteCourse(Course course) {
        EventRegistrationUtil.deleteAllEventRegistations(course.getCourseName());
        EventUtil.deleteEventsByCourse(course);
        WishUtil.deleteWishByCourse(course);

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "DELETE FROM courses WHERE course_id = '" + CourseUtil.getCourseIdByName(course.getCourseName()) + "'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Course deleted");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static Course[] getAllCourses(){
        ArrayList<Course> courses = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "SELECT * FROM courses";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Course course = new Course(rs.getString("course_name"));
                course.setCourseId(rs.getInt("course_id"));
                courses.add(course);
            }

        } catch (Exception e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return courses.toArray(new Course[0]);
    }

    public static int getCourseIdByName(String courseName) {
        int courseId = 0;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){

            String sql = "SELECT course_id FROM courses WHERE course_name = '" + courseName + "'";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                courseId = rs.getInt("course_id");
            }

        }catch (Exception e){
            System.out.println("Connection failed");
        }

        return courseId;
    }
}
