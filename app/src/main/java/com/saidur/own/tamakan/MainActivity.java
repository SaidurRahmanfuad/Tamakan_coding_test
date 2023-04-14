package com.saidur.own.tamakan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.saidur.own.tamakan.Adapters.Adapter_meetings;
import com.saidur.own.tamakan.DB.DBCrudHelper;
import com.saidur.own.tamakan.Interface.IMeetings;
import com.saidur.own.tamakan.Model.ModelMeetings;
import com.saidur.own.tamakan.Model.ModelRooms;
import com.saidur.own.tamakan.Model.ModelSession;
import com.saidur.own.tamakan.Network.Request.RQ_UpdateMeeting;
import com.saidur.own.tamakan.Presenter.P_Meetings;
import com.saidur.own.tamakan.Ui.Auth.ActivityLogin;
import com.saidur.own.tamakan.Ui.Meetings.AddMeeting_BSD;
import com.saidur.own.tamakan.Utils.ConnectionInfo;
import com.saidur.own.tamakan.Utils.Const;
import com.saidur.own.tamakan.Utils.Msg;
import com.saidur.own.tamakan.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IMeetings.View, Adapter_meetings.ClickListener,
        AddMeeting_BSD.BottomSheetListener{
    ActivityMainBinding binding;
    P_Meetings presentar;
    DBCrudHelper dbCrudHelper;
    ModelSession session;
    ProgressDialog pd;
    Adapter_meetings adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbCrudHelper = new DBCrudHelper(MainActivity.this);
        presentar = new P_Meetings(MainActivity.this, this);
        session = dbCrudHelper.getSession();

        binding.Username.setText(session.getName());
        binding.department.setText(session.getDepartment());
        binding.email.setText(session.getEmail());

        pd = new ProgressDialog(MainActivity.this);
        if (ConnectionInfo.isConnected(this)) {
            pd.setMessage("Data Loading...");
            pd.show();
            //Call All Meeting api
            presentar.callMeetings(session.getAccess_token());
        }else {
            Msg._warning_CustomMessage(binding.getRoot(),"No Internet!!");
        }

        binding.addMeeting.setOnClickListener(view -> {
            Const.From="Main";
            AddMeeting_BSD bottomSheetDialog = AddMeeting_BSD.newInstance();
            bottomSheetDialog.show(getSupportFragmentManager(),AddMeeting_BSD.TAG);
        });
        binding.llLogout.setOnClickListener(view -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit App")
                    .setMessage("Are you sure you want to Log out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean isDone=dbCrudHelper._deleteAllFromTableBolean("tblSession");
                        if(isDone)
                        {
                            dbCrudHelper._deleteAllFromTable("tblInitTable");
                            Intent i = new Intent(MainActivity.this, ActivityLogin.class);
                            startActivity(i);
                            finish();
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    //Show Meeting List
    @Override
    public void OnSuccess(List<ModelMeetings> data) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        if (data != null) {
            adapter = new Adapter_meetings(MainActivity.this, data, this);
            binding.rvMeetings.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
            binding.rvMeetings.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnError(String message) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        Msg._error_CustomMessage(binding.getRoot(),message);
    }

    @Override
    public void onClick_(int pos, ModelMeetings data) {

    }

    //Delete Meeting
    @Override
    public void onDeleteClick_(ModelMeetings data) {
        pd.setMessage("Deleting Please wait...");
        pd.show();
        presentar.callDeleteMeeting(session.getAccess_token(),data.getId());
    }
    //Pass Data to view for Updating Meeting
    @Override
    public void onUpdateClick_(ModelMeetings data) {
        try {
            Const.From="AdapterUpdate";
            Gson gson=new Gson();
            Bundle bundle = new Bundle();
            String vlu=gson.toJson(data);
            bundle.putString("Data", vlu);

            AddMeeting_BSD bottomSheetDialog = AddMeeting_BSD.newInstance();
            bottomSheetDialog.setArguments(bundle);
            bottomSheetDialog.show(getSupportFragmentManager(),AddMeeting_BSD.TAG);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage().toString());
            Toast.makeText(this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //Save Meeting
    @Override
    public void SaveMeetingClick(Map<String,String> req) {
        pd.setMessage("Please wait...");
        pd.show();
        presentar.callAddMeetings(session.getAccess_token(),req);
    }

    //Update meeting Api Call
    @Override
    public void UpdateMeetingClick(int id,RQ_UpdateMeeting req) {
        pd.setMessage("Updating Please wait...");
        pd.show();
        presentar.callUpdateMeeting(session.getAccess_token(),id,req);
    }

    //Show api response success Dialog
    @Override
    public void viewSaveSuccess(String msg) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        dialogSuccess(msg);
    }

    @Override
    public void OnSuccessRooms(List<ModelRooms> data) {

    }

    @Override
    public void OnErrorRooms(String message) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        Msg._error_CustomMessage(binding.getRoot(),message);
    }
//Show Meeting Delete Dialog
    @Override
    public void OnMeetingDeleted(String message) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        dialogSuccess(message);
    }
    //Show Meeting Update Dialog
    @Override
    public void OnMeetingUpdated(String message) {
        if (pd != null || pd.isShowing()) {
            pd.dismiss();
        }
        dialogSuccess(message);
    }
    //Dialog for showing Response Message
    private void dialogSuccess(String msg) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Success")
                .setMessage(msg)
                .setPositiveButton("OK", (dialog, which) -> {
                    presentar.callMeetings(session.getAccess_token());
                    dialog.dismiss();
                }).setCancelable(false).show();
    }
}