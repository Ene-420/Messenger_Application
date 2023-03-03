package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Adapter.FeedAdapter;
import com.example.myapplication.FeedActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.FeedModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment {
    ImageView addIcon;
    FirebaseAuth auth;
    DatabaseReference ref;
    String cUsername;

    FeedAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<FeedModel> feedModelList = new ArrayList<>();


    public FeedFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
        recyclerView = view.findViewById(R.id.feedRecyclerView);


        adapter = new FeedAdapter(feedModelList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    if (data.getKey().equals("userName")) {
                        cUsername = data.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addIcon = view.findViewById(R.id.add_feed);

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(cUsername);
                Intent intent = new Intent(getContext(), FeedActivity.class);
                intent.putExtra("Username", cUsername);
                startActivity(intent);


            }
        });


        ref.child("Feed").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                System.out.println(snapshot.getValue());

                FeedModel model = snapshot.getValue(FeedModel.class);
                model.setuId(snapshot.getKey());
                feedModelList.add(model);

                adapter.notifyDataSetChanged();
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}