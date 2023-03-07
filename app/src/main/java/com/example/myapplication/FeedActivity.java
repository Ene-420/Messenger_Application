package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.FeedModel;
import com.example.myapplication.model.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class FeedActivity extends AppCompatActivity {

    TextView cancel,post;
    EditText postText;
    RadioGroup visibility;
    RadioButton visibilitySetting;
    FirebaseAuth auth;
    FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_feed);
        post = findViewById(R.id.post_feed);
        cancel = findViewById(R.id.cancel_feed);
        postText = findViewById(R.id.post_textField);
        visibility = (RadioGroup) findViewById(R.id.radioGroup);

        String postUsername  = getIntent().getStringExtra("Username");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = visibility.getCheckedRadioButtonId();
                visibilitySetting = findViewById(selected);
                if (selected == -1){
                    Toast.makeText(FeedActivity.this, "Select Visibility", Toast.LENGTH_SHORT).show();
                }

                else {
                    if(visibilitySetting.getText().equals("Public")){
                        String feedMessage = postText.getText().toString();
                        FeedModel model = new FeedModel(feedMessage, auth.getCurrentUser().getUid(), "Public");
                        model.setUsername(postUsername);
                        model.setTimeStamp(new Date().getTime());

                        database.getReference("Users").child(auth.getCurrentUser().getUid()).child("Feed").push().setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        System.out.println("COMPLETED");
                                    }
                                });






                    }
                }


            }
        });
    }
}