//Course.java
//This class represents a course in the database
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.entities;

public class Course {
    private String courseName;

    public Course() {
    }

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return courseName;
    }
}
