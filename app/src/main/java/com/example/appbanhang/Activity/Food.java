package com.example.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appbanhang.Adapter.DienThoaiAdapter;
import com.example.appbanhang.Adapter.FoodAdapter;
import com.example.appbanhang.R;
import com.example.appbanhang.Utilyti.NetworkChangeListener;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Food extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;
    FoodAdapter adapterFood;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        loai = getIntent().getIntExtra("loai", 1);


        Anhxa();
        ActionToolbar();
        getData(page);
        addEventLoad();

    }
        private void addEventLoad() {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (isLoading == false){
                        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == sanPhamMoiList.size()-1){
                            isLoading = true;
                            loadMore();
                        }
                    }
                }
            });
        }

        private void loadMore() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    sanPhamMoiList.add(null);
                    adapterFood.notifyItemInserted(sanPhamMoiList.size()-1);
                }
            });
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sanPhamMoiList.remove(sanPhamMoiList.size()-1);
                    adapterFood.notifyItemRemoved(sanPhamMoiList.size());
                    page = page +1;
                    getData(page);
                    adapterFood.notifyDataSetChanged();
                    isLoading = false;
                }
            }, 2000);
        }

        private void  getData(int page) {
            compositeDisposable.add(apiBanHang.getSanPham(page, loai)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            sanPhamMoiModel -> {
                                if (sanPhamMoiModel.isSuccess()){
                                    Log.d("loggg", sanPhamMoiModel.getResult().size() + "....");
                                    if (adapterFood == null){
                                        sanPhamMoiList = sanPhamMoiModel.getResult();
                                        adapterFood = new FoodAdapter(getApplicationContext(), sanPhamMoiList);
                                        recyclerView.setAdapter(adapterFood);
                                    }else{
                                        int vitri = sanPhamMoiList.size()-1;
                                        int soluongadd = sanPhamMoiModel.getResult().size();
                                        for (int i = 0; i<soluongadd; i++){
                                            sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                                        }
                                        adapterFood.notifyItemRangeInserted(vitri, soluongadd);
                                    }


                                }else{
                                    Toast.makeText(getApplicationContext(), "H???t d??? li???u", Toast.LENGTH_SHORT).show();
                                    isLoading = true;
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),"Kh??ng th??? k???t n???i sever", Toast.LENGTH_SHORT).show();
                            }

                    ));
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

        private void Anhxa() {
            toolbar = findViewById(R.id.toolbar_list);
            recyclerView = findViewById(R.id.recycleview_dienthoai);
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            sanPhamMoiList = new ArrayList<>();
        }
        @Override
        protected void onDestroy() {

            super.onDestroy();
        }
    //check_connected
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        FancyToast.makeText(Food.this,"CONNECTION FAIL",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        FancyToast.makeText(Food.this,"CONNECTION SUCCESS",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        super.onStop();
    }
}