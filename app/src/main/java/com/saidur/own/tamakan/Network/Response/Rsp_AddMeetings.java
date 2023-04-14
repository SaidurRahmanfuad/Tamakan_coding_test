package com.saidur.own.tamakan.Network.Response;

import com.saidur.own.tamakan.Model.ModelMeetings;
public class Rsp_AddMeetings {
    ModelMeetings data;
    String message;

    public Rsp_AddMeetings() {
    }

    public ModelMeetings getData() {
        return data;
    }

    public void setData(ModelMeetings data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
