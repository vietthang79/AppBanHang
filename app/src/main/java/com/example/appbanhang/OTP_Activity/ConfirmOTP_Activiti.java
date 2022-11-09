package com.example.appbanhang.OTP_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ConfirmOTP_Activiti extends AppCompatActivity {
    EditText inputcode1,inputcode2,inputcode3,inputcode4,inputcode5,inputcode6;

    private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otp_activiti);

        EditText inputMobie =  findViewById(R.id.inputMobie);


        TextView textmobie = findViewById(R.id.txtMobie);
        textmobie.setText(String.format(
                "+ 84 - %s", getIntent().getStringExtra("mobie")
        ));


        inputcode1 = findViewById(R.id.inputCode1);
        inputcode2 = findViewById(R.id.inputCode2);
        inputcode3 = findViewById(R.id.inputCode3);
        inputcode4 = findViewById(R.id.inputCode4);
        inputcode5 = findViewById(R.id.inputCode5);
        inputcode6 = findViewById(R.id.inputCode6);
        setupOTPInputs();
        DeleteOTPInputs();
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button buttonVerify = findViewById(R.id.btnConfirmOTP);

        verificationId = getIntent().getStringExtra("verificationId");

        findViewById(R.id.btnConfirmOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputcode1.getText().toString().trim().isEmpty()
                        || inputcode2.getText().toString().trim().isEmpty()
                        || inputcode3.getText().toString().trim().isEmpty()
                        || inputcode4.getText().toString().trim().isEmpty()
                        || inputcode5.getText().toString().trim().isEmpty()
                        || inputcode6.getText().toString().trim().isEmpty()
                ){
                    Toast.makeText(ConfirmOTP_Activiti.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = inputcode1.getText().toString()
                        + inputcode2.getText().toString()
                        + inputcode3.getText().toString()
                        + inputcode4.getText().toString()
                        + inputcode5.getText().toString()
                        + inputcode6.getText().toString();
                if (verificationId != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    buttonVerify.setVisibility(View.INVISIBLE);



                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    buttonVerify.setVisibility(View.VISIBLE);

                                    if (task.isSuccessful()){

                                        Intent intent = new Intent(ConfirmOTP_Activiti.this, ResetPass.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        Toast.makeText(ConfirmOTP_Activiti.this, "qua trang duoc r !", Toast.LENGTH_SHORT).show();
//
                                    }else {
                                        Toast.makeText(ConfirmOTP_Activiti.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            }
        });


        findViewById(R.id.txtResendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Country code + Mobie Number
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+ 84 " + getIntent().getStringExtra("mobie"),


                        60,
                        TimeUnit.SECONDS,
                        ConfirmOTP_Activiti.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){


                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(ConfirmOTP_Activiti.this, e.getMessage()  , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId = newVerificationId;
                                Toast.makeText(ConfirmOTP_Activiti.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }



    private void setupOTPInputs(){
        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()){
                    inputcode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void DeleteOTPInputs(){
        inputcode6.removeTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

//                if (s.toString().trim().isEmpty()){
//                    inputcode5.requestFocus();
//                }
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputcode5.removeTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().trim().isEmpty()){
                    inputcode4.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputcode4.removeTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().trim().isEmpty()){
                    inputcode3.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputcode3.removeTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().trim().isEmpty()){
                    inputcode2.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputcode2.removeTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().trim().isEmpty()){
                    inputcode1.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}