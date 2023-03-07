package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPageActivity extends AppCompatActivity {

    EditText email;
    EditText password;

    FirebaseAuth auth;

    public void login(View view){
        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            Toast.makeText(LoginPageActivity.this, "Fill All Required Fields", Toast.LENGTH_LONG).show();


        }

        else {
            auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(LoginPageActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginPageActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.loginEmailText);
        password = findViewById(R.id.loginPaasswordText);
    }
}