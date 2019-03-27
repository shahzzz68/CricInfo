package com.example.shahz.cricinfo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shahz.cricinfo.Player_Score_Details;
import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.fragments.First_Inning_Score;
import com.example.shahz.cricinfo.model_class.Match_OverView_Details;

import java.util.List;

public class Results_Recycler_Adapter extends RecyclerView.Adapter<Results_Recycler_Adapter.ViewHolder> {

    List<Match_OverView_Details> match_overView_details;
    Context context;

    public Results_Recycler_Adapter(List<Match_OverView_Details> match_overView_details, Context context) {
        this.match_overView_details = match_overView_details;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_result_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final String t1h=match_overView_details.get(position).getBatting();
        holder.team1heading.setText(t1h);

        final String t2h=match_overView_details.get(position).getBowling();
        holder.team2heading.setText(t2h);

        final String wintossby=match_overView_details.get(position).getTosswinby();
        final String chose=match_overView_details.get(position).getModselect();
        String tosswintext=wintossby+" win the toss and choose to "+chose;
        holder.tossWininfo.setText(tosswintext);

        final String bating=match_overView_details.get(position).getBatting();
        holder.team1Label.setText(bating);

        final String bowling=match_overView_details.get(position).getBowling();
        holder.team2Label.setText(bowling);

        holder.team1score.setText(match_overView_details.get(position).getFirst());
        holder.team2score.setText(match_overView_details.get(position).getSecond());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, Player_Score_Details.class);

//                if (chose.equals("Bat"))
//                {
                    intent.putExtra("t1name",match_overView_details.get(position).getTeam1() );
                    intent.putExtra("t2name",match_overView_details.get(position).getTeam2());
                    intent.putExtra("mod",chose);
                    intent.putExtra("bat",t1h);
                    intent.putExtra("bowl",t2h);

//                }
//                else
//                {
//                    intent.putExtra("t1name",match_overView_details.get(position).getTeam2() );
//                    intent.putExtra("t2name",match_overView_details.get(position).getTeam1());
//                }


                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return match_overView_details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView team1heading;
        TextView team2heading;
        TextView tossWininfo;
        TextView team1Label;
        TextView team2Label;
        TextView team1score;
        TextView team2score;

        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            team1heading=itemView.findViewById(R.id.resultTeam1);
            team2heading=itemView.findViewById(R.id.resultTeam2);
            tossWininfo=itemView.findViewById(R.id.tosswinInfoTxt);
            team1Label=itemView.findViewById(R.id.team1LabelTxt);
            team2Label=itemView.findViewById(R.id.team2LabelTxt);
            team1score=itemView.findViewById(R.id.team1ScoreTxt);
            team2score=itemView.findViewById(R.id.team2ScoreTxt);
            constraintLayout=itemView.findViewById(R.id.constraintResultparent);

        }
    }
}
