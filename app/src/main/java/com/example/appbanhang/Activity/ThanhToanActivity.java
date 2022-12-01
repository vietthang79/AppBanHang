package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.Activity.Admin_Login_Home.Login_Admin_Activity;
import com.example.appbanhang.R;
import com.example.appbanhang.Utilyti.NetworkChangeListener;
import com.example.appbanhang.utils.Utils;
import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DecimalFormat;

public class ThanhToanActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txttongtien, txtsodt, txtemail;
    EditText edtdiachi;
    Button btndathang;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        initView();
        initControl();


//        Intent intent = getIntent();
//        txtemail.setText(intent.getStringExtra("email"));
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

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien));



//        txtemail.setText(decimalFormat.format(txtemail.getText()));
//        txtsodt.setText(decimalFormat.format(txtsodt.getText()));

//        txtemail.setText(Utils.dangki_current.getEmail());


        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)){
                    FancyToast.makeText(ThanhToanActivity.this,"Address is empty!",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }else{
                    Log.d("Test", new Gson().toJson(Utils.manggiohang));
                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    FancyToast.makeText(ThanhToanActivity.this,"You have successfully placed your order",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    startActivity(new Intent(getApplicationContext(), Login_Admin_Activity.class));

                }

            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toobar);
        txttongtien = findViewById(R.id.txttongtien);
//        txtsodt = findViewById(R.id.txtdienthoai);
        txtemail = findViewById(R.id.txtemail);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang = findViewById(R.id.btndathang);



    }
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        FancyToast.makeText(ThanhToanActivity.this,"CONNECTION FAIL",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        FancyToast.makeText(ThanhToanActivity.this,"CONNECTION SUCCESS",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        super.onStop();
    }
}