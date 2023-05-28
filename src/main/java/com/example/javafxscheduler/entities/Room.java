//Room.java
//This class represents a room in the database
//Author: Benedikt Schmatz
//Last changed: 28.05.2023

package com.example.javafxscheduler.entities;

public class Room {
    private String roomName;

    public Room() {
    }

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return roomName;
    }

}
