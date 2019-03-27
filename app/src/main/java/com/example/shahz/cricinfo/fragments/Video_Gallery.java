package com.example.shahz.cricinfo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shahz.cricinfo.Adapters.Notification_Recycler_Adapter;
import com.example.shahz.cricinfo.Adapters.Video_Gallery_adapter;
import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.model_class.MatchesData;
import com.example.shahz.cricinfo.model_class.Video_MetaData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class Video_Gallery extends Fragment {

    RecyclerView videoRecyclerView;
    List<Video_MetaData> list;
    Video_Gallery_adapter adapter;
    FirebaseFirestore firestore;

    public Video_Gallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.video__gallery_fragment, container, false);

        firestore=FirebaseFirestore.getInstance();
        videoRecyclerView=view.findViewById(R.id.videoRecycler);


        list= new ArrayList<>();

        setAdapter();
        GetVideoDetails();

        return view;
    }


    private void GetVideoDetails()
    {
        Query fireStoreQuarry=firestore.collection("VideoMetadata")
                .orderBy("time", Query.Direction.DESCENDING);

        fireStoreQuarry.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!queryDocumentSnapshots.isEmpty()) {

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                           Video_MetaData data = documentChange.getDocument().toObject(Video_MetaData.class);
                            list.add(data);

                            adapter.notifyDataSetChanged();

                        }
                    }

                }
                else
                {
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setAdapter()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        videoRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Video_Gallery_adapter(list,getActivity());
        videoRecyclerView.setAdapter(adapter);
    }



}
