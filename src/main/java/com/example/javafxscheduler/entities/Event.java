//Event.java
//This class represents the event entity in the database
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.entities;

import com.example.javafxscheduler.util.TimeUtil;

import java.sql.Date;
import java.sql.Time;


public class Event{

    private int eventId;

    private String roomName;

    private int adminId;

    private String eventName;

    private Date eventDate;

    private Time eventStartTime;

    private Time eventEndTime;

    public Event() {
    }

    public Event (String roomName, int adminId, String eventName, Date eventDate, Time eventStartTime, Time eventEndTime) {
        this.roomName = roomName;
        this.adminId = adminId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventRoom() {
        return roomName;
    }

    public void setEventRoom(String roomName) {
        this.roomName = roomName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Time getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Time eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public Time getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Time eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    @Override
    public String toString() {
        return eventName + " | " + eventDate + " | " + eventStartTime + " - " + eventEndTime + " | " + roomName;
    }

}
