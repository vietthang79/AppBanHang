package com.example.appbanhang.OTP_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.SQL.SQLiteHelper;

public class ResetPass extends AppCompatActivity {
    EditText email;
    Button reset;
    SQLiteHelper db;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        email=findViewById(R.id.email_frg);
        reset=findViewById(R.id.btn_reset);
        toolbar = findViewById(R.id.toobar_TienIch);
        db= new SQLiteHelper(this);
        ActionToolbar();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                Boolean check = db.checkemail(Email);

                if (check==true){
                    Intent intent = new Intent(getApplicationContext(), ForgetPasword.class);
                    intent.putExtra("email",Email);
                    startActivity(intent);
                }else {
                    Toast.makeText(ResetPass.this, "Email dose not exit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}