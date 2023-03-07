package com.example.myapplication.fragments;



import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.UsersAdapter;
import com.example.myapplication.R;
import com.example.myapplication.model.MessageModel;
import com.example.myapplication.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;


public class ChatsFragment extends Fragment {


    UsersAdapter adapter;
    FirebaseAuth auth;

    RecyclerView recyclerView;
    FirebaseDatabase database;

    public ChatsFragment() {
        // Required empty public constructor
    }
    //ragmentChatsBinding binding;
    ArrayList<Users> chatList = new ArrayList<>();
    ArrayList<String> userIds;
    Long timeStamp = 0L;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.chatRecyclerView);

        userIds = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //chatList = getData();
        adapter = new UsersAdapter(chatList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        database.getReference().child("Chats").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getValue() == null) {
                        System.out.println("Nothing to print");
                    }
                    else {
                        //System.out.println("Chats " + dataSnapshot.getValue() + " " + dataSnapshot.getKey());

                        String userId= dataSnapshot.getKey();
                        database.getReference().child("Users").child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Users users = snapshot.getValue(Users.class);
                                users.setUserId(snapshot.getKey());
                                System.out.println("userName " + users.getUserName());


                                database.getReference().child("Chats").child(auth.getCurrentUser().getUid()).child(userId).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot data : snapshot.getChildren() ){
                                            MessageModel model = data.getValue(MessageModel.class);
                                            if(model.getTimeStamp() > timeStamp){
                                                timeStamp = model.getTimeStamp();
                                                users.setLastMessage(model.getMessage());

                                            }
                                            System.out.println("Last Message "+ users.getLastMessage() );
                                        }
                                        chatList.add(users);
                                        adapter.notifyDataSetChanged();
                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println(chatList.size());
        return view;
    }



}