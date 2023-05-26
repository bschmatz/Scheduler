//EventRegistration.java
//This class represents the event registration entity in the database
//Author: Benedikt Schmatz
//Last changed: 26.05.2023

package com.example.javafxscheduler.entities;

public class EventRegistration{

    private int registrationId;

    private Integer eventId;

    private Integer studentId;

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public EventRegistration() {
    }

    public EventRegistration(Integer eventId, Integer studentId) {
        this.eventId = eventId;
        this.studentId = studentId;
    }

}
