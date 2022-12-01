package com.example.appbanhang.Activity.Admin_Login_Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.Adapter.GioHangAdapter;
import com.example.appbanhang.R;
import com.example.appbanhang.utils.Utils;

import java.text.DecimalFormat;

public class Ban1_Admin_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    GioHangAdapter adapter;
    ImageView img_giohangtrong;
    TextView giohangtrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban1_admin);
        Anhxa();
    }

    private void Anhxa() {

        recyclerView = findViewById(R.id.RC_View_Admin);
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        img_giohangtrong = findViewById(R.id.img_giohangtrong);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
            img_giohangtrong.setVisibility(View.VISIBLE);
        }else{
            adapter = new GioHangAdapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }
    }

}