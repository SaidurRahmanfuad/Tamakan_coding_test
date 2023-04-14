package com.saidur.own.tamakan.Interface;

import com.saidur.own.tamakan.Model.ModelMeetings;
import com.saidur.own.tamakan.Model.ModelRooms;
import com.saidur.own.tamakan.Network.Request.RQ_Login;
import com.saidur.own.tamakan.Network.Request.RQ_UpdateMeeting;
import com.saidur.own.tamakan.Network.Response.Data;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Path;

public interface IMeetings {
    interface Presenter {
        void callMeetings(String a_token);
        void callAddMeetings(String a_token, Map<String,String> mapparam);
        void callMeetingRooms(String a_token);
        void callDeleteMeeting(String a_token,int meeting);
        void callUpdateMeeting(String a_token,int meeting,RQ_UpdateMeeting body);
    }
    interface View {
        void OnSuccess(List<ModelMeetings> data);
        void OnError(String message);
        void viewSaveSuccess(String message);

        void OnSuccessRooms(List<ModelRooms> data);
        void OnErrorRooms(String message);

        void OnMeetingDeleted(String message);
        void OnMeetingUpdated(String message);
    }
}
