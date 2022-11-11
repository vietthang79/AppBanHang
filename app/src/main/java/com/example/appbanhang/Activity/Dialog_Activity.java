package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.appbanhang.R;
import com.example.appbanhang.utils.Utils;

public class Dialog_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Button btnOpendialogButtom=findViewById(R.id.btn_open_dialog_bottom);
        Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.btn_toup);
        btnOpendialogButtom.setAnimation(animation);

        btnOpendialogButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNameDialog(Gravity.CENTER_VERTICAL);
            }
        });
    }
    private void openNameDialog(int gravity){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_feedback);
        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes=window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);
        // bam ra ngoai co the thoat dialog dc
        if (Gravity.BOTTOM==gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }
        EditText editTextname=dialog.findViewById(R.id.edt_name);
        Button btnNo=dialog.findViewById(R.id.btn_no);
        Button btnYes=dialog.findViewById(R.id.btn_yes);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextname.getText().toString())){
                        editTextname.setError("Vui lòng nhập tên của bạn");
                }else {
                    Intent intent=new Intent(getBaseContext(),MainActivity.class);
                    String ten = editTextname.getText().toString();
                    intent.putExtra("tenkhachhang", ten);
                    startActivity(intent);
                }
                return;
            }
        });
        dialog.show();
    }

}