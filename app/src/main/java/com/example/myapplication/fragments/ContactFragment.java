package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        recyclerView = view.findViewById(R.id.contactRecyclerView);
        database =  FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        adapter = new ContactAdapter(contactList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));



        database.getReference().child("Users").child(user.getUid()).child("Contacts")
/*                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data : snapshot.getChildren()){
                            System.out.println(data.child("Contacts").getValue());

//                    Users contacts = data.getValue(Users.class);
//                    contacts.setUserId(data.getKey());
//                    contactList.add(contacts);
//                    System.out.println("UserName"+ contacts.getUserName());

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //contactList.clear();

//                Users contacts = snapshot.getValue(Users.class);
//                System.out.println(contacts.getUserName());
                Users contactListFrmDB = snapshot.getValue(Users.class);
                contactListFrmDB.setUserId(snapshot.getKey());
                contactList.add(contactListFrmDB);

/*                for(Users data : snapshot.getValue(Users.class)){
                    //System.out.println(data.getValue());
                    Users contacts = snapshot.getValue(Users.class);
                    System.out.println(contacts.getUserName());
                    contactList.add(contacts);

*//*                    //Users contacts = new Users();
                    Users contacts = data.getValue(Users.class);
                    //contacts.setUserName(contactsName);
                    contacts.setUserId(data.getKey());
                    contactList.add(contacts);
                    System.out.println("UserName"+ contacts.getUserName());*//*

                }*/
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