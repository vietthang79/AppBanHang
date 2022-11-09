package com.example.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.appbanhang.Activity.TienIch.CheckConnect;
import com.example.appbanhang.Activity.TienIch.ContentProvider;
import com.example.appbanhang.Activity.TienIch.GG_Maps;
import com.example.appbanhang.Activity.TienIch.XML_Activity;
import com.example.appbanhang.R;
import com.example.appbanhang.Utilyti.NetworkChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.shashank.sony.fancytoastlib.FancyToast;

public class TienIch_Activity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView go,go1,go2,go3;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    BottomNavigationView bottomNav;

    Switch Switch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tien_ich);

        AnhXa();
        ChuyenTrang();
        ActionToolbar();
        EventDarkMode();
        getEventClick();
        //DarkMode


    }
    private void getEventClick() {


        bottomNav.setSelectedItemId(R.id.nav_tienich);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_trangchu:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_tienich:

                        return true;


                }
                return false;
            }
        });







    }


    private void EventDarkMode() {

        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

    }

    public void ActionToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ChuyenTrang() {
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TienIch_Activity.this, CheckConnect.class);
                startActivity(intent);
            }
        });

        go1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TienIch_Activity.this, ContentProvider.class);
                startActivity(intent);
            }
        });

        go2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TienIch_Activity.this, XML_Activity.class);
                startActivity(intent);
            }
        });

        go3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TienIch_Activity.this, GG_Maps.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        go = findViewById(R.id.img_go);
        go1 = findViewById(R.id.img_go1);
        go2 = findViewById(R.id.img_go2);
        go3 = findViewById(R.id.img_go3);
        toolbar = findViewById(R.id.toobar_TienIch);
        Switch = findViewById(R.id.switch1);
        bottomNav = findViewById(R.id.bottom_nav);



    }
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        FancyToast.makeText(TienIch_Activity.this,"CONNECTION FAIL",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        FancyToast.makeText(TienIch_Activity.this,"CONNECTION SUCCESS",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        super.onStop();
    }

}