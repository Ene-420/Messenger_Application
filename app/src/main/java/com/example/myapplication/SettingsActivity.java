package com.example.myapplication;

import static java.util.Arrays.asList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    ListView listView;
    public void backButton(View view){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        listView = findViewById(R.id.settingsListView);

        ArrayList<String> settingsContent = new ArrayList<>(asList("View Share Code", "Update Details", "Add New Contact" ));

        ArrayAdapter<String>  adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settingsContent);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), ShareCodeActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent UpdateIntent = new Intent(getApplicationContext(), UpdateContactActivity.class);
                        startActivity(UpdateIntent);
                        break;
                    case 2:
                        Intent addContactIntent = new Intent(getApplicationContext(), AddContactActivity.class);
                        startActivity(addContactIntent);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}