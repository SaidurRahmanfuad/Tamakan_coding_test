package com.saidur.own.tamakan.Network;


import com.saidur.own.tamakan.Network.Request.RQ_Login;
import com.saidur.own.tamakan.Network.Request.RQ_UpdateMeeting;
import com.saidur.own.tamakan.Network.Response.Rsp_AddMeetings;
import com.saidur.own.tamakan.Network.Response.Rsp_Login;
import com.saidur.own.tamakan.Network.Response.Rsp_Meetings;
import com.saidur.own.tamakan.Network.Response.Rsp_Rooms;
import com.saidur.own.tamakan.Network.Response.Rsp_UpMeeting;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiSource {
    @Headers({"Accept: application/json"})
    @POST("/api/login")
    Call<Rsp_Login> callLogin(@Body RQ_Login login);

    @GET("/api/meetings")
    Call<Rsp_Meetings> callMeetings(@Header("Authorization") String access_token);

    @POST("/api/store-meetings")
    Call<Rsp_AddMeetings> callAddMeeting(@Header("Authorization") String access_token, @QueryMap Map<String,String> mapparam);

    @GET("/api/rooms")
    Call<Rsp_Rooms> callrooms(@Header("Authorization") String access_token);

    @DELETE("/api/delete-meeting/{meeting}")
    Call<Rsp_Rooms> callDelMeeting(@Header("Authorization") String access_token, @Path("meeting") int meeting);

    @PUT("/api/update-meeting/{meeting}")
    Call<Rsp_UpMeeting> callUpdateMeeting(@Header("Authorization") String access_token, @Path("meeting") int meeting
    , @Body RQ_UpdateMeeting body);


}


