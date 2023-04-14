package com.saidur.own.tamakan.Presenter;


import android.content.Context;

import androidx.annotation.NonNull;

import com.saidur.own.tamakan.Interface.ILogin;
import com.saidur.own.tamakan.Interface.IMeetings;
import com.saidur.own.tamakan.Network.ApiSource;
import com.saidur.own.tamakan.Network.Request.RQ_Login;
import com.saidur.own.tamakan.Network.Request.RQ_UpdateMeeting;
import com.saidur.own.tamakan.Network.Response.Rsp_AddMeetings;
import com.saidur.own.tamakan.Network.Response.Rsp_Login;
import com.saidur.own.tamakan.Network.Response.Rsp_Meetings;
import com.saidur.own.tamakan.Network.Response.Rsp_Rooms;
import com.saidur.own.tamakan.Network.Response.Rsp_UpMeeting;
import com.saidur.own.tamakan.Network.RetrofitClientInstance;

import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class P_Meetings implements IMeetings.Presenter {
    IMeetings.View view;
    Context context;
    public P_Meetings(Context context, IMeetings.View view) {
        this.context = context;
        this.view = view;
    }
    @Override
    public void callMeetings(String a_token) {
        try {
            ApiSource service = RetrofitClientInstance.getRetrofitInstance().create(ApiSource.class);
            Call<Rsp_Meetings> call = service.callMeetings("Bearer "+a_token);
            call.enqueue(new Callback<Rsp_Meetings>() {
                @Override
                public void onResponse(@NonNull Call<Rsp_Meetings> call, @NonNull Response<Rsp_Meetings> response) {
                    if(response.code()==200 && response.body().getData()!=null)
                    {
                        view.OnSuccess(response.body().getData());
                    }
                    else {
                        view.OnError("Data Not Found!!");
                    }

                }
                @Override
                public void onFailure(@NonNull Call<Rsp_Meetings> call, @NonNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        view.OnError("Slow Connection Detected. Please try again");
                    }else{
                        view.OnError("Something went wrong... Please try again");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void callAddMeetings(String a_token, Map<String,String> req) {
        try {
            ApiSource service = RetrofitClientInstance.getRetrofitInstance().create(ApiSource.class);
            Call<Rsp_AddMeetings> call = service.callAddMeeting("Bearer "+a_token,req);
            call.enqueue(new Callback<Rsp_AddMeetings>() {
                @Override
                public void onResponse(@NonNull Call<Rsp_AddMeetings> call, @NonNull Response<Rsp_AddMeetings> response) {

                    if(response.body().getData()!=null && response.body().getMessage()!=null)
                    {
                        view.viewSaveSuccess("Room add successfully");
                    }else {
                        view.OnError("Room booked by another user");
                    }


                }
                @Override
                public void onFailure(@NonNull Call<Rsp_AddMeetings> call, @NonNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        view.OnError("Slow Connection Detected. Please try again");
                    }else{
                        view.OnError("Something went wrong... Please try again");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            view.OnError(e.getMessage().toString());

        }
    }

    @Override
    public void callMeetingRooms(String a_token) {
        try {
            ApiSource service = RetrofitClientInstance.getRetrofitInstance().create(ApiSource.class);
            Call<Rsp_Rooms> call = service.callrooms("Bearer "+a_token);
            call.enqueue(new Callback<Rsp_Rooms>() {
                @Override
                public void onResponse(@NonNull Call<Rsp_Rooms> call, @NonNull Response<Rsp_Rooms> response) {
                    if(response.code()==200 && response.body().getData()!=null)
                    {
                        view.OnSuccessRooms(response.body().getData());
                    }
                    else {
                        view.OnErrorRooms("Data Not Found!!");
                    }

                }
                @Override
                public void onFailure(@NonNull Call<Rsp_Rooms> call, @NonNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        view.OnError("Slow Connection Detected. Please try again");
                    }else{
                        view.OnError("Something went wrong... Please try again");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callDeleteMeeting(String a_token, int meeting) {
        try {
            ApiSource service = RetrofitClientInstance.getRetrofitInstance().create(ApiSource.class);
            Call<Rsp_Rooms> call = service.callDelMeeting("Bearer "+a_token,meeting);
            call.enqueue(new Callback<Rsp_Rooms>() {
                @Override
                public void onResponse(@NonNull Call<Rsp_Rooms> call, @NonNull Response<Rsp_Rooms> response) {
                    if(response.code()==200)
                    {
                        view.OnMeetingDeleted(response.body().getMessage());
                    }
                    else {
                        view.OnErrorRooms("Not Deleted!!");
                    }

                }
                @Override
                public void onFailure(@NonNull Call<Rsp_Rooms> call, @NonNull Throwable t) {
                    if(t instanceof SocketTimeoutException){
                        view.OnError("Slow Connection Detected. Please try again");
                    }else{
                        view.OnError("Something went wrong... Please try again");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callUpdateMeeting(String a_token, int meeting, RQ_UpdateMeeting body) {
        try {
            ApiSource service = RetrofitClientInstance.getRetrofitInstance().create(ApiSource.class);
            Call<Rsp_UpMeeting> call = service.callUpdateMeeting("Bearer "+a_token,meeting,body);
            call.enqueue(new Callback<Rsp_UpMeeting>() {
                @Override
                public void onResponse(@NonNull Call<Rsp_UpMeeting> call, @NonNull Response<Rsp_UpMeeting> response) {
                    if(response.body().isData())
                    {
                        view.OnMeetingUpdated("Room add successfully");
                    }else {
                        view.OnError("Room booked by another user");
                    }

                }
                @Override
                public void onFailure(@NonNull Call<Rsp_UpMeeting> call, @NonNull Throwable t) {
                    System.out.println(t.getMessage().toString());
                    if(t instanceof SocketTimeoutException){
                        view.OnError("Slow Connection Detected. Please try again");
                    }else{
                        view.OnError("Something went wrong... Please try again");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
