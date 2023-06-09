//Wish.java
//This class represents the wish entity in the database
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.entities;

import com.example.javafxscheduler.util.TimeUtil;

import java.sql.Time;
import java.sql.Date;

public class Wish {
    private int wishId;
    private String assistant;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Course course;
    private Room room;

    public Wish(String assistant, Date date, Time startTime, Time endTime, Course course, Room room) {
        this.assistant = assistant;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.course = course;
        this.room = room;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    @Override
    public String toString(){
        return course + " | Date: " + date + " | Start: " + startTime + " | End: " + endTime + " | Room: " + room + " | Assistant: " + assistant;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        Wish wish = (Wish) o;

        return assistant.equals(wish.assistant)
                && date.equals(wish.date)
                && TimeUtil.dateOverlapping(this.date, wish.getDate())
                && TimeUtil.timeOverlapping(this.startTime, this.endTime, wish.getStartTime(), wish.getEndTime())
                && room.equals(wish.room);
    }
}
