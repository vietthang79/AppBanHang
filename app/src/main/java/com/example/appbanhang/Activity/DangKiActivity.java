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
                    name.setError("T??n kh??ng ???????c ????? tr???ng");
                }
                if (TextUtils.isEmpty(Email)) {
                    isCorrect = false;
                    email.setError("Email kh??ng ???????c ????? tr???ng");
                }
                if (TextUtils.isEmpty(Taikhoan)) {
                    isCorrect = false;
                    taikhoan.setError("Tai khoan kh??ng ???????c ????? tr???ng");
                }
                if (TextUtils.isEmpty(Matkhau)) {
                    isCorrect = false;
                    matkhau.setError("Pass kh??ng ???????c ????? tr???ng");
                }
                if (TextUtils.isEmpty(Phone)) {
                    isCorrect = false;
                    sdt.setError("SDT kh??ng ???????c ????? tr???ng");
                }

                //------ submit
                if (isCorrect) {
                    DangkiDAO dao = new DangkiDAO(getBaseContext());
                    boolean isExistUserName = dao.checkExistUsername(Taikhoan);
                    if (isExistUserName) {
                        Toast.makeText(getBaseContext(),"T??i kho???n ???? s??? d???ng",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Dangki product = new Dangki(
                                Hovaten, Email, Taikhoan, Matkhau, Phone
                        );
                        boolean isCreated = dao.create(product);
                        if (isValidEmail(Email)) {
                            if (isCreated) {
                                Toast.makeText(getBaseContext(),"????ng k?? t??i kho???n th??nh c??ng",
                                        Toast.LENGTH_LONG).show();
//                                SendMailService javaMailAPI = new SendMailService(getBaseContext(), Email,
//                                        "????ng k?? th??nh c??ng", "ch??c m???ng b???n ????ng k?? th??nh c??ng t??i kho???n:" + Taikhoan + "\n" +
//                                        "m???t kh???u: " + matkhau);
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
