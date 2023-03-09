package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.model.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShareCodeActivity extends AppCompatActivity {

    TextView userShareCodeText;
    FirebaseAuth auth;
    FirebaseDatabase database;

    public void backButton(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_code);
        userShareCodeText = findViewById(R.id.userShareCode);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String shareCode = snapshot.child("shareCode").getValue(String.class);
                userShareCodeText.setText(shareCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void generateNewShareCode(View view){
        String shareCode = new Users().setShareCode();

        database.getReference("Users").child(auth.getCurrentUser().getUid()).child("shareCode").setValue(shareCode).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                userShareCodeText.setText(shareCode);
            }
        });


    }
}