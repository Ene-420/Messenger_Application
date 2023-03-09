package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.chatPageActivity;
import com.example.myapplication.model.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    ArrayList<Users> list;
    Context context;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View uAdapterView = LayoutInflater.from(context).inflate(R.layout.user_chat_sample, parent,false);
        return new ViewHolder(uAdapterView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = list.get(position);

        //Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.face).into(holder.image);
        holder.userName.setText(users.getUserName());
        holder.lastMessage.setText(users.getLastMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, chatPageActivity.class);
                intent.putExtra("userId",users.getUserId());
                intent.putExtra("profilePic", users.getProfilePic());
                intent.putExtra("userName", users.getUserName());
                ((Activity)context).startActivityForResult(intent, 2);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView userName, lastMessage;
        ImageView image, notification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //image = itemView.findViewById(R.id.profile_image);
            lastMessage = itemView.findViewById(R.id.last_message);
            userName = itemView.findViewById(R.id.userNameList);
            //notification = itemView.findViewById(R.id.notification_icon);
        }


    }
}
