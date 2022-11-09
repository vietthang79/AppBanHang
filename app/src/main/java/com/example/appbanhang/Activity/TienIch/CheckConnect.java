package com.example.appbanhang.Activity.TienIch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbanhang.Activity.TienIch_Activity;
import com.example.appbanhang.R;

public class CheckConnect extends AppCompatActivity {
    private Button btn_CheckConnect;
    ConnectivityManager connectivityManager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_connect);
        btn_CheckConnect = findViewById(R.id.btnCheckConnect);
        toolbar = findViewById(R.id.toobar_CheckConnect);

        Check_KetNoi();
        ActionToolbar();


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
    private void Check_KetNoi() {
        //CHECK KẾT NỐI
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobieInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        btn_CheckConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (networkInfo.isConnected()){
                    Toast.makeText(CheckConnect.this, "WIFI", Toast.LENGTH_SHORT).show();
                }else if (mobieInfo.isConnected()){
                    Toast.makeText(CheckConnect.this, "MOBIE", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}