package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.appbanhang.R;
import com.example.appbanhang.SQL.SQLiteHelper;
import com.example.appbanhang.Utilyti.NetworkChangeListener;
import com.example.appbanhang.model.User;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    private Button signup;
    TextView txtError1, txtError2, txtError3, txtError4;
    EditText Username, Password, Email, Date;
    RadioButton radioButton;
    RadioGroup radioGroup;
    ImageView ImgBtnCalendar;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup = findViewById(R.id.btnSignUp);
        txtError1 = findViewById(R.id.txtViewError);
        txtError2 = findViewById(R.id.txtViewErrorr);
        txtError3 = findViewById(R.id.txtViewErrorrr);
        txtError4 = findViewById(R.id.txtViewErrorrrr);

        Username = findViewById(R.id.edtUsernameLG);
        Password = findViewById(R.id.edtPasswordLG);
        Email = findViewById(R.id.edtEmail);
        radioGroup = findViewById(R.id.GroupGioiTinh);
        Date = findViewById(R.id.edtDate);
        ImgBtnCalendar = findViewById(R.id.ImgBtnDate);

        sqLiteHelper = new SQLiteHelper(this);

        Calendar calendar = Calendar.getInstance();

        //----------------------------------------------- Handel sự kiện imageBtn & edittext -------------------------------------------------------//

        DatePickerDialog.OnDateSetListener dateEvent = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int mounth = i1 +1;
                Date.setText(i2 + "/" + mounth + "/" + i);
            }
        };
        Log.i("TEST", calendar.get(Calendar.YEAR) + "");

        ImgBtnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        SignUp.this, dateEvent,

                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                dialog.show();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.btn_toup);
        signup.setAnimation(animation);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);


                String UserName = Username.getText().toString();
                String emaill = Email.getText().toString();
                String password = Password.getText().toString();
                String ngaysinh = Date.getText().toString();

                if (UserName.trim().equals("")) {
                    txtError1.setError(" ");
                    txtError1.setText("Enter complete information!  ");
                    YoYo.with(Techniques.Wave)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.edtUsernameLG));
                } else {
                    txtError1.setText("");
                    txtError1.setError(null);

                }
                if (emaill.trim().equals("")) {
                    txtError2.setError(" ");
                    txtError2.setText("Enter complete information!  ");
                    YoYo.with(Techniques.Wave)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.edtEmail));
                } else {
                    txtError2.setText("");
                    txtError2.setError(null);
                }
                if (password.trim().equals("")) {
                    txtError3.setError(" ");
                    txtError3.setText("Enter complete information!  ");
                    YoYo.with(Techniques.Wave)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.edtPasswordLG));
                } else {
                    txtError3.setText("");
                    txtError3.setError(null);
                }
                if (ngaysinh.trim().equals("")) {
                    txtError3.setError(" ");
                    txtError4.setText("Enter complete information!  ");
                    YoYo.with(Techniques.Wave)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.edtDate));
                } else {
                    txtError4.setText("");
                    txtError4.setError(null);
                }
                if (!isValidEmail(emaill)) {
                    txtError3.setText("Wrong data format");
                    YoYo.with(Techniques.Wave)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.edtEmail));
                }
                else  if (!isValidName(UserName)) {
                    txtError1.setText("Wrong data format");
                    YoYo.with(Techniques.Wave)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.edtUsernameLG));
                }
                else {
                    User user = new User();
                    user.setUserName(Username.getText().toString());
                    user.setEmail(Email.getText().toString());
                    user.setPassword(Password.getText().toString());
                    user.setNgaysinh(Date.getText().toString());

                    user.setGioitinh(radioButton.getText().toString());

                    sqLiteHelper.addUser(user);
                    //----------------------------------------------- Handel sự kiện WAITTING -------------------------------------------------------//

                    ProgressDialog progressDialog = ProgressDialog.show(SignUp.this,
                            "Waitting", "do process...");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 3000);

                    Intent i = new Intent(getBaseContext(), Login.class);


                    String give_user = Username.getText().toString();
                    String give_pass = Email.getText().toString();

                    Bundle bundle = new Bundle();

                    bundle.putString("user_name", give_user);
                    bundle.putString("password_give", give_pass);
                    i.putExtra("DU_LIEU",bundle);
                    Log.i("TEST_USER", give_user +" user " + give_pass+ " pass");
                    startActivity(i);

                }

            }
        });

    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public boolean isValidName(String name) {
        String regex = "[a-zA-Z ]+";
        return name.matches(regex);
    }
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        FancyToast.makeText(SignUp.this,"CONNECTION FAIL",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        FancyToast.makeText(SignUp.this,"CONNECTION SUCCESS",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        super.onStop();
    }
}