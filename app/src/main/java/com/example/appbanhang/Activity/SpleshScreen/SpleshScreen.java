package com.example.appbanhang.Activity.SpleshScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.appbanhang.Activity.Login;
import com.example.appbanhang.R;

public class SpleshScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SpleshScreen.this, Login.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                //finish
                finish();
            }
        },4500);
    }
}