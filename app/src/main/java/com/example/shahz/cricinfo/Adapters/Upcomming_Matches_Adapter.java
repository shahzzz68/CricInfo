package com.example.shahz.cricinfo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.model_class.MatchesData;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class Upcomming_Matches_Adapter extends RecyclerView.Adapter<Upcomming_Matches_Adapter.ViewHolder> {

    private List<MatchesData> matchesDataList;
    Context context;

    public Upcomming_Matches_Adapter(List<MatchesData> matchesData)
    {
        this.matchesDataList=matchesData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_match_view,parent,false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String descText= matchesDataList.get(position).getDescription();
        holder.setDecsText(descText);

        String team1= matchesDataList.get(position).getTeam1();
        holder.setTeam1(team1);

        String team2= matchesDataList.get(position).getTeam2();
        holder.setTeam2(team2);


        String date= matchesDataList.get(position).getDate();
        holder.setDate(date);

        String time= matchesDataList.get(position).getTime();
        holder.setTime(time);


//            long millisecond = matchesDataList.get(position).getTimeStamp().getTime();
//            String dateformat = android.text.format
//                    .DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
//            holder.setDate(dateformat);


    }

    @Override
    public int getItemCount() {
        return matchesDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        View mVIew;
        TextView description;
        TextView team1;
        TextView team2;
        TextView time;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            mVIew= itemView;
        }

        public void setDecsText(String text)
        {
            description=mVIew.findViewById(R.id.desc);
            description.setText(text);
        }

        public void setTeam1(String text)
        {
            team1=mVIew.findViewById(R.id.team1);
            team1.setText(text);
        }

        public void setTeam2(String text)
        {
            team2=mVIew.findViewById(R.id.team2);
            team2.setText(text);
        }

        public void setDate(String text)
        {
            date=mVIew.findViewById(R.id.date);
            date.setText(text);
        }

        public void setTime(String text)
        {
            time=mVIew.findViewById(R.id.time);
            time.setText(text);
        }

        public void toast(String string)
        {
            Toast.makeText(mVIew.getContext(), string, Toast.LENGTH_SHORT).show();
        }

    }

}
