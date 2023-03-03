package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.Adapter.ChatAdapter;
import com.example.myapplication.model.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


public class chatPageActivity extends AppCompatActivity {
    EditText message;

    FirebaseAuth auth;
    FirebaseDatabase database;
    ImageView sendMessage, cameraIcon;


    public void backButton(View view){
        finish();
    }

    public void cameraClick(View view){
        PopupMenu popupMenu = new PopupMenu(chatPageActivity.this,cameraIcon );
        popupMenu.getMenuInflater().inflate(R.menu.camera_popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.select_existing:
                        System.out.println(1);
                        break;
                    case R.id.camera:
                        System.out.println(2);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
        popupMenu.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        String senderId = auth.getCurrentUser().getUid();
        TextView userName =findViewById(R.id.userName);
        RecyclerView chatRecyclerView = findViewById(R.id.chatRecyclerView);
        message = findViewById(R.id.messageBar);
        sendMessage = findViewById(R.id.send_Btn);
        cameraIcon =  findViewById(R.id.cameraRoll);
        String receivedId = getIntent().getStringExtra("userId");
        ImageView userProfilePic = (ImageView)findViewById(R.id.profile_pic);

        userName.setText(intent.getStringExtra("userName"));

        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels,  receivedId,this);

        chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);

        final String outgoing = receivedId + senderId;
        final String incoming = senderId + receivedId;

        database.getReference("Users").child(senderId).child("Contacts").child(receivedId)
                        .child("Chats").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for(DataSnapshot data : snapshot.getChildren()){
                            MessageModel model = data.getValue(MessageModel.class);
                            model.setMessageId(data.getKey());
                            messageModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageToSend = message.getText().toString();
                final MessageModel model = new MessageModel(senderId, messageToSend);
                model.setTimeStamp(new Date().getTime());

                message.setText("");

                database.getReference().child("Users").child(senderId)
                        .child("Contacts").child(receivedId).child("Chats")
                        .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("Users").child(receivedId).child("Contacts")
                                        .child(senderId).child("Chats")
                                        .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });




            }
        });


    }


}