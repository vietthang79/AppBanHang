package com.example.appbanhang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.DAO.DangkiDAO;
import com.example.appbanhang.R;

public class DangNhapActivity extends AppCompatActivity {
    private TextView dangki, btndangnhap, quenpass;
    private EditText taikhoann, matkhauu;
    private CheckBox ckLuuTaiKhoan;
    private static String TaiKhoan = "LuuTaiKhoan";
    private static String KhoiPhucTaiKhoan = "KhoiPhucTaiKhoan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        quenpass = findViewById(R.id.quenpass);
        dangki = findViewById(R.id.tv_dangkyy);
        btndangnhap = findViewById(R.id.btndangnhap);
        taikhoann = findViewById(R.id.edtaikhoan);
        matkhauu = findViewById(R.id.edmatkhau);
        ckLuuTaiKhoan = findViewById(R.id.cbLuuTK);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String taiKhoan = bundle.getString("taikhoan");
            taikhoann.setText(taiKhoan);
        }

        quenpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), KhoiPhucPass.class);
                startActivity(intent);
            }
        });

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangkiDAO dangkiDAO = new DangkiDAO(getBaseContext());
                String username = taikhoann.getText().toString();
                String password = matkhauu.getText().toString();
                if (username.trim().equals("") || password.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "tài khoản hoặc mật khẩu ko để trống",
                            Toast.LENGTH_LONG).show();
                } else {
                    boolean isLogin = dangkiDAO.checkLogin(username, password);
                    if (isLogin) {

                        Toast.makeText(getBaseContext(), "đăng nhập thành công",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        // kiểm tra xem user có lưu tài khoản lại không?
                        if (ckLuuTaiKhoan.isChecked()) {
                            LuuTaiKhoan(username, password);
                        }
                        // main activity
                    } else {
                        Toast.makeText(getBaseContext(), "đăng nhập ko thành công",
                                Toast.LENGTH_LONG).show();

                    }
                }

            }

        });
        KhoiPhucTaiKhoan();

        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhapActivity.this, DangKiActivity.class);
                startActivity(intent);
            }
        });


    }

    public void LuuTaiKhoan(String taikhoan, String matkhau) {
        SharedPreferences sharedPreferences = getSharedPreferences(TaiKhoan, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("taikhoan", taikhoan);
        editor.putString("matkhau", matkhau);
        editor.commit();
    }

    public void KhoiPhucTaiKhoan() {
        SharedPreferences sharedPreferences = getSharedPreferences(TaiKhoan, MODE_PRIVATE);
        String username = sharedPreferences.getString("taikhoan", "");
        String password = sharedPreferences.getString("matkhau", "");
        if (!username.equals("") && !password.equals("")) {
            taikhoann.setText(username);
            matkhauu.setText(password);
            ckLuuTaiKhoan.setChecked(true);
        }
    }
}
