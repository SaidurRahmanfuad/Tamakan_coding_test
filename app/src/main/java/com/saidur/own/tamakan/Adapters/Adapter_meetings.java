package com.saidur.own.tamakan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.own.tamakan.Model.ModelMeetings;
import com.saidur.own.tamakan.R;

import java.util.List;

public class Adapter_meetings extends RecyclerView.Adapter<Adapter_meetings.BookViewHolder> {
    private final Context context;
    List<ModelMeetings> aList=null;
    ClickListener listener;
    public Adapter_meetings(Context context, List<ModelMeetings> aList,ClickListener listener ) {
        this.aList = aList;
        this.context = context;
        this.listener = listener;
    }
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_meetings, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        ModelMeetings md=aList.get(position);

        holder.tv_client_name.setText("Client Name : "+md.getClient_name());
        holder.tv_date.setText("Date : "+md.getDate());
        holder.tv_company_name.setText("Company Name : "+md.getCompany_name());
        holder.tv_start_time.setText("Start Time : "+md.getStart_time());
        holder.tv_end_time.setText("End Time : "+md.getEnd_time());
        holder.iv_del.setOnClickListener(view -> {
            listener.onDeleteClick_(md);
        });
        holder.iv_update.setOnClickListener(view -> {
            listener.onUpdateClick_(md);
        });
    }

    @Override
    public int getItemCount() {
        if(aList!=null && aList.size()>0)
        {
            return aList.size();
        }
        return 0;
    }
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_client_name,tv_date,tv_company_name,tv_start_time,tv_end_time;
        ImageView iv_del,iv_update;
        public BookViewHolder(View view) {
            super(view);
            tv_client_name = view.findViewById(R.id.tv_client_name);
            tv_date = view.findViewById(R.id.tv_date);
            tv_company_name = view.findViewById(R.id.tv_company_name);
            tv_start_time = view.findViewById(R.id.tv_start_time);
            tv_end_time = view.findViewById(R.id.tv_end_time);
            iv_del = view.findViewById(R.id.iv_del);
            iv_update = view.findViewById(R.id.iv_update);
        }
    }

    public interface ClickListener{
        void onClick_(int pos,ModelMeetings data);
        void onDeleteClick_(ModelMeetings data);
        void onUpdateClick_(ModelMeetings data);
    }
}
