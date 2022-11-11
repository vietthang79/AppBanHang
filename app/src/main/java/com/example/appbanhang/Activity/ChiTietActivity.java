package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Adapter.LoaiSpAdapter;
import com.example.appbanhang.Adapter.SanPhamMoiAdapter;
import com.example.appbanhang.R;
import com.example.appbanhang.Utilyti.NetworkChangeListener;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.LoaiSp;
import com.example.appbanhang.model.LoaiSpModel;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.model.SanPhamMoiModel;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;

    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;


    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        initView();
        ActionToolbar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
         if (Utils.manggiohang.size() > 0){
             boolean flag = false;
             int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
             for (int i = 0; i<Utils.manggiohang.size(); i++){
                 if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
                     Utils.manggiohang.get(i).setSpluong(soluong + Utils.manggiohang.get(i).getSpluong());
                     long gia =Long.parseLong(sanPhamMoi.getGiasp())* Utils.manggiohang.get(i).getSpluong();
                     Utils.manggiohang.get(i).setGiasp(gia);
                     flag = true;
                 }
             }
             if (flag == false){

                 long gia = Long.parseLong(sanPhamMoi.getGiasp())* soluong;
                 GioHang gioHang = new GioHang();
                 gioHang.setGiasp(gia);
                 gioHang.setSpluong(soluong);
                 gioHang.setIdsp(sanPhamMoi.getId());
                 gioHang.setTensp(sanPhamMoi.getTensp());
                 gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                 Utils.manggiohang.add(gioHang);
             }
         }else{
             int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
             long gia = Long.parseLong(sanPhamMoi.getGiasp())* soluong;
             GioHang gioHang = new GioHang();
             gioHang.setGiasp(gia);
             gioHang.setSpluong(soluong);
             gioHang.setIdsp(sanPhamMoi.getId());
             gioHang.setTensp(sanPhamMoi.getTensp());
             gioHang.setHinhsp(sanPhamMoi.getHinhanh());
             Utils.manggiohang.add(gioHang);


         }
        int totalItem = 0;
        for (int i =0; i<Utils.manggiohang.size(); i++){
            totalItem = totalItem+Utils.manggiohang.get(i).getSpluong();
        }
            badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
//        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        if (sanPhamMoi.getHinhanh().contains("http")) {
            Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        }else{
            String hinh = Utils.BASE_URL+"images/"+sanPhamMoi.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh).into(imghinhanh);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Gia :"+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp())) + "D");
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterspin);

    }



    public void ActionToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.themvaogiohang);
        imghinhanh = findViewById(R.id.imgchitiet);
        spinner = findViewById(R.id.spinner);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);


        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                String giaSP = giasp.getText().toString();
                giohang.putExtra("giatien", giaSP);
                Log.i("test", "da chuyen");
                //----------------------------------------------- Handel sự kiện WAITTING -------------------------------------------------------//

                ProgressDialog progressDialog = ProgressDialog.show(ChiTietActivity.this,
                        "Waitting", "do process...");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
                startActivity(giohang);
            }
        });
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i =0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem+Utils.manggiohang.get(i).getSpluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

//aaa
    @Override
    protected void onResume(){
        super.onResume();
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i =0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem+Utils.manggiohang.get(i).getSpluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
    //aaaaa sdfsdfds sfsfsfs
    //check_connected
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        FancyToast.makeText(ChiTietActivity.this,"CONNECTION FAIL",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        FancyToast.makeText(ChiTietActivity.this,"CONNECTION SUCCESS",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

        super.onStop();
    }
}
