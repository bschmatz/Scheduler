//Event.java
//This class represents the event entity in the database
//Author: Benedikt Schmatz
//Last changed: 19.05.2023

package com.example.javafxscheduler.entities;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Time;


public class Event {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;

    private Integer roomId;

    private Integer adminId;

    private String eventName;

    private Date eventDate;

    private Time eventStartTime;

    private Time eventEndTime;

    public Event() {
    }

    public Event (Integer roomId, Integer adminId, String eventName, Date eventDate, Time eventStartTime, Time eventEndTime) {
        this.roomId = roomId;
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

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (eventId != event.eventId) return false;
        if (roomId != null ? !roomId.equals(event.roomId) : event.roomId != null) return false;
        if (adminId != null ? !adminId.equals(event.adminId) : event.adminId != null) return false;
        if (eventName != null ? !eventName.equals(event.eventName) : event.eventName != null) return false;
        if (eventDate != null ? !eventDate.equals(event.eventDate) : event.eventDate != null) return false;
        if (eventStartTime != null ? !eventStartTime.equals(event.eventStartTime) : event.eventStartTime != null)
            return false;
        if (eventEndTime != null ? !eventEndTime.equals(event.eventEndTime) : event.eventEndTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventId;
        result = 31 * result + (roomId != null ? roomId.hashCode() : 0);
        result = 31 * result + (adminId != null ? adminId.hashCode() : 0);
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (eventDate != null ? eventDate.hashCode() : 0);
        result = 31 * result + (eventStartTime != null ? eventStartTime.hashCode() : 0);
        result = 31 * result + (eventEndTime != null ? eventEndTime.hashCode() : 0);
        return result;
    }

    public void save() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/uebung07?user=bene&password=password")){
            System.out.println("Connection established");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {

    }

    @Override
    public String toString() {
        return "Event {" +
                "eventId=" + eventId +
                ", roomId=" + roomId +
                ", adminId=" + adminId +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", eventStartTime=" + eventStartTime +
                ", eventEndTime=" + eventEndTime +
                '}';
    }

}
