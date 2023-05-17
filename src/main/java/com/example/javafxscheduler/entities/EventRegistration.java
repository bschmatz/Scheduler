package com.example.javafxscheduler.entities;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Entity
@Table(name = "event_registrations", schema = "uebung07", catalog = "")
public class EventRegistration{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "registration_id")
    private int registrationId;
    @Basic
    @Column(name = "event_id")
    private Integer eventId;
    @Basic
    @Column(name = "student_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventRegistration that = (EventRegistration) o;

        if (registrationId != that.registrationId) return false;
        if (eventId != null ? !eventId.equals(that.eventId) : that.eventId != null) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = registrationId;
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        return result;
    }

    public void save(User user) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        session.persist(this);
        session.getTransaction().commit();
        session.close();
    }

    public void delete() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        session.remove(this);
        session.getTransaction().commit();
        session.close();
    }
}
