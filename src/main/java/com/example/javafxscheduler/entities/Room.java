package com.example.javafxscheduler.entities;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.example.javafxscheduler.util.sqlOperations;

@Entity
@Table(name = "rooms", schema = "uebung07", catalog = "")
public class Room implements sqlOperations {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "room_id")
    private int roomId;
    @Basic
    @Column(name = "room_name")
    private String roomName;
    @Basic
    @Column(name = "room_location")
    private String roomLocation;

    public Room() {
    }

    public Room (String roomName, String roomLocation) {
        this.roomName = roomName;
        this.roomLocation = roomLocation;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (roomId != room.roomId) return false;
        if (roomName != null ? !roomName.equals(room.roomName) : room.roomName != null) return false;
        if (roomLocation != null ? !roomLocation.equals(room.roomLocation) : room.roomLocation != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roomId;
        result = 31 * result + (roomName != null ? roomName.hashCode() : 0);
        result = 31 * result + (roomLocation != null ? roomLocation.hashCode() : 0);
        return result;
    }

    @Override
    public void save() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        session.persist(this);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        session.remove(this);
        session.getTransaction().commit();
        session.close();
    }

}
