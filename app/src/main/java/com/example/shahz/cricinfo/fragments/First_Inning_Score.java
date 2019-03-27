package com.example.shahz.cricinfo.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.example.shahz.cricinfo.Adapters.ListView_Adapter;
import com.example.shahz.cricinfo.Adapters.Results_Recycler_Adapter;
import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.model_class.First_inning_Details;
import com.example.shahz.cricinfo.model_class.MatchesData;
import com.example.shahz.cricinfo.model_class.Players_data;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class First_Inning_Score extends Fragment {

    ListView listView;
    ListView_Adapter adapter;
    List<First_inning_Details> firstInningDetails;
    FirebaseFirestore firestore;
    RecyclerView recyclerView;

    //First_Inning_Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.first_inning_score_fragment, container, false);

        firestore =FirebaseFirestore.getInstance();
        firstInningDetails= new ArrayList<>();

       // Toast.makeText(getActivity(), getArguments().getString("bat")+getArguments().getString("bowl"), Toast.LENGTH_SHORT).show();
        listView=rootView.findViewById(R.id.listView);

        adapter = new ListView_Adapter(getActivity(), R.layout.single_first_inning_item, firstInningDetails);
        listView.setAdapter(adapter);

        String bat=getArguments().getString("bat");
        String bowl=getArguments().getString("bowl");
        String mod=getArguments().getString("batorbowl");
        String id=bat+" vs "+bowl;
        String select;
        if (mod.equals("Bat"))
        {
            select="first";
        }
        else
        {
            select="second";
        }
        FirstInningDetails(id,select);
        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();

        return rootView;


    }


    private void FirstInningDetails(String matchid,String ining)
    {


        firestore.collection("Results").document(matchid).collection("first").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                if (!queryDocumentSnapshots.isEmpty()) {

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            First_inning_Details first_inning_details = documentChange.getDocument().toObject(First_inning_Details.class);
                            firstInningDetails.add(first_inning_details);
                            adapter.notifyDataSetChanged();

                        }


                    }

                }
            }

                                                                                                            }
        );
    }
}
