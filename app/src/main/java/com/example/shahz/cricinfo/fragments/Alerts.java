package com.example.shahz.cricinfo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shahz.cricinfo.Adapters.Notification_Recycler_Adapter;
import com.example.shahz.cricinfo.Adapters.Players_Recycler_Adapter;
import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.model_class.Notification_data;
import com.example.shahz.cricinfo.model_class.Players_data;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Alerts extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private Notification_Recycler_Adapter notificationRecyclerAdapter;
    private List<Notification_data> notificationModelClass;

    public Alerts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_alerts, container, false);

        recyclerView=view.findViewById(R.id.alertsRecyclerView);
        firestore=FirebaseFirestore.getInstance();
        notificationModelClass= new ArrayList<>();

        setAdapter();
        LoadNotificaionData();

        return view;
    }

    private void LoadNotificaionData()
    {


        firestore.collection("Notifications").orderBy("data", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                    Notification_data notificationData = documentChange.getDocument().toObject(Notification_data.class);
                                    notificationModelClass.add(notificationData);
                                    notificationRecyclerAdapter.notifyDataSetChanged();

                                  //  Toast.makeText(getActivity(), notificationModelClass.get(0).toString(), Toast.LENGTH_SHORT).show();

                                }


                            }

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                                                                                                            }
        );
        notificationRecyclerAdapter.notifyDataSetChanged();
        return;
       // return notificationModelClass;
    }

    private void setAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        notificationRecyclerAdapter=new Notification_Recycler_Adapter(notificationModelClass, getActivity(),
                getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(notificationRecyclerAdapter);

    }

}
