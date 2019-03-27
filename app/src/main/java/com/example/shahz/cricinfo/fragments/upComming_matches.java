package com.example.shahz.cricinfo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shahz.cricinfo.Adapters.Upcomming_Matches_Adapter;
import com.example.shahz.cricinfo.model_class.MatchesData;
import com.example.shahz.cricinfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class upComming_matches extends Fragment {

    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private List<MatchesData> matchesDataList;
    private Upcomming_Matches_Adapter upcomming_matches_adapter;
    private DocumentSnapshot lastPost;

    public upComming_matches() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.up_comming_matches, container, false);

        Initilization(view);
        setAdapter();
        getFireStoreData();
        recyclerViewScrollListner();
       // MultipleListners();

        return view;
    }


    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(upcomming_matches_adapter);

    }

    private void Initilization(View view)
    {
        matchesDataList=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);
        firestore=FirebaseFirestore.getInstance();
        upcomming_matches_adapter= new Upcomming_Matches_Adapter(matchesDataList);
    }



    private void getFireStoreData()
    {
        Query fireStoreQuarry=firestore.collection("Matches")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(5);

        fireStoreQuarry.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    lastPost = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            MatchesData matchesData = documentChange.getDocument().toObject(MatchesData.class);
                            matchesDataList.add(matchesData);

                            upcomming_matches_adapter.notifyDataSetChanged();

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

    private void LoadPosts()
    {
        Query fireStoreQuarry=firestore.collection("Matches")
                .startAfter(lastPost)
                .limit(5);

        fireStoreQuarry.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (!queryDocumentSnapshots.isEmpty()) {

                        lastPost = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);

                        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                MatchesData matchesData = documentChange.getDocument().toObject(MatchesData.class);
                                matchesDataList.add(matchesData);
                                upcomming_matches_adapter.notifyDataSetChanged();
                            } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                                upcomming_matches_adapter.notifyDataSetChanged();
                            }
                            else if (documentChange.getType() == DocumentChange.Type.REMOVED)
                            {
                                Upcomming_Matches_Adapter.ViewHolder viewHolder;

                                upcomming_matches_adapter.notifyDataSetChanged();
                            }

                        }



                }
                else
                    {
                        try {
                            Toast.makeText(getActivity(), "No More Data", Toast.LENGTH_SHORT).show();

                        }
                        catch (Exception ex)
                        {

                        }

                         }


            }
        });
    }

    private void recyclerViewScrollListner()
    {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Boolean reachBottom= !recyclerView.canScrollVertically(1);
                if (reachBottom)
                {
                    LoadPosts();
                }
            }
        });
    }

    public void MultipleListners()
    {
        firestore.collection("Matches").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }
                    Toast.makeText(getActivity(), ""+list, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                     }
            }
        });
    }


}
