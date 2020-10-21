package com.example.vidload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class firstactivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstactivity);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.Home:
                break;
            case R.id.Play:
                Intent intent2=new Intent(firstactivity.this,Home.class);
                startActivity(intent2);
                break;
            case R.id.downloadvideo:
                Intent intent=new Intent(firstactivity.this,Downloadvideo.class);
                startActivity(intent);
                break;
            case R.id.insta_profile:
                Intent intent1=new Intent(firstactivity.this,Instaprofile.class);
                startActivity(intent1);
                break;
        }
        return false;
    }

    public void openvideop(View view) {
        Intent intent2=new Intent(firstactivity.this,Home.class);
        startActivity(intent2);
    }

    public void opendownload(View view) {
        Intent intent=new Intent(firstactivity.this,Downloadvideo.class);
        startActivity(intent);
    }

    public void openinsta(View view) {
        Intent intent1=new Intent(firstactivity.this,Instaprofile.class);
        startActivity(intent1);
    }
}
