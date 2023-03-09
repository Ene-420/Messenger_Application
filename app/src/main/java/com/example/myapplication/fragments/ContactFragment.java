package com.example.myapplication.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.ContactAdapter;
import com.example.myapplication.R;
import com.example.myapplication.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class ContactFragment extends Fragment {

    ContactAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Users> contactList = new ArrayList<>();
    FirebaseUser user;
    FirebaseDatabase database;

    public ContactFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact, container, false);
        getActivity().setTitle("Contacts");
        recyclerView = view.findViewById(R.id.contactRecyclerView);
        database =  FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        adapter = new ContactAdapter(contactList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));



        database.getReference().child("Contacts").child(user.getUid())
                .addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Users contactListFrmDB = snapshot.getValue(Users.class);
                contactListFrmDB.setUserId(snapshot.getKey());
                contactList.add(contactListFrmDB);
                Collections.sort(contactList, Comparator.comparing(Users::getUserName));

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


        System.out.println(contactList.size());
        return view;
    }
}