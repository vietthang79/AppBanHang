package com.example.appbanhang.Utilyti;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatButton;

import com.example.appbanhang.Activity.ChiTietActivity;
import com.example.appbanhang.Activity.Login;
import com.example.appbanhang.Activity.SpleshScreen.SpleshScreen;
import com.example.appbanhang.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Common.isConnectedToInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog, null);
            builder.setView(layout_dialog);

            AppCompatButton btnRetry = layout_dialog.findViewById(R.id.btn_Try_Connect);

            //show dialog
            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.setCancelable(false);

            dialog.getWindow().setGravity(Gravity.CENTER);

            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //----------------------------------------------- Handel sự kiện WAITTING -------------------------------------------------------//

                    ProgressDialog progressDialog = ProgressDialog.show(context,
                            "Waitting", "do process...");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 3000);




                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });


        }

    }


}
