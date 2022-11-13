package com.example.appbanhang.OTP_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTP_Activity extends AppCompatActivity {
    Spinner spinner;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String VericationCodeSent;
    String getPhoneNumber,getOTP;
    private TextView timer;
    private Button btnGetOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

//        spinner = findViewById(R.id.spinner);
        TextView error = findViewById(R.id.txterror);
        final EditText inputMobie = findViewById(R.id.inputMobie);
        btnGetOTP = findViewById(R.id.btnGetOTP);
        timer = findViewById(R.id.txtTime);

//        ArrayAdapter<String> countryCodes = new ArrayAdapter<String>(this,
//                R.layout.spinner_item, CountryDetails.countryCodes);
//        spinner.setAdapter(countryCodes);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
//        firebaseLogin ();

        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String numberphone = inputMobie.getText().toString();

//                String spinnerText = spinner.getSelectedItem().toString();
                String PhoneNum = inputMobie.getText().toString();

                if (PhoneNum == null || PhoneNum.trim().isEmpty()) {
                    error.setText("Provide phone number!");
                    return;

                } else if (!isValidNumPhone(numberphone)) {
                    error.setText("Invalid number of phone");
                } else {
                    error.setText("");

//                    getPhoneNumber = spinnerText + PhoneNum;

                    progressBar.setVisibility(View.VISIBLE);
                    btnGetOTP.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+84" + inputMobie.getText().toString(),
                            60, TimeUnit.SECONDS, SendOTP_Activity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBar.setVisibility(View.GONE);
                                    btnGetOTP.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    btnGetOTP.setVisibility(View.VISIBLE);
                                    Toast.makeText(SendOTP_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.GONE);
                                    btnGetOTP.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getBaseContext(),ConfirmOTP_Activiti.class);
                                    intent.putExtra("mobie",inputMobie.getText().toString());
                                    intent.putExtra("verificationId",verificationId);
                                    startActivity(intent);
                                }
                            }

                    );


                    startTimer(60 * 1000, 1000);
                    btnGetOTP.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void startTimer(final long finish,long tick) {
        timer.setVisibility(View.VISIBLE);
        CountDownTimer countDownTimer;
        countDownTimer = new CountDownTimer(finish, tick) {
            @Override
            public void onTick(long l) {
                long remindSec = l/1000;
                timer.setText("Retry after " + (remindSec / 60) + " : " + (remindSec % 60));
                timer.setTextColor(getResources().getColor(R.color.red));

            }

            @Override
            public void onFinish() {
                btnGetOTP.setVisibility(View.VISIBLE);
                Toast.makeText(SendOTP_Activity.this, "Finish", Toast.LENGTH_SHORT).show();
                timer.setVisibility(View.INVISIBLE);
                cancel();
            }
        }.start();

    }

    public boolean isValidNumPhone(String email) {
        String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
        return email.matches(reg);
    }
}