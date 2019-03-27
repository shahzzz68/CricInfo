package com.example.shahz.cricinfo.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.Dialogs.Dialog_Notificaiton_detail;
import com.example.shahz.cricinfo.model_class.Notification_data;

import java.util.List;

public class Notification_Recycler_Adapter extends RecyclerView.Adapter<Notification_Recycler_Adapter.ViewHolder>{

    List<Notification_data> notificationData;
    Context context;
    FragmentManager fragmentManager;

    public Notification_Recycler_Adapter(List<Notification_data> notificationData, Context context, FragmentManager fragmentManager) {
        this.notificationData = notificationData;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_notification_itemview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String getMsg=notificationData.get(position).getMsgTitle();
        holder.title.setText(getMsg);

        final String getBody=notificationData.get(position).getMsgBody();
        holder.body.setText(getBody);

        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ((FragmentActivity)context).recreate();

                return true;
            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle =new Bundle();
                bundle.putString("MsgTitle",getMsg);
                bundle.putString("MsgBody",getBody);

                Dialog_Notificaiton_detail detail= new Dialog_Notificaiton_detail();
                detail.setArguments(bundle);
                detail.show(((FragmentActivity)context).getSupportFragmentManager(),"manager");


            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView body;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.notyTitle);
            body=itemView.findViewById(R.id.notyBody);
            constraintLayout=itemView.findViewById(R.id.notificationParent);

        }
    }
}
