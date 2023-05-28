//Wish.java
//This class represents the wish entity in the database
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.entities;

import java.sql.Time;
import java.sql.Date;

public class Notification {
    private int notificationId;
    private String assistant;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Time newStartTime;
    private Time newEndTime;
    private Course course;
    private Room room;

    public Notification(Wish wish, Event event){
        this.assistant = wish.getAssistant();
        this.date = wish.getDate();
        this.startTime = wish.getStartTime();
        this.endTime = wish.getEndTime();
        this.newStartTime = event.getEventStartTime();
        this.newEndTime = event.getEventEndTime();
        this.course = wish.getCourse();
        this.room = wish.getRoom();
    }

    public Notification(String assistant, Date date, Time startTime, Time endTime, Time newStartTime, Time newEndTime, Course course, Room room) {
        this.assistant = assistant;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.newStartTime = newStartTime;
        this.newEndTime = newEndTime;
        this.course = course;
        this.room = room;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
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

    public Time getNewStartTime() {
        return newStartTime;
    }

    public void setNewStartTime(Time newStartTime) {
        this.newStartTime = newStartTime;
    }

    public Time getNewEndTime() {
        return newEndTime;
    }

    public void setNewEndTime(Time newEndTime) {
        this.newEndTime = newEndTime;
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

    @Override
    public String toString(){
        return course + " | Date: " + date + " | Start: " + startTime + " | End: " + endTime + " | Room: " + room + " | Assistant: " + assistant;
    }

}