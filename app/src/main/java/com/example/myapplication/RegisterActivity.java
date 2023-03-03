package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    public void moveToRegisterPage(View view){
        Intent moveToRegister = new Intent(getApplicationContext(), RegisterPageActivity.class);
        startActivity(moveToRegister);
    }

    public void moveToLoginPage(View view){
        Intent moveToLogin = new Intent(getApplicationContext(),LoginPageActivity.class);
        startActivity(moveToLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }


    }
}