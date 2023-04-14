package com.saidur.own.tamakan.Network.Response;

import com.saidur.own.tamakan.Model.ModelMeetings;

import java.util.List;

public class Rsp_Meetings {
    List<ModelMeetings> data;
    String message;

    public Rsp_Meetings() {
    }

    public List<ModelMeetings> getData() {
        return data;
    }

    public void setData(List<ModelMeetings> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
