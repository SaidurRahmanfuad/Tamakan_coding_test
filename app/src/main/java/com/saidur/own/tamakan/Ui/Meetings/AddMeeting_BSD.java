package com.saidur.own.tamakan.Ui.Meetings;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saidur.own.tamakan.DB.DBCrudHelper;
import com.saidur.own.tamakan.Interface.IMeetings;
import com.saidur.own.tamakan.Model.ModelMeetings;
import com.saidur.own.tamakan.Model.ModelRooms;
import com.saidur.own.tamakan.Model.ModelSession;
import com.saidur.own.tamakan.Network.Request.RQ_UpdateMeeting;
import com.saidur.own.tamakan.Presenter.P_Meetings;
import com.saidur.own.tamakan.R;
import com.saidur.own.tamakan.Utils.Const;
import com.saidur.own.tamakan.Utils.UtilityHelper;
import com.saidur.own.tamakan.databinding.BottomSheetAddMeetingBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class AddMeeting_BSD extends BottomSheetDialogFragment implements View.OnClickListener, IMeetings.View {
    BottomSheetAddMeetingBinding binding;
    String type = "";
    AddMeeting_BSD.BottomSheetListener mListener;
    public static final String TAG = "ActionBottomDialog";
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    P_Meetings presenter;
    ModelMeetings data;
    public static AddMeeting_BSD newInstance() {
        return new AddMeeting_BSD();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), R.style.MyTransparentBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetAddMeetingBinding.inflate(getLayoutInflater());
        dbCrudHelper = new DBCrudHelper(requireActivity());
        session = dbCrudHelper.getSession();
        presenter = new P_Meetings(requireActivity(), this);
        presenter.callMeetingRooms(session.getAccess_token());

        if(Const.From.equals("AdapterUpdate"))
        {
            Gson gson = new Gson();
            Bundle mArgs = getArguments();
            data = gson.fromJson(mArgs.getString("Data"), new TypeToken<ModelMeetings>() {
            }.getType());
            binding.tvTag.setText("Update Meeting");
            binding.btnSave.setVisibility(View.GONE);
            binding.btnUpdate.setVisibility(View.VISIBLE);
            binding.etCompanyName.setText(data.getCompany_name());
            binding.etClientName.setText(data.getClient_name());
            binding.tvDate.setText(data.getDate());
            binding.tvStime.setText(data.getStart_time());
            binding.tvEtime.setText(data.getEnd_time());
            binding.etDesc.setText(data.getDescription());

        }


        binding.ivDatepick.setOnClickListener(view -> UtilityHelper._datePicker(binding.tvDate, requireContext()));
        binding.ivSTime.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvStime, requireContext()));
        binding.ivETime.setOnClickListener(view -> UtilityHelper._timePicker(binding.tvEtime, requireContext()));
        binding.btnSave.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
        binding.btnClose.setOnClickListener(this);

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                String client_name = binding.etClientName.getText().toString().trim();
                String company_name = binding.etCompanyName.getText().toString().trim();
                String date = binding.tvDate.getText().toString().trim();
                String start_time = binding.tvStime.getText().toString().trim();
                String end_time = binding.tvEtime.getText().toString().trim();
                String description = binding.etDesc.getText().toString().trim();
                boolean isDate = true;
                boolean isComp = true;
                boolean isName = true;
                boolean issTime = true;
                boolean isetime = true;
                boolean isDesc = true;
                if (TextUtils.isEmpty(client_name)) {
                    binding.etClientName.setError("Required");
                    isName = false;
                    return;
                }
                if (TextUtils.isEmpty(company_name)) {
                    binding.etCompanyName.setError("Required");
                    isComp = false;
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    binding.tvDate.setError("Required");
                    isDate = false;
                    return;
                }
                if (TextUtils.isEmpty(start_time)) {
                    binding.tvStime.setError("Required");
                    issTime = false;
                    return;
                }
                if (TextUtils.isEmpty(end_time)) {
                    binding.tvEtime.setError("Required");
                    isetime = false;
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    binding.etDesc.setError("Required");
                    isDesc = false;
                    return;
                }

                ModelRooms room=(ModelRooms)binding.spinnerRoom.getSelectedItem();
                int roomId= room.getId();
                if (isDate && isComp && isName && issTime && isetime && isDesc) {
                    Map<String, String> req = new HashMap<>();
                    req.put("date", binding.tvDate.getText().toString());
                    req.put("room_id", String.valueOf(roomId));
                    req.put("start_time", binding.tvStime.getText().toString());
                    req.put("user_id", String.valueOf(session.getUserId()));
                    req.put("company_name", binding.etCompanyName.getText().toString());
                    req.put("end_time", binding.tvEtime.getText().toString());
                    req.put("description", binding.etDesc.getText().toString());
                    req.put("client_name", binding.etClientName.getText().toString());

                    mListener.SaveMeetingClick(req);
                    dismiss();
                }

                break;
            case R.id.btn_update:
                boolean isDate1 = true;
                boolean isComp1 = true;
                boolean isName1 = true;
                boolean issTime1 = true;
                boolean isetime1 = true;
                boolean isDesc1 = true;
                if (TextUtils.isEmpty(binding.etClientName.getText().toString())) {
                    binding.etClientName.setError("Required");
                    isName = false;
                    return;
                }
                if (TextUtils.isEmpty(binding.etCompanyName.getText().toString().trim())) {
                    binding.etCompanyName.setError("Required");
                    isComp = false;
                    return;
                }
                if (TextUtils.isEmpty(binding.tvDate.getText().toString().trim())) {
                    binding.tvDate.setError("Required");
                    isDate = false;
                    return;
                }
                if (TextUtils.isEmpty(binding.tvStime.getText().toString().trim())) {
                    binding.tvStime.setError("Required");
                    issTime = false;
                    return;
                }
                if (TextUtils.isEmpty(binding.tvEtime.getText().toString().trim())) {
                    binding.tvEtime.setError("Required");
                    isetime = false;
                    return;
                }
                if (TextUtils.isEmpty(binding.etDesc.getText().toString().trim())) {
                    binding.etDesc.setError("Required");
                    isDesc = false;
                    return;
                }
                if (isDate1 && isComp1 && isName1 && issTime1 && isetime1 && isDesc1) {
                    RQ_UpdateMeeting up=new RQ_UpdateMeeting();
                    String today=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    up.setClient_name(binding.etClientName.getText().toString().trim());
                    up.setCompany_name(binding.etCompanyName.getText().toString().trim());
                    up.setDate(binding.tvDate.getText().toString().trim());
                    up.setStart_time(binding.tvStime.getText().toString().trim());
                    up.setEnd_time(binding.tvEtime.getText().toString().trim());
                    up.setDescription(binding.etDesc.getText().toString().trim());
                    ModelRooms rooms=(ModelRooms)binding.spinnerRoom.getSelectedItem();
                    int roomIds= rooms.getId();
                    up.setRoom_id(roomIds);
                    up.setUser_id(session.getUserId());
                    up.setCreated_at(today);
                    up.setUpdated_at(today);
                    mListener.UpdateMeetingClick(data.getId(),up);
                    dismiss();
                }

                break;
            case R.id.btn_close:
                dismiss();
                break;

        }
    }

    @Override
    public void OnSuccess(List<ModelMeetings> data) {

    }

    @Override
    public void OnError(String message) {

    }

    @Override
    public void viewSaveSuccess(String message) {

    }

    @Override
    public void OnSuccessRooms(List<ModelRooms> data) {
        if (data != null) {
            // ArrayAdapter<ModelRooms> adapter=new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item,data);
            ArrayAdapter<ModelRooms> adapter = new ArrayAdapter<>(requireActivity(), R.layout.dropdown_item, R.id.tv_data, data);
            binding.spinnerRoom.setAdapter(adapter);
        }
    }

    @Override
    public void OnErrorRooms(String message) {

    }

    @Override
    public void OnMeetingDeleted(String message) {

    }

    @Override
    public void OnMeetingUpdated(String message) {

    }


    public interface BottomSheetListener {
        void SaveMeetingClick(Map<String, String> req);
        void UpdateMeetingClick(int id,RQ_UpdateMeeting req);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BottomSheetListener) {
            mListener = (BottomSheetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }

    }
}



