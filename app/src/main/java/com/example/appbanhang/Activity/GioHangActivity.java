package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.appbanhang.Adapter.GioHangAdapter;
import com.example.appbanhang.R;
import com.example.appbanhang.Utilyti.NetworkChangeListener;
import com.example.appbanhang.model.EventBus.TinhTongEvent;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.utils.Utils;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {

    TextView giohangtrong, tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    long tongtiensp ;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        tinhTongTien();
    }

    private void tinhTongTien() {
         tongtiensp = 0;
        for (int i = 0 ; i<Utils.mangmuahang.size(); i++){
            tongtiensp = tongtiensp + (Utils.mangmuahang.get(i).getGiasp() * Utils.mangmuahang.get(i).getSpluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp));


    }

     private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
        }else{
            adapter = new GioHangAdapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }

        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                intent.putExtra("tongtien",tongtiensp);
                //----------------------------------------------- Handel sự kiện WAITTING -------------------------------------------------------//

                ProgressDialog progressDialog = ProgressDialog.show(GioHangActivity.this,
                        "Waitting", "do process...");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleviewgiohang);
        btnmuahang = findViewById(R.id.btnmuahang);
    }

    //check_connected
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        FancyToast.makeText(GioHangActivity.this,"CONNECTION FAIL",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        FancyToast.makeText(GioHangActivity.this,"CONNECTION SUCCESS",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if (event != null){
            tinhTongTien();
        }
    }

}