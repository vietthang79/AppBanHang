package com.example.appbanhang.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.project2.orchid.Activity.CategoryActivity;
//import com.project2.orchid.Activity.NoiBatActivity;
//import com.project2.orchid.Activity.SearchActivity;
//import com.project2.orchid.Activity.YeuThichActivity;
//import com.project2.orchid.Animation.ViewPagerAdapter;
//import com.project2.orchid.Object.Category;
//import com.project2.orchid.Object.Category2;
//import com.project2.orchid.Object.Product;
//import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapter;
//import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterDanhmuc;
//import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterHomeCategory;
//import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterHomeTimkiem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;

import com.example.appbanhang.Activity.GioHangActivity;
import com.example.appbanhang.Adapter.ListDrinkAdapter;
import com.example.appbanhang.Adapter.LoaiSpAdapter;
import com.example.appbanhang.Adapter.SanPhamMoiAdapter;
import com.example.appbanhang.Adapter.ViewPagerAdapter;
import com.example.appbanhang.R;
import com.example.appbanhang.Utilyti.NetworkChangeListener;
import com.example.appbanhang.model.ListDrink_Horizol;
import com.example.appbanhang.model.LoaiSp;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment implements View.OnClickListener {
    //    ArrayList<Product> lstDaxem;
//    public ArrayList<Product> lstNoibat, lstDienthoai, lstQuanao, lstNhacua, lstSach, lstLamdep;
//    List<Category> lstBtnNoibat, lstBtnTimkiem;
//    List<Category2> lstDanhmuc;
//    LinearLayout layoutDoraemon;
    Button search, dienThoai, quanAo, nhaCua, sach, lamDep;
    ImageButton danhmuc;
    ViewPager viewPager;
    ProgressBar loadingView, loadingViewNoibat;
    //    SwipeRefreshLayout swipeRefreshLayout;
//    public DatabaseReference reference, refDaxem;
//    public Query refDienthoai, refQuanao, refNhacua, refLamdep, refSach;
    TextView xemthemYeuThich, xemthemNoiBat, xemthemDanhmuc;
//    FirebaseAuth mAuth;
//    Random rd;
//    Intent intent;
    RecyclerView recyclerViewManHinhChinh;

    public View root;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 2500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; // time in milliseconds between successive task executions.
    LoaiSpAdapter loaiSpAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    SanPhamMoiAdapter spAdapter;
    NotificationBadge badge;
    List<SanPhamMoi> mangSpMoi;
    List<LoaiSp> mangloaisp;
    RecyclerView recyclerView_hor;
    List<ListDrink_Horizol> horizolList;
    ListDrinkAdapter listDrinkAdapter;
    FrameLayout frameLayout;
    //check_connected
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
//        if (Utils.manggiohang == null){
//            Utils.manggiohang = new ArrayList<>();
//        }else{
//            int totalItem = 0;
//            for (int i =0; i<Utils.manggiohang.size(); i++){
//                totalItem = totalItem+Utils.manggiohang.get(i).getSpluong();
//            }
//            badge.setText(String.valueOf(totalItem));
//        }
//
//        frameLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent giohang =new Intent(getContext(), GioHangActivity.class);
//                startActivity(giohang);
//            }
//        });
//        xemthemYeuThich = root.findViewById(R.id.home_daxem_xemthem);
//        xemthemNoiBat = root.findViewById(R.id.home_noibat_xemthem);
        xemthemDanhmuc = root.findViewById(R.id.home_danhmuc_xemthem);
        search = root.findViewById(R.id.home_search);
//        swipeRefreshLayout = root.findViewById(R.id.refreshLayout);
        dienThoai = root.findViewById(R.id.btn_dienthoai);
        quanAo = root.findViewById(R.id.btn_thoitrang);
        nhaCua = root.findViewById(R.id.btn_nhacua);
        sach = root.findViewById(R.id.btn_sach);
        lamDep = root.findViewById(R.id.btn_lamdep);
        danhmuc = root.findViewById(R.id.home_btn_danhmuc);
        viewPager = root.findViewById(R.id.viewPager);
        recyclerViewManHinhChinh = root.findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        badge = root.findViewById(R.id.menu_sl);
        frameLayout = root.findViewById(R.id.framegiohang);
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
//        recyclerView_hor = root.findViewById(R.id.recycleview_foodlist);
//        loadingView = root.findViewById(R.id.loading_view);
//        loadingViewNoibat = root.findViewById(R.id.loading_noibat);
//        layoutDoraemon = root.findViewById(R.id.layout_doraemon);

//        initData();
//        =====================================================================================================================

        if (isConnected(getContext())){

//            ActionViewFlipper();
//            getLoaiSanPham();
            getSpMoi();
//            getEventClick();
//            getListPhoto();
        }else{
            Toast.makeText(getActivity(),"Kh??ng c?? internet, vui l??ng k???t n???i", Toast.LENGTH_SHORT).show();
        }
        setViewPager();

        search.setOnClickListener(this);
        dienThoai.setOnClickListener(this);
        nhaCua.setOnClickListener(this);
        lamDep.setOnClickListener(this);
        quanAo.setOnClickListener(this);
        sach.setOnClickListener(this);
//        xemthemYeuThich.setOnClickListener(this);
//        xemthemNoiBat.setOnClickListener(this);
        xemthemDanhmuc.setOnClickListener(this);
        danhmuc.setOnClickListener(this);

//        setDanhmuc();
//        setLstBtnNoibat();
//        setLstBtnTimkiem();
//        loadData();
//        setLoadMoreAction();

        return root;
    }

    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                mangSpMoi = sanPhamMoiModel.getResult();
                                spAdapter = new SanPhamMoiAdapter(getContext(), mangSpMoi);
                                recyclerViewManHinhChinh.setAdapter(spAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getContext(), "Kh??ng k???t n???i ???????c sever"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                ));
    }

    private void setViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
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

    @Override
    public void onClick(View view) {

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

//    private void setDanhmuc() {
//        lstDanhmuc = new ArrayList<>();
//        lstDanhmuc.add(new Category2("??i???n tho???i - M??y t??nh", R.drawable.dienthoaimaytinh));
//        lstDanhmuc.add(new Category2("Th???i trang", R.drawable.thoitrang1));
//        lstDanhmuc.add(new Category2("Nh?? c???a - N???i th???t", R.drawable.xoong));
//        lstDanhmuc.add(new Category2("L??m ?????p - S???c kh???e", R.drawable.son));
//        lstDanhmuc.add(new Category2("S??ch", R.drawable.khuyenhoc));
//        lstDanhmuc.add(new Category2("????? ch??i, M??? v?? b??", R.drawable.mevabe));
//        lstDanhmuc.add(new Category2("Th??? thao - D?? ngo???i", R.drawable.thethao));
//        lstDanhmuc.add(new Category2("?? t?? - Xe m??y", R.drawable.xemay));
//        lstDanhmuc.add(new Category2("Voucher - D???ch v???", R.drawable.voucher));
//
//        GridLayoutManager layoutManagerDanhmuc = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
//        RecyclerView recyclerViewDanhmuc = (RecyclerView) root.findViewById(R.id.recyclerView_home_danhmuc);
//        recyclerViewDanhmuc.setLayoutManager(layoutManagerDanhmuc);
//        RecyclerViewAdapterDanhmuc myAdapterDanhmuc = new RecyclerViewAdapterDanhmuc(this, lstDanhmuc);
//        recyclerViewDanhmuc.setAdapter(myAdapterDanhmuc);
//    }

//    private void setLstBtnNoibat() {
//        lstBtnNoibat = new ArrayList<>();
//        lstBtnNoibat.add(new Category("T???t c???"));
//        lstBtnNoibat.add(new Category("??i???n tho???i - M??y t??nh"));
//        lstBtnNoibat.add(new Category("Th???i trang"));
//        lstBtnNoibat.add(new Category("S??ch"));
//        lstBtnNoibat.add(new Category("L??m ?????p - S???c kh???e"));
//        lstBtnNoibat.add(new Category("????? gia d???ng"));
//
//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        RecyclerView recyclerViewBtnNoibat = (RecyclerView) root.findViewById(R.id.recyclerView_home_button);
//        recyclerViewBtnNoibat.setLayoutManager(layoutManager2);
//        RecyclerViewAdapterHomeCategory myAdapterBtnNoibat = new RecyclerViewAdapterHomeCategory(this, lstBtnNoibat);
//        recyclerViewBtnNoibat.setAdapter(myAdapterBtnNoibat);
//    }

//    private void setLstBtnTimkiem() {
//        lstBtnTimkiem = new ArrayList<>();
//        lstBtnTimkiem.add(new Category("Kh???u trang"));
//        lstBtnTimkiem.add(new Category("Iphone"));
//        lstBtnTimkiem.add(new Category("N?????c r???a tay"));
//        lstBtnTimkiem.add(new Category("Tai nghe bluetooth"));
//        lstBtnTimkiem.add(new Category("M?? t??m"));
//        lstBtnTimkiem.add(new Category("Balo"));
//        lstBtnTimkiem.add(new Category("?????ng h??? th??ng minh"));
//
//        LinearLayoutManager layoutManagerTimkiem = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        RecyclerView recyclerViewBtnTimkiem = root.findViewById(R.id.recyclerView_home_timkiem);
//        recyclerViewBtnTimkiem.setLayoutManager(layoutManagerTimkiem);
//        RecyclerViewAdapterHomeTimkiem myAdapterBtnTimkiem = new RecyclerViewAdapterHomeTimkiem(this, lstBtnTimkiem);
//        recyclerViewBtnTimkiem.setAdapter(myAdapterBtnTimkiem);
//    }

//    private void loadData() {
//        rd = new Random();
//        LinearLayoutManager layoutManagerNoibat = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerViewNoibat = (RecyclerView) root.findViewById(R.id.recyclerView_home_noibat);
//        recyclerViewNoibat.setLayoutManager(layoutManagerNoibat);
//
//        reference = FirebaseDatabase.getInstance().getReference().child("Product");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstNoibat = new ArrayList<>();
//                loadingViewNoibat.setVisibility(GONE);
//                List<Product> full = new ArrayList<>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Product p = dataSnapshot1.getValue(Product.class);
//                    full.add(p);
//                }
//                Collections.shuffle(full);
//
//                for (int i = 0; i < 5; ++i)
//                    lstNoibat.add(full.get(i));
//
//                RecyclerViewAdapter myAdapterNoibat = new RecyclerViewAdapter(getContext(), lstNoibat);
//                recyclerViewNoibat.setAdapter(myAdapterNoibat);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        LinearLayoutManager layoutManagerDienthoai = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        final RecyclerView recyclerViewDienthoai = (RecyclerView) root.findViewById(R.id.recyclerView_home_dienthoai);
//        recyclerViewDienthoai.setLayoutManager(layoutManagerDienthoai);
//
//        refDienthoai = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("danhMuc").equalTo("??i???n tho???i - M??y t??nh");
//        refDienthoai.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstDienthoai = new ArrayList<Product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Product p = dataSnapshot1.getValue(Product.class);
//                    lstDienthoai.add(p);
//                }
//                RecyclerViewAdapter myAdapterDienthoai = new RecyclerViewAdapter(getContext(), lstDienthoai);
//                recyclerViewDienthoai.setAdapter(myAdapterDienthoai);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        LinearLayoutManager layoutManagerQuanao = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        final RecyclerView recyclerViewQuanao = (RecyclerView) root.findViewById(R.id.recyclerView_home_quanao);
//        recyclerViewQuanao.setLayoutManager(layoutManagerQuanao);
//
//        refQuanao = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("danhMuc").equalTo("Qu???n ??o - Th???i trang");
//        refQuanao.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstQuanao = new ArrayList<Product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Product p = dataSnapshot1.getValue(Product.class);
//                    lstQuanao.add(p);
//                }
//                RecyclerViewAdapter myAdapterQuanao = new RecyclerViewAdapter(getContext(), lstQuanao);
//                recyclerViewQuanao.setAdapter(myAdapterQuanao);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        LinearLayoutManager layoutManagerNhacua = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        final RecyclerView recyclerViewNhacua = (RecyclerView) root.findViewById(R.id.recyclerView_home_nhacua);
//        recyclerViewNhacua.setLayoutManager(layoutManagerNhacua);
//
//        refNhacua = FirebaseDatabase.getInstance().getReference().child("Product");
//        refNhacua.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstNhacua = new ArrayList<Product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Product p = dataSnapshot1.getValue(Product.class);
//                    if (p.getDanhMuc().equals("Nh?? c???a - ????? gia d???ng"))
//                        lstNhacua.add(p);
//                }
//                RecyclerViewAdapter myAdapterNhacua = new RecyclerViewAdapter(getContext(), lstNhacua);
//                recyclerViewNhacua.setAdapter(myAdapterNhacua);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        LinearLayoutManager layoutManagerLamdep = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        final RecyclerView recyclerViewLamdep = (RecyclerView) root.findViewById(R.id.recyclerView_home_lamdep);
//        recyclerViewLamdep.setLayoutManager(layoutManagerLamdep);
//
//        refLamdep = FirebaseDatabase.getInstance().getReference().child("Product");
//        refLamdep.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstLamdep = new ArrayList<Product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Product p = dataSnapshot1.getValue(Product.class);
//                    if (p.getDanhMuc().equals("S???c kh???e - L??m ?????p"))
//                        lstLamdep.add(p);
//                }
//                RecyclerViewAdapter myAdapterLamdep = new RecyclerViewAdapter(getContext(), lstLamdep);
//                recyclerViewLamdep.setAdapter(myAdapterLamdep);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        LinearLayoutManager layoutManagerSach = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        final RecyclerView recyclerViewSach = (RecyclerView) root.findViewById(R.id.recyclerView_home_sach);
//        recyclerViewSach.setLayoutManager(layoutManagerSach);
//
//        refSach = FirebaseDatabase.getInstance().getReference().child("Product");
//        refSach.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstSach = new ArrayList<Product>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Product p = dataSnapshot1.getValue(Product.class);
//                    if (p.getDanhMuc().equals("S??ch - V??n ph??ng ph???m"))
//                        lstSach.add(p);
//                }
//                RecyclerViewAdapter myAdapterSach = new RecyclerViewAdapter(getContext(), lstSach);
//                recyclerViewSach.setAdapter(myAdapterSach);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        LinearLayoutManager layoutManagerDaxem = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        final RecyclerView recyclerViewDaxem = (RecyclerView) root.findViewById(R.id.recyclerView_home_daxem);
//        recyclerViewDaxem.setLayoutManager(layoutManagerDaxem);
//
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        refDaxem = FirebaseDatabase.getInstance().getReference().child("Favourite").child(currentUser.getUid());
//        refDaxem.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                loadingView.setVisibility(GONE);
//                lstDaxem = new ArrayList<Product>();
//                if (dataSnapshot.exists()) layoutDoraemon.setVisibility(GONE);
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Product p = dataSnapshot1.getValue(Product.class);
//                    lstDaxem.add(p);
//                }
//                RecyclerViewAdapter myAdapterDaxem = new RecyclerViewAdapter(getContext(), lstDaxem);
//                recyclerViewDaxem.setAdapter(myAdapterDaxem);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void setLoadMoreAction() {
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadData();
//            }
//        });
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.home_search:
//                intent = new Intent(getActivity(), SearchActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.btn_dienthoai:
//                intent = new Intent(getActivity(), CategoryActivity.class);
//                intent.putExtra("DanhMuc", "??i???n tho???i - M??y t??nh");
//                startActivity(intent);
//                break;
//            case R.id.btn_nhacua:
//                intent = new Intent(getActivity(), CategoryActivity.class);
//                intent.putExtra("DanhMuc", "Nh?? c???a - ????? gia d???ng");
//                startActivity(intent);
//                break;
//            case R.id.btn_lamdep:
//                intent = new Intent(getActivity(), CategoryActivity.class);
//                intent.putExtra("DanhMuc", "S???c kh???e - L??m ?????p");
//                startActivity(intent);
//                break;
//            case R.id.btn_thoitrang:
//                intent = new Intent(getActivity(), CategoryActivity.class);
//                intent.putExtra("DanhMuc", "Qu???n ??o - Th???i trang");
//                startActivity(intent);
//                break;
//            case R.id.btn_sach:
//                intent = new Intent(getActivity(), CategoryActivity.class);
//                intent.putExtra("DanhMuc", "S??ch - V??n ph??ng ph???m");
//                startActivity(intent);
//                break;
//            case R.id.home_daxem_xemthem:
//                intent = new Intent(getActivity(), YeuThichActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.home_noibat_xemthem:
//                intent = new Intent(getActivity(), NoiBatActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.home_danhmuc_xemthem:
//                intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("Selection", "List");
//                startActivity(intent);
//                break;
//            case R.id.home_btn_danhmuc:
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("Selection", "List");
//                startActivity(intent);
//                break;
//        }
//    }
}
