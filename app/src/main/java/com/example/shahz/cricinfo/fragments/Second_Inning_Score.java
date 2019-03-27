package com.example.shahz.cricinfo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shahz.cricinfo.Adapters.ListView_Adapter;
import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.model_class.First_inning_Details;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Second_Inning_Score extends Fragment {


    FirebaseFirestore firestore;
    ListView listView;
    ListView_Adapter adapter;
    List<First_inning_Details> firstInningDetails;
    public Second_Inning_Score() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.second__inning__score_fragment, container, false);
        firestore= FirebaseFirestore.getInstance();
        firstInningDetails= new ArrayList<>();

        listView=view.findViewById(R.id.secondinListView);
        adapter = new ListView_Adapter(getActivity(), R.layout.single_first_inning_item, firstInningDetails);
        listView.setAdapter(adapter);

        String bat=getArguments().getString("bat");
        String bowl=getArguments().getString("bowl");
        String mod=getArguments().getString("batorbowl");
        String id=bat+" vs "+bowl;
        String select;
        if (mod.equals("Bat"))
        {
            select="second";
        }
        else
        {
            select="first";
        }
        SecondInningDetails(id,select);

        return  view;
    }

    private void SecondInningDetails(String matchid, String ining)
    {


        firestore.collection("Results").document(matchid).collection("second").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
