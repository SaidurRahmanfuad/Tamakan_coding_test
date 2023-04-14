package com.saidur.own.tamakan.Presenter;


import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.saidur.own.tamakan.Interface.ILogin;
import com.saidur.own.tamakan.Network.ApiSource;
import com.saidur.own.tamakan.Network.Request.RQ_Login;
import com.saidur.own.tamakan.Network.Response.Rsp_Login;
import com.saidur.own.tamakan.Network.RetrofitClientInstance;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class P_Login implements ILogin.Presenter {
    ILogin.View view;
    Context context;
    public P_Login(Context context, ILogin.View view) {
        this.context = context;
        this.view = view;
    }
    @Override
    public void callLogin(RQ_Login rq) {
        ApiSource service = RetrofitClientInstance.getRetrofitInstance().create(ApiSource.class);
        Call<Rsp_Login> call = service.callLogin(rq);
        call.enqueue(new Callback<Rsp_Login>() {
            @Override
            public void onResponse(@NonNull Call<Rsp_Login> call, @NonNull Response<Rsp_Login> response) {
                if(response.code()==200 && response.body().getData()!=null)
                {
                    view.OnSuccess(response.body().getData());
                }
                else {
                    view.OnError("Email & Password Not Matched!!");
                }

            }
            @Override
            public void onFailure(@NonNull Call<Rsp_Login> call, @NonNull Throwable t) {
                if(t instanceof SocketTimeoutException){
                    view.OnError("Slow Connection Detected. Please try again");
                }else{
                    view.OnError("Something went wrong... Please try again");
                }
            }
        });
    }

}
