package com.example.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.appbanhang.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getEventClick();
        Anhxa();

    }

    private void Anhxa() {
        bottomNav = findViewById(R.id.bottom_nav_profile);

    }

    private void getEventClick() {
        bottomNav.setSelectedItemId(R.id.nav_nguoidung);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_trangchu:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_nguoidung:
                        return true;

                    case R.id.nav_tienich:
                        startActivity(new Intent(getApplicationContext(),TienIch_Activity.class));
                        overridePendingTransition(3,3);
                        return true;


                }
                return false;
            }
        });
    }
}