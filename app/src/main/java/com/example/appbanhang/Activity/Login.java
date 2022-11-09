package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.appbanhang.OTP_Activity.SendOTP_Activity;
import com.example.appbanhang.R;
import com.example.appbanhang.SQL.SQLiteHelper;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity {
    private Button SignUp,SignIn;
    private TextView forget,errorLG;
    private EditText User,Pass;
    SQLiteHelper sqLiteHelper;

    CheckBox RememberMe;

    //Remember
    private SharedPreferences preferences;
    public static final String PREFS_NAME = "PrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

//        getPreferencesData();
        bindWidget();




        sqLiteHelper = new SQLiteHelper(this);

        //Remember
        preferences = getSharedPreferences(PREFS_NAME, 0);
        boolean rememberMe = preferences.getBoolean("rememberMe", false);
        if(rememberMe == true){
            //get previously stored login details
            String login = preferences.getString("login", null);
            String upass = preferences.getString("password", null);

            if(login != null && upass != null){
                //fill input boxes with stored login and pass
                EditText username = findViewById(R.id.edtUsernameSG);
                EditText password = findViewById(R.id.edtPasswordSG);
                username.setText(login);
                password.setText(upass);

                //set the check box to 'checked'
                CheckBox rememberMeCbx = (CheckBox)findViewById(R.id.CBox_Remember);
                rememberMeCbx.setChecked(true);
            }
        }


        Animation animation1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.btn_toup);
        SignIn.setAnimation(animation1);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=User.getText().toString();
                String password=Pass.getText().toString();
                if (TextUtils.isEmpty(user)||TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Enter complete information!", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkuserpass = sqLiteHelper.checkuserpassword(user,password);

                    if (checkuserpass == true){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        CheckBox rememberMeCbx = (CheckBox)findViewById(R.id.CBox_Remember);
                        boolean isChecked = rememberMeCbx.isChecked();
                        if(isChecked){
                            saveLoginDetails();
                        }else{
                            removeLoginDetails();
                        }
                        FancyToast.makeText(Login.this,"LOGIN SUCCESS",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        //----------------------------------------------- Handel sự kiện WAITTING -------------------------------------------------------//

                        ProgressDialog progressDialog = ProgressDialog.show(Login.this,
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

                    else {

                        errorLG.setText("Wrong account or password! ");
                        YoYo.with(Techniques.Wave)
                                .duration(700)
                                .repeat(1)
                                .playOn(findViewById(R.id.edtUsernameSG));

                        YoYo.with(Techniques.Wave)
                                .duration(700)
                                .repeat(1)
                                .playOn(findViewById(R.id.edtPasswordSG));

                        FancyToast.makeText(Login.this,"LOGIN FAILED",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                }
            }
        });

        Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.btn_toup);
        SignUp.setAnimation(animation);
        // đi đến trang SignUp
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), SignUp.class);
                startActivity(i);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), SendOTP_Activity.class);
                startActivity(i);
            }
        });
    }

    private void bindWidget() {

        SignUp = (Button) findViewById(R.id.btnSignUp);
        SignIn = (Button) findViewById(R.id.btnSignIn);
        forget = (TextView) findViewById(R.id.txtForget);
        errorLG = (TextView) findViewById(R.id.txterrorLG);
        User = (EditText) findViewById(R.id.edtUsernameSG);
        Pass = (EditText) findViewById(R.id.edtPasswordSG);

//        newPass = (EditText) findViewById(R.id.edtNewPassFG);
        RememberMe = (CheckBox) findViewById(R.id.CBox_Remember);

    }


    //Remember
    private void saveLoginDetails(){
        //fill input boxes with stored login and pass
        EditText username = findViewById(R.id.edtUsernameSG);
        EditText password = findViewById(R.id.edtPasswordSG);
        String login = username.getText().toString();
        String upass = password.getText().toString();
        SharedPreferences.Editor e = preferences.edit();
        e.putBoolean("rememberMe", true);
        e.putString("login", login);
        e.putString("password", upass);
        e.commit();
    }
    //Remember
    private void removeLoginDetails(){
        SharedPreferences.Editor e = preferences.edit();
        e.putBoolean("rememberMe", false);
        e.remove("login");
        e.remove("password");
        e.commit();
    }
}