package com.example.appbanhang.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.Activity.QR_Code.ScannerView;
import com.example.appbanhang.Adapter.TableAdapter;
import com.example.appbanhang.R;
import com.example.appbanhang.SQL.SQLite_NameUsers_Helper;
import com.example.appbanhang.model.Name_Users;
import com.example.appbanhang.model.Table;
import com.example.appbanhang.utils.Utils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class Dialog_Activity extends AppCompatActivity {
    SQLite_NameUsers_Helper sqLiteHelper =  new SQLite_NameUsers_Helper(this);
    public TextView txt_tenban;
    public Dialog dialog;
    TableAdapter tableAdapter;
    List<Table> tableList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Button btnOpendialogButtom = findViewById(R.id.btn_open_dialog_bottom);

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
        dialog=new Dialog(this);
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

        //================================================= QR_scanner =================================================
        txt_tenban = dialog.findViewById(R.id.txt_QR_Scanner);
//        txt_tenban.setText("dakdkakdka");
        ImageView img_QR = dialog.findViewById(R.id.img_QR_scanner);

        img_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        Dialog_Activity.this
                );
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

            }
        });

        EditText editTextname = dialog.findViewById(R.id.edt_name);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        Button btnYes = dialog.findViewById(R.id.btn_yes);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_tenban = dialog.findViewById(R.id.txt_QR_Scanner);

                if (TextUtils.isEmpty(editTextname.getText().toString())){
                        editTextname.setError("Vui lòng nhập tên của bạn");
                }
//                else  if (TextUtils.isEmpty(txt_tenban.getText().toString())){
//                    txt_tenban.setError("Vui lòng quét mã QR để tiến hành đặt món");
//                }
                else {
                    Intent intent=new Intent(getBaseContext(),MainActivity.class);
                    String ten = editTextname.getText().toString();
                    intent.putExtra("tenkhachhang", ten);
                    Name_Users name_users = new Name_Users();
                    name_users.setUserName(editTextname.getText().toString());
                    sqLiteHelper.addNameUser(name_users);



                    startActivity(intent);
                }
                return;
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );


        if (intentResult.getContents() != null){
            openNameDialog(Gravity.CENTER_VERTICAL);
            txt_tenban = dialog.findViewById(R.id.txt_QR_Scanner);

            txt_tenban.setText(intentResult.getContents());
            Toast.makeText(this, intentResult.getContents(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "OOPS...", Toast.LENGTH_SHORT).show();
        }
    }
}