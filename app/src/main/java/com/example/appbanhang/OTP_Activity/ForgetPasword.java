package com.example.appbanhang.OTP_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.Activity.Login;
import com.example.appbanhang.R;
import com.example.appbanhang.SQL.SQLiteHelper;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasword extends AppCompatActivity {
    SQLiteHelper sqLiteHelper;
    Toolbar toolbar;

    TextView txtErrorFG,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pasword);
        Button RSpass = findViewById(R.id.btnResendPass);
        EditText Newpassword = findViewById(R.id.edtNewPassFG);
        toolbar = findViewById(R.id.toobar_TienIch);
        txtErrorFG = findViewById(R.id.txtErrorFG);
        email=findViewById(R.id.txtViewEmailRS);

        sqLiteHelper = new SQLiteHelper(this);

        Intent intent = getIntent();
        email.setText(intent.getStringExtra("email"));
        ActionToolbar();


        RSpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Newpassword.getText().toString().length()<8 && !isValidPassword(Newpassword.getText().toString() ) ){
                    Toast.makeText(getBaseContext(), "Not valid", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), " valid", Toast.LENGTH_SHORT).show();
                }
                String Email = email.getText().toString();
                String Pass = Newpassword.getText().toString();


                Boolean checknewpass = sqLiteHelper.updatepass(Email, Pass);

                if (checknewpass == true) {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    FancyToast.makeText(getBaseContext(),"Password update successfully", FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                } else {
                    FancyToast.makeText(getBaseContext(), "Password not update !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }

            }



        });

    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[a-zA-Z0-9]{8,24}";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
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