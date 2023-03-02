package com.example.myapplication.fragments;



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
import com.example.myapplication.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {


    UsersAdapter adapter;
    RecyclerView recyclerView;
    FirebaseDatabase database;

    public ChatsFragment() {
        // Required empty public constructor
    }
    //ragmentChatsBinding binding;
    ArrayList<Users> chatList = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.chatRecyclerView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //chatList = getData();
        adapter = new UsersAdapter(chatList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());
                    chatList.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println(chatList.size());
        return view;
    }

    private ArrayList<Users> getData()
    {
/*        ArrayList<Users> list = new ArrayList<>();
        list.add(new Users("May",
                "Best Of Luck"));
        list.add(new Users("Maki",
                "b of l"));
        list.add(new Users("lay",
                "This is testing exam .."));

        return list;*/
        return new ArrayList<>();
    }
}