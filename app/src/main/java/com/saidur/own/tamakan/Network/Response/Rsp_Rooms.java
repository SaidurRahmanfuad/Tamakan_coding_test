package com.saidur.own.tamakan.Network.Response;

import com.saidur.own.tamakan.Model.ModelRooms;

import java.util.List;

public class Rsp_Rooms {
    List<ModelRooms> data;
    String message;

    public Rsp_Rooms() {
    }

    public List<ModelRooms> getData() {
        return data;
    }

    public void setData(List<ModelRooms> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
