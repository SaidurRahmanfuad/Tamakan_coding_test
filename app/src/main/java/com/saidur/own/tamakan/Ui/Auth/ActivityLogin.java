package com.saidur.own.tamakan.Ui.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.saidur.own.tamakan.DB.DBCrudHelper;
import com.saidur.own.tamakan.Interface.ILogin;
import com.saidur.own.tamakan.MainActivity;
import com.saidur.own.tamakan.Model.ModelSession;
import com.saidur.own.tamakan.Network.Request.RQ_Login;
import com.saidur.own.tamakan.Network.Response.Data;
import com.saidur.own.tamakan.Presenter.P_Login;
import com.saidur.own.tamakan.Utils.ConnectionInfo;
import com.saidur.own.tamakan.Utils.Msg;
import com.saidur.own.tamakan.databinding.ActivityLoginBinding;

public class ActivityLogin extends AppCompatActivity implements ILogin.View {
ActivityLoginBinding binding;
ProgressDialog pd;
P_Login presenter;
DBCrudHelper dbCrudHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter=new P_Login(ActivityLogin.this,this);
        dbCrudHelper=new DBCrudHelper(ActivityLogin.this);
        //Check User Logged in or not
        boolean isExist= dbCrudHelper.checkSessonExist();
        if(isExist)
        {
            Intent in=new Intent(ActivityLogin.this, MainActivity.class);
            startActivity(in);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
        pd=new ProgressDialog(ActivityLogin.this);
        pd.setMessage("Authenticating...");
        binding.btnLogin.setOnClickListener(view -> {
            if(TextUtils.isEmpty(binding.inputEmail.getText().toString().trim()))
            {
                Msg._warning_CustomMessage(binding.getRoot(),"Email Required!!");
                return;
            } else if(TextUtils.isEmpty(binding.inputPassword.getText().toString().trim()))
            {
                Msg._warning_CustomMessage(binding.getRoot(),"Password Required!!");
                return;
            }else {
              if(ConnectionInfo.isConnected(ActivityLogin.this))
              {
                  RQ_Login rq=new RQ_Login(binding.inputEmail.getText().toString().trim(),
                          binding.inputPassword.getText().toString().trim());
                  pd.show();
                  presenter.callLogin(rq);
              }
            }
        });
    }

    @Override
    public void OnSuccess(Data data) {
        if(pd!=null || pd.isShowing())
        {
            pd.dismiss();
        }
        if(data!=null)
        {
            ModelSession session=new ModelSession();
            session.setUserId(data.getUser().getId());
            session.setName(data.getUser().getName());
            session.setEmail(data.getUser().getEmail());
            session.setDepartment(data.getUser().getDepartment());
            session.setType(data.getUser().getType());
            session.setCreated_at(data.getUser().getCreated_at());
            session.setUpdated_at(data.getUser().getUpdated_at());
            session.setAccess_token(data.getAccess_token());
            session.setToken_type(data.getToken_type());
            session.setExpires_at(data.getExpires_at());
            boolean isDone= dbCrudHelper.InsertSessionInfo(session);
            Msg._success_CustomMessage(binding.getRoot(),"Login Successful");
            if(isDone)
            {
                Intent in=new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(in);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }


        }
    }

    @Override
    public void OnError(String message) {
        if(pd!=null || pd.isShowing())
        {
            pd.dismiss();
            Msg._success_CustomMessage(binding.getRoot(),message);
        }

    }
}