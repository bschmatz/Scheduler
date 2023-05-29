//Event.java
//This class represents the event entity in the database
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.entities;

import java.sql.Date;
import java.sql.Time;


public class Event{

    private int eventId;

    private Room room;

    private int adminId;

    private Course course;

    private Date eventDate;

    private Time eventStartTime;

    private Time eventEndTime;

    public Event() {
    }

    public Event (Room room, int adminId, Course course, Date eventDate, Time eventStartTime, Time eventEndTime) {
        this.room = room;
        this.adminId = adminId;
        this.course = course;
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

    public Course getCourse() {
        return course;
    }

    public void setEventName(Course eventName) {
        this.course = eventName;
    }

    public Room getEventRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return this.getEventRoom() == event.getEventRoom();
    }

    @Override
    public String toString() {
        return course + " | " + eventDate + " | " + eventStartTime + " - " + eventEndTime + " | " + room;
    }

}
