package com.saidur.own.tamakan.Interface;

import com.saidur.own.tamakan.Network.Request.RQ_Login;
import com.saidur.own.tamakan.Network.Response.Data;

public interface ILogin {
    interface Presenter {
        void callLogin(RQ_Login rq);
    }
    interface View {
        void OnSuccess(Data data);
        void OnError(String message);
    }
}
