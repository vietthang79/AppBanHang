package com.example.appbanhang.Activity.Admin_Login_Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appbanhang.Activity.Dialog_Activity;
import com.example.appbanhang.R;

public class Login_Admin_Activity extends AppCompatActivity {

    TextView error_id,error_name;
    EditText name_NV, id_NV;
    Button btn_dangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        error_name = findViewById(R.id.txterror_Name_NV);
        error_id = findViewById(R.id.txterror_ID_NV);
        name_NV = findViewById(R.id.edt_NameNV);
        id_NV = findViewById(R.id.edtid_NV);
        btn_dangnhap = findViewById(R.id.btnDangnhapNV);

        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten_NV = name_NV.getText().toString();
                String ma_NV = id_NV.getText().toString();

                if (TextUtils.isEmpty(ten_NV)){
                    error_name.setText("Tên của bạn đang trống!");
                }else {
                    error_name.setText(null);
                }
                if (TextUtils.isEmpty(ma_NV)){
                    error_id.setText("Mã số nhân viên của bạn đang trống!");
                }else {
                    error_id.setText(null);
                }
                if (ma_NV.equals("NV001")  && ten_NV.equals("Thang") || ma_NV.equals("NV002")  && ten_NV.equals("Truong")
                        || ma_NV.equals("NV003")  && ten_NV.equals("Nguyen") || ma_NV.equals("NV004")  && ten_NV.equals("Huy")
                        || ma_NV.equals("NV005")  && ten_NV.equals("Tuan") ){
                    Intent i = new Intent(getBaseContext(), Table_Admin_Activity.class);
                    startActivity(i);
                }else {
                    error_id.setText("Tài khoản không hợp lệ!");
                }
            }
        });
    }
}