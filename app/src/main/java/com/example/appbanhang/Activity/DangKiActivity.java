package com.example.appbanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.DAO.DangkiDAO;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Dangki;
import com.google.android.material.textfield.TextInputLayout;


import java.util.ArrayList;
import java.util.List;

public class DangKiActivity extends AppCompatActivity {
    private ImageView dangnhap1;
    private TextView dangnhap, dangki;
    private EditText name, email, taikhoan, matkhau, sdt;
    List<Dangki> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        DangkiDAO dao = new DangkiDAO(getBaseContext());
        dangnhap1 = findViewById(R.id.dangnhap);
        dangnhap = findViewById(R.id.tv_dangnhapapp);
        name = findViewById(R.id.edname);
        email = findViewById(R.id.edemail);
        taikhoan = findViewById(R.id.edtaikhoan);
        matkhau = findViewById(R.id.edmatkhau);
        sdt = findViewById(R.id.edPhone);
        dangki = findViewById(R.id.dki);

        submitDangKy();

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKiActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });
//        dangnhap1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void submitDangKy() {
        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Hovaten = name.getText().toString();
                String Email = email.getText().toString();
                String Taikhoan = taikhoan.getText().toString();
                String Matkhau = matkhau.getText().toString();
                String Phone = sdt.getText().toString();

                TextInputLayout[] inputLayouts = new TextInputLayout[]{

                };
                for (TextInputLayout input : inputLayouts
                ) {
                    if (input != null) {
                        input.setError("");
                    }
                }
                boolean isCorrect = true;
                if (TextUtils.isEmpty(Hovaten)) {
                    isCorrect = false;
                    name.setError("Tên không được để trống");
                }
                if (TextUtils.isEmpty(Email)) {
                    isCorrect = false;
                    email.setError("Email không được để trống");
                }
                if (TextUtils.isEmpty(Taikhoan)) {
                    isCorrect = false;
                    taikhoan.setError("Tai khoan không được để trống");
                }
                if (TextUtils.isEmpty(Matkhau)) {
                    isCorrect = false;
                    matkhau.setError("Pass không được để trống");
                }
                if (TextUtils.isEmpty(Phone)) {
                    isCorrect = false;
                    sdt.setError("SDT không được để trống");
                }

                //------ submit
                if (isCorrect) {
                    DangkiDAO dao = new DangkiDAO(getBaseContext());
                    boolean isExistUserName = dao.checkExistUsername(Taikhoan);
                    if (isExistUserName) {
                        Toast.makeText(getBaseContext(),"Tài khoản đã sử dụng",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Dangki product = new Dangki(
                                Hovaten, Email, Taikhoan, Matkhau, Phone
                        );
                        boolean isCreated = dao.create(product);
                        if (isValidEmail(Email)) {
                            if (isCreated) {
                                Toast.makeText(getBaseContext(),"Đăng ký tài khoản thành công",
                                        Toast.LENGTH_LONG).show();
//                                SendMailService javaMailAPI = new SendMailService(getBaseContext(), Email,
//                                        "đăng ký thành công", "chúc mừng bạn đăng ký thành công tài khoản:" + Taikhoan + "\n" +
//                                        "mật khẩu: " + matkhau);
//                                javaMailAPI.execute();

                                Intent intent = new Intent(getBaseContext(), DangNhapActivity.class);
                                startActivity(intent);
                                Bundle bundle = new Bundle();
                                bundle.putString("taikhoan", Taikhoan);
                                intent.putExtras(bundle);
                            }
                        }
                    }
                }
            }
        });

    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
