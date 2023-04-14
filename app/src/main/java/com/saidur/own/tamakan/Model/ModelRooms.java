package com.saidur.own.tamakan.Model;

public class ModelRooms {
    int id;
    String room_name;

    public ModelRooms() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    @Override
    public String toString() {
        return room_name;
    }
}
