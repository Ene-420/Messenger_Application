package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.ChatAdapter;
import com.example.myapplication.model.MessageModel;

import java.util.ArrayList;

public class chatPageActivity extends AppCompatActivity {
    EditText message;



    public void sendBtn(View view){

    }
    public void backButton(View view){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        Intent intent = getIntent();
        // final String senderId = auth.getUid();
        TextView userName =findViewById(R.id.userName);
        RecyclerView chatRecyclerView = findViewById(R.id.chatRecyclerView);
        message = findViewById(R.id.messageBar);
        String receivedId = getIntent().getStringExtra("userId");
        ImageView userProfilePic = (ImageView)findViewById(R.id.profile_pic);

        userName.setText(intent.getStringExtra("userName"));

        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels,  receivedId,this);

        chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);

        //final String outgoing = receivedId + senderId;
        //final String incoming = senderId + receivedId;




    }
}