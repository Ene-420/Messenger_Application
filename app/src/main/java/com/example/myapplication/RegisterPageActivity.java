package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.utils.ViewSpline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPageActivity extends AppCompatActivity {


    EditText firstNameText;
    EditText lastNameText;
    EditText passwordText;
    EditText emailText;
    EditText shareCode;
    Chip acceptTnCField;

    boolean chipSelected = false;
    //boolean validDetails = false;

    ProgressDialog progressDialog;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    public void registerDetails(View view){


        if(firstNameText.getText().toString().isEmpty() || lastNameText.getText().toString().isEmpty() || emailText.getText().toString().isEmpty()||passwordText.getText().toString().isEmpty()
        || !chipSelected){
            Toast.makeText(RegisterPageActivity.this, "Fill All Required Fields", Toast.LENGTH_LONG).show();


        }
        else {
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Users users = new Users(firstNameText.getText().toString(), lastNameText.getText().toString(), emailText.getText().toString());
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(id).setValue(users);
                                Toast.makeText(RegisterPageActivity.this, "Account Created", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }

                            else {
                                Toast.makeText(RegisterPageActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        passwordText = findViewById(R.id.passwordNoText);
        emailText = findViewById(R.id.emailText);
        shareCode = findViewById(R.id.shareCodeText);
        acceptTnCField = findViewById(R.id.acceptTnCField);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Account Being Created");
        acceptTnCField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chipSelected = true;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



    }
}