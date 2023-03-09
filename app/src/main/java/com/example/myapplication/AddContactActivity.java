package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileReader;

public class AddContactActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextInputEditText contactShareCodeText;
    FirebaseDatabase database;

    public void backButton(View view) {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        contactShareCodeText = findViewById(R.id.contactShareCode);


    }

    public void addContact(View view){
        //String text =  contactShareCodeText.getEditText().toString();
        boolean valid = false;

        if(contactShareCodeText.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Valid ShareCode", Toast.LENGTH_SHORT).show();
        }

        else{
            database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot data : snapshot.getChildren() ){
                        //String key = snapshot.getKey();
                        String code =  data.child("shareCode").getValue(String.class);
                        String userUsername = snapshot.child(auth.getCurrentUser().getUid()).child("userName").getValue(String.class);
                        String userEmail = snapshot.child(auth.getCurrentUser().getUid()).child("email").getValue(String.class);
                        Users currentUser = new Users();
                        currentUser.setUserName(userUsername);
                        currentUser.setEmail(userEmail);
                        try {
                            if (contactShareCodeText.getText().toString().equals(code)) {

                                String username = data.child("userName").getValue(String.class);
                                String email = data.child("email").getValue(String.class);
                                String key = data.getKey();

                                Users user = new Users();
                                user.setUserName(username);
                                user.setEmail(email);

                                database.getReference().child("Contacts").child(auth.getCurrentUser().getUid()).child(key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        database.getReference().child("Contacts").child(key)
                                                .child(auth.getCurrentUser().getUid()).setValue(currentUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(AddContactActivity.this, "Contact Added", Toast.LENGTH_LONG).show();
                                                    }
                                                });


                                    }
                                });
                            }


                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}