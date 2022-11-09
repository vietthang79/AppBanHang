package com.example.appbanhang.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.DAO.DangkiDAO;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Dangki;

public class KhoiPhucPass extends AppCompatActivity {

    private Button sendemail;
    private EditText txtemail, txtusername, txtmatkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoi_phuc_pass2);

        DangkiDAO dao = new DangkiDAO(getBaseContext());
        sendemail = findViewById(R.id.sendemail);
        txtemail = findViewById(R.id.txtemail);
        txtusername = findViewById(R.id.txtusername);
        txtmatkhau = findViewById(R.id.txtmatkhau);

        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = txtusername.getText().toString();
                String MailGet = txtemail.getText().toString();
                String NewPass = txtmatkhau.getText().toString();
                Dangki dangki = new Dangki();
                dangki.setTaikhoan(Username);
                dangki.setMatkhau(NewPass);
                boolean isExistUserName = dao.checkExistUsernameForget(Username, MailGet);

                if(isExistUserName == true) {
                    boolean isUpdate = dao.updateUSER(dangki);
                    if(isUpdate == true){
                        Toast.makeText(getBaseContext(), "Edit thanh cong",
                                Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getBaseContext(), "Edit that bai",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(), "Tai khoan khong ton tai",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}