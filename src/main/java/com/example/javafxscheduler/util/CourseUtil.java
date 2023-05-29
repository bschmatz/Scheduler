//CourseUtil.java
//Adds some utility functions for the course entity, including database interaction
//Author: Benedikt Schmatz
//Last changed: 29.05.2023

package com.example.javafxscheduler.util;

import com.example.javafxscheduler.entities.Course;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CourseUtil {

    //saves a course to the database
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

    //deletes a course based on the id
    public static void deleteCourse(Course course) {
        EventRegistrationUtil.deleteCourseRegistrations(course.getCourseName());
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

    //returns all courses from the database
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

    //returns the course id based on the course name
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
