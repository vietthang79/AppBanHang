package com.example.appbanhang.Demo_SQLite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.appbanhang.Activity.Dialog_Activity;
import com.example.appbanhang.R;
import com.example.appbanhang.SQL.SQLite_NhanVien_Helper;
import com.example.appbanhang.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class demosplite_NV extends AppCompatActivity {

    Spinner quyentruycap;
    Spinner trangthai;
    EditText masoNV, tenNV,luongNV,calam;
    Button btnXacNhan;
    int loai = 1;
    SQLite_NhanVien_Helper sqLiteHelper =  new SQLite_NhanVien_Helper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demosplite_nv);
        AnhXa();
        initData();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Dialog_Activity.class);

                NhanVien nhanVien = new NhanVien();

                nhanVien.setMaNV(masoNV.getText().toString());
                nhanVien.setNameNV(tenNV.getText().toString());
                nhanVien.setCalam(calam.getText().toString());
                nhanVien.setLuongNV(luongNV.getText().toString());
                nhanVien.setQuyen_Truy_Cap(String.valueOf(quyentruycap));
                nhanVien.setTrangThai(String.valueOf(trangthai));

                sqLiteHelper.add_NV(nhanVien);
                startActivity(intent);
            }
        });
    }
    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Admin");
        stringList.add("User");

        List<String> TrangthaiNV = new ArrayList<>();
        TrangthaiNV.add("1 ( Đang làm )");
        TrangthaiNV.add("0 ( Đã nghỉ )");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        quyentruycap.setAdapter(adapter);
        quyentruycap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter <String> adapter_TrangThai = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TrangthaiNV);
        trangthai.setAdapter(adapter_TrangThai);
        trangthai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void AnhXa() {
        quyentruycap = findViewById(R.id.spinner_quyen);
        trangthai = findViewById(R.id.spinner_trangthai);
        masoNV = findViewById(R.id.edt_msnv);
        tenNV = findViewById(R.id.edt_tennv);
        luongNV = findViewById(R.id.edt_luongNV);
        calam = findViewById(R.id.edt_calam);
        btnXacNhan = findViewById(R.id.btn_xacnhan_demo);
    }
}