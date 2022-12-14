package com.example.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Adapter.LoaiSpAdapter;
import com.example.appbanhang.Adapter.SanPhamMoiAdapter;
import com.example.appbanhang.Adapter.ViewPagerAdapter;
import com.example.appbanhang.Fragment.HomeFragment;
import com.example.appbanhang.Fragment.ListFragment;
import com.example.appbanhang.R;

import com.example.appbanhang.Utilyti.NetworkChangeListener;
import com.example.appbanhang.model.LoaiSp;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewManHinhChinh;
    BottomNavigationView bottomNav;
    List<LoaiSp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ViewPager viewPager;
    int newPosition, startingPosition;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 2500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; // time in milliseconds between successive task executions.
    //check_connected
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    ImageButton imgbtn_cf,imgbtn_ts,imgbtn_thuoc,imgbtn_kem,imgbtn_food;

    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        ChuyenTrang();
        if (isConnected(this)){
            setViewPager();
            getSpMoi();
            getEventClick();

        }else{
            Toast.makeText(getApplicationContext(),"Kh??ng c?? internet, vui l??ng k???t n???i", Toast.LENGTH_SHORT).show();
        }
    }

    private void ChuyenTrang() {
        //Coffee
        imgbtn_cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coffee = new Intent(MainActivity.this, DienThoai.class);
                coffee.putExtra("type", 3);
                startActivity(coffee);
            }
        });


        //MilkTea
        imgbtn_ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent milktea = new Intent(MainActivity.this, LapTop.class);
                milktea.putExtra("type", 4);
                startActivity(milktea);
            }
        });


        //Thuoc La
        imgbtn_thuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Cigarette = new Intent(MainActivity.this, ThuocLa.class);
                Cigarette.putExtra("type", 5);
                startActivity(Cigarette);
            }
        });


        //Kem
        imgbtn_kem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cream = new Intent(MainActivity.this, Ice_Cream.class);
                cream.putExtra("type", 7);
                startActivity(cream);
            }
        });


        //Food
        imgbtn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent food = new Intent(MainActivity.this, Food.class);
                food.putExtra("type", 1);
                startActivity(food);
            }
        });

        //tim kiem
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getEventClick() {
        bottomNav.setSelectedItemId(R.id.nav_trangchu);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_trangchu:
                        return true;

                    case R.id.nav_tienich:
                        startActivity(new Intent(getApplicationContext(),TienIch_Activity.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });

    }

    private void setViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext());
        viewPager.setAdapter(viewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void getSpMoi() {
        for (int i = 0; i < 7; ++i){

            compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                mangSpMoi = sanPhamMoiModel.getResult();
                                spAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSpMoi);
                                recyclerViewManHinhChinh.setAdapter(spAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Kh??ng k???t n???i ???????c sever"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                ));
        }

    }

    private void Anhxa() {

        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        viewPager = findViewById(R.id.viewPager);
        bottomNav = findViewById(R.id.bottom_nav);
        badge = findViewById(R.id.menu_sl);
        frameLayout =findViewById(R.id.framegiohang);
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        imgbtn_cf = findViewById(R.id.home_btn_danhmuc);
        imgbtn_ts = findViewById(R.id.home_btn_uathich);
        imgbtn_thuoc = findViewById(R.id.home_btn_deal);
        imgbtn_kem = findViewById(R.id.home_vi);
        imgbtn_food = findViewById(R.id.home_btn_food);
        search = findViewById(R.id.home_search);
        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else{
            int totalItem = 0;
            for (int i =0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem+Utils.manggiohang.get(i).getSpluong();
            }
//            badge.setText(String.valueOf(totalItem));
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i =0; i<Utils.manggiohang.size(); i++){
            totalItem = totalItem+Utils.manggiohang.get(i).getSpluong();
        }
//        badge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null &&  wifi.isConnected()) ||(mobile != null && mobile.isConnected())){
            return true;
        }else{
            return  false;
        }

    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}