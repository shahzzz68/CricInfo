package com.example.shahz.cricinfo.Adapters;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;


import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.model_class.Players_data;
import com.example.shahz.cricinfo.fragments.Players_detail_fragment;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Players_Recycler_Adapter extends RecyclerView.Adapter<Players_Recycler_Adapter.ViewHolder> {


    List<Players_data> playersData;
    Context context;
    FragmentManager fragmentManager;

    public Players_Recycler_Adapter(List<Players_data> playersData,Context context,FragmentManager manager) {
        this.playersData = playersData;
        this.context=context;
        this.fragmentManager=manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_players_detail_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final String name=playersData.get(position).getName();
        holder.setName(name);

        final String type=playersData.get(position).getType();
        holder.setType(type);

        final String style= playersData.get(position).getStyle();
        holder.setStyle(style);

        final String team= playersData.get(position).getTeam();
        holder.setTeam(team);

        String image= playersData.get(position).getImageUrl();
        try {
            holder.setProfileImg(image);
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage().toString()+" No image path found for some images", Toast.LENGTH_LONG).show();
        }



        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, playersData.get(position).getName(), Toast.LENGTH_SHORT).show();

                Bundle bundle=new Bundle();
                bundle.putString("name" ,name);
                bundle.putString("type" ,type);
                bundle.putString("style" ,style);
                bundle.putString("team" ,team);

                Players_detail_fragment playersDetailFragment=new Players_detail_fragment();
                playersDetailFragment.setArguments(bundle);
                playersDetailFragment.show(((FragmentActivity)context).getSupportFragmentManager(),"string");



            }
        });




    }

    @Override
    public int getItemCount() {
        return playersData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View holderView;
        TextView name;
        TextView type;
        TextView style;
        TextView team;
        CircularImageView profileImg;
        ConstraintLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            holderView=itemView;
            parentLayout=itemView.findViewById(R.id.parentLayout);
        }

        public void setName(String string)
        {
            name=holderView.findViewById(R.id.playerNameTxt);
            name.setText(string);
        }

        public void setType(String string)
        {
            type=holderView.findViewById(R.id.playerTypeTxt);
            type.setText(string);
        }

        public void setStyle(String string)
        {
            style=holderView.findViewById(R.id.playerStyleTxt);
            style.setText(string);
        }

        public void setTeam(String string)
        {
            team=holderView.findViewById(R.id.playerTeamTxt);
            team.setText(string);

        }

        public void setProfileImg(String s)
        {

                profileImg=holderView.findViewById(R.id.playerProfileImg);
                Picasso.get()
                        .load(s)
                        .centerCrop()
                        .placeholder(R.drawable.default_profile_image)
                        .fit()
                        .centerCrop()
                        .into(profileImg);


        }

    }
}
