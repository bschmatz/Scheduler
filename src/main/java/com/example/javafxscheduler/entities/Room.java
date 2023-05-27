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
