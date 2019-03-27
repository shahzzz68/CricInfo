package com.example.shahz.cricinfo.Dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shahz.cricinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dialog_Notificaiton_detail extends DialogFragment {


    private TextView mgTitle;
    private TextView mgBody;

    public Dialog_Notificaiton_detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.dialog_notification_details, container, false);

        mgTitle = view.findViewById(R.id.DialogTitleTxt);
        mgBody = view.findViewById(R.id.DialogBodyTxt);


        mgTitle.setText(getArguments().getString("MsgTitle"));
        mgBody.setText(getArguments().getString("MsgBody"));

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

}
