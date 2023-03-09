package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.fragments.ChatsFragment;
import com.example.myapplication.fragments.ContactFragment;
import com.example.myapplication.fragments.FeedFragment;
import com.example.myapplication.fragments.MapsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Fragment selectFragment;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                break;

            case R.id.shareCode:
                break;

            default:
                break;

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_chats);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_chats:
                        selectFragment = new ChatsFragment();
                        break;
                    case R.id.nav_contacts:
                        selectFragment = new ContactFragment();
                        break;
                    case R.id.nav_feed:
                        selectFragment = new FeedFragment();
                        break;
                    case R.id.nav_maps:
                        selectFragment = new MapsFragment();
                        break;

                }
                if (selectFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
                }
                return true;

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatsFragment()).commit();

    }


}