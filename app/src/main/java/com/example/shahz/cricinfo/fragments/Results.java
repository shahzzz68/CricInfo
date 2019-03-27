package com.example.shahz.cricinfo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shahz.cricinfo.Adapters.Notification_Recycler_Adapter;
import com.example.shahz.cricinfo.Adapters.Results_Recycler_Adapter;
import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.model_class.First_inning_Details;
import com.example.shahz.cricinfo.model_class.Match_OverView_Details;
import com.example.shahz.cricinfo.model_class.Notification_data;
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
public class Results extends Fragment {

    RecyclerView resultRecycler;
    FirebaseFirestore firestore;
    List<Match_OverView_Details> matchOverViewDetails;
    Results_Recycler_Adapter adapter;

    public Results() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_results, container, false);

        resultRecycler=view.findViewById(R.id.resultsRecyclerView);
        firestore=FirebaseFirestore.getInstance();
        matchOverViewDetails= new ArrayList<>();

        setAdapter();
        MatchOverViewInfo();

        return view;
    }

    private void MatchOverViewInfo()
    {


        firestore.collection("Results")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                         @Override
                                         public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                                             if (!queryDocumentSnapshots.isEmpty()) {

                                                 for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                                                     if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                                         Match_OverView_Details match = documentChange.getDocument().toObject(Match_OverView_Details.class);
                                                         matchOverViewDetails.add(match);
                                                         adapter.notifyDataSetChanged();

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

    }

    private void setAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        resultRecycler.setLayoutManager(linearLayoutManager);
        adapter=new Results_Recycler_Adapter(matchOverViewDetails, getActivity());
        resultRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}
