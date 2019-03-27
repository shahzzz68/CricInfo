package com.example.shahz.cricinfo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shahz.cricinfo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class Live_Scores extends Fragment {


    TextView bolerTxt,inningText;
    TextView scrolText,vsText, totalOverText;
    TextView scoreWikettxt,overbalTxt,ballTxt,runratetxt;
    TextView plyerAname,plyerAruns,playerAballs, plyerA4s, plyerA6s;
    TextView plyerBname,plyerBruns,playerBballs, plyerB4s, plyerB6s;
    DatabaseReference databaseReference;
    ConstraintLayout layout;
    ProgressBar progressBar;

    View rootView;

    public Live_Scores() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_live__scores, container, false);
        rootView=view;

        layout=view.findViewById(R.id.liveScoreLayout);
        progressBar=view.findViewById(R.id.livescoreProgressBar);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        scrolText=view.findViewById(R.id.scrolTxt);
        scrolText.setSelected(true);

        vsText=view.findViewById(R.id.vsTxt);
        totalOverText=view.findViewById(R.id.totalOversTxt);

        bolerTxt=view.findViewById(R.id.bowlerTxt);
        inningText=view.findViewById(R.id.inningTxt);

        scoreWikettxt=view.findViewById(R.id.scoreWicketsTxt);
        overbalTxt=view.findViewById(R.id.oversBallsTxt);
        ballTxt=view.findViewById(R.id.ballsTxt);
        runratetxt=view.findViewById(R.id.runrateTxt);

        plyerAname=view.findViewById(R.id.playerAnametxt);
        plyerAruns=view.findViewById(R.id.playerAruns);
        playerAballs=view.findViewById(R.id.playerAballs);
        plyerA4s=view.findViewById(R.id.playerAfours);
        plyerA6s=view.findViewById(R.id.playerA6s);

        plyerBname=view.findViewById(R.id.playerBnameTxt);
        plyerBruns=view.findViewById(R.id.playerBruns);
        playerBballs=view.findViewById(R.id.playerBballs);
        plyerB4s=view.findViewById(R.id.playerB4s);
        plyerB6s=view.findViewById(R.id.playerB6s);

        ScoresData();
        PlayerAdata();
        PlayerBdata();
        Matchdata();

//        overbalTxt.setText("("+overs+"."+balls+")");
//        scoreWikettxt.setText(runs+"/"+wickets);

        return view;
    }

    private void ScoresData()
    {

        databaseReference.child("Scores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.GONE);

                String runs=dataSnapshot.child("runs").getValue().toString();
                String wickets=dataSnapshot.child("wickets").getValue().toString();
                String overs=dataSnapshot.child("overs").getValue().toString();
                String balls=dataSnapshot.child("balls").getValue().toString();

                double intruns=Double.parseDouble(runs);
                double intovers=Double.parseDouble(overs);
                double intballs=Double.parseDouble(balls);

                double runrate= intruns/(intovers+(intballs/6));

               // Toast.makeText(getActivity(), String.valueOf(runrate), Toast.LENGTH_SHORT).show();

                runratetxt.setText("RR "+String.valueOf(runrate));

                overbalTxt.setText("("+overs+"."+balls+")");
                scoreWikettxt.setText(runs+"/"+wickets);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void PlayerAdata()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child("PlayerA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                String runs=dataSnapshot.child("runs").getValue().toString();
                String ballspld=dataSnapshot.child("ballsplayed").getValue().toString();
                String fours=dataSnapshot.child("fours").getValue().toString();
                String sixs=dataSnapshot.child("six").getValue().toString();
                String bowler=dataSnapshot.child("bowlerName").getValue().toString();

                bolerTxt.setText(bowler);

                plyerAname.setText(name);
                plyerAruns.setText(String.valueOf(runs));
                playerAballs.setText(String.valueOf(ballspld));
                plyerA4s.setText(String.valueOf(fours));
                plyerA6s.setText(String.valueOf(sixs));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

       private void PlayerBdata()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child("PlayerB").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                String runs=dataSnapshot.child("runs").getValue().toString();
                String ballspld=dataSnapshot.child("ballsplayed").getValue().toString();
                String fours=dataSnapshot.child("fours").getValue().toString();
                String sixs=dataSnapshot.child("six").getValue().toString();

                plyerBname.setText(name);
                plyerBruns.setText(String.valueOf(runs));
                playerBballs.setText(String.valueOf(ballspld));
                plyerB4s.setText(String.valueOf(fours));
                plyerB6s.setText(String.valueOf(sixs));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Matchdata()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Match").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String batTeam=dataSnapshot.child("battingTeam").getValue().toString();
                String TosswinTeam=dataSnapshot.child("winnigTeam").getValue().toString();
                String ovrs=dataSnapshot.child("overs").getValue().toString();
                String team1Nm=dataSnapshot.child("team1").getValue().toString();
                String team2Nm=dataSnapshot.child("team2").getValue().toString();
                String chose=dataSnapshot.child("batOrBowl").getValue().toString();
                String ining=dataSnapshot.child("inning").getValue().toString();

                scrolText.setText( TosswinTeam+" won the toss and choose to "+chose+" Now batting Team "+"("+batTeam+")" );
                vsText.setText(team1Nm+" VS "+team2Nm);
                totalOverText.setText(ovrs+" overs Match");
                inningText.setText(ining);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
