package com.example.shahz.cricinfo.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.shahz.cricinfo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Players_detail_fragment extends DialogFragment {


    public TextView playerName ;
    public TextView style ;
    public TextView type ;
    public TextView team ;

    public Players_detail_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView=inflater.inflate(R.layout.fragment_players_detail_fragment, container, false);

         playerName = mView.findViewById(R.id.playerName);
         style = mView.findViewById(R.id.playerStyle);
         type = mView.findViewById(R.id.playerType);
         team = mView.findViewById(R.id.playerTeam);

        playerName.setText(getArguments().getString("name"));
        style.setText(getArguments().getString("style"));
        team.setText(getArguments().getString("team"));
        type.setText(getArguments().getString("type"));

        getDialog().setTitle("player details");


        return mView;
    }


    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Player Details");

        return dialog;
    }
}
