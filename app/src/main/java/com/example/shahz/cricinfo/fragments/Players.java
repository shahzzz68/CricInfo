package com.example.shahz.cricinfo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shahz.cricinfo.Adapters.Players_Recycler_Adapter;
import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.model_class.Example;
import com.example.shahz.cricinfo.model_class.ItemClick;
import com.example.shahz.cricinfo.model_class.Players_data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Players extends Fragment {

    private FirebaseFirestore firestore;
    private List<Players_data> playersDataClass;
    private List<String> playerList ;
    private List<String> deptList;
    private List<String> documentList;
    private Spinner spinner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private View rootView;
    private List<Example> exampleList;
    private Players_Recycler_Adapter playersRecyclerAdapter;
    private RecyclerView recyclerView;
    String spinnerText;
    ItemClick itemClick;




    public Players() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_players, container, false);

        rootView=view;
        playersDataClass=new ArrayList<>();
        playerList=new ArrayList<>();
        deptList= new ArrayList<>();
        documentList = new ArrayList<>();
        exampleList=new ArrayList<>();

        firestore=FirebaseFirestore.getInstance();
        spinner=view.findViewById(R.id.spinner);

        recyclerView=view.findViewById(R.id.playersRecyclerView);

        itemClick=new ItemClick();

        setAdapter();
        DocumentList();


        return view;
    }


    private void DocumentList()
    {
        firestore.collection("Players").orderBy("deptName").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                            documentList.add(document.getId());

                    }

           /////////////   SPINNER CALL/////////////
                    Spinner(documentList);
           ///////////////////////////////////////////
                } else {
                    Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void Spinner(List<String> list)
    {
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_spinner_item, documentList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int spinnerPosition;
                String text= spinner.getSelectedItem().toString();
                   setSpinnerText(text);
                   itemClick.setPosition(i);
                //Toast.makeText(adapterView.getContext(), String.valueOf(itemClick.getPosition()), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


    }
    private void setSpinnerText(String string)
    {
        playersDataClass.clear();
        String text=string;
        LoadPlayerFields(text);
    }


    private void LoadPlayerFields(String deptName)
    {


        firestore.collection("Players").document(deptName).collection("player details").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                if (!queryDocumentSnapshots.isEmpty()) {

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            Players_data players_data = documentChange.getDocument().toObject(Players_data.class);
                            playersDataClass.add(players_data);
                            playersRecyclerAdapter.notifyDataSetChanged();

                        }


                    }

                }
            }

        }
    );
    }

    private void setAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        playersRecyclerAdapter=new Players_Recycler_Adapter(playersDataClass,getActivity()
        ,getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(playersRecyclerAdapter);

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",itemClick.getPosition());


    }

    public void SpinnerPos()
    {
       ItemClick itemClick=new ItemClick();
        Toast.makeText(getActivity(), String.valueOf(itemClick.getPosition()), Toast.LENGTH_SHORT).show();


    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (savedInstanceState!=null)
//        {
//            int i=savedInstanceState.getInt("position");
//            spinner.setSelection(i);
//        }
//
//    }
}




