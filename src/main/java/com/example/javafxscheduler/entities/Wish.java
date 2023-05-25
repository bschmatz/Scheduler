package com.example.javafxscheduler.entities;

import java.sql.Time;
import java.util.Date;

public class Wish {
    private String assistant;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String course;
    private String room;

    public Wish(String assistant, Date date, Time startTime, Time endTime, String course, String room) {
        this.assistant = assistant;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.course = course;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

        public String getRoom() {
        return room;
    }

        public void setRoom(String room) {
        this.room = room;
    }


}
