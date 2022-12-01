package com.example.appbanhang.Activity.Admin_Login_Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.appbanhang.Adapter.DienThoaiAdapter;
import com.example.appbanhang.Adapter.GioHangAdapter;
import com.example.appbanhang.Adapter.TableAdapter;
import com.example.appbanhang.Database.Database;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Ban;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.model.Table;
import com.example.appbanhang.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Table_Admin_Activity extends AppCompatActivity {

//    LinearLayout ban1,ban2,ban3,ban4,ban5,ban6;

    public static Database database;
    TableAdapter tableAdapter;
    List<Table> tableList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_admin);
        Anhxa();
//        Chuyentrang();
    }

//    private void Chuyentrang() {
//        ban1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent (getBaseContext(), Ban1_Admin_Activity.class));
//            }
//        });
//        ban2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent (getBaseContext(), Ban1_Admin_Activity.class));
//            }
//        });
//
//        ban3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent (getBaseContext(), Ban1_Admin_Activity.class));
//            }
//        });
//
//        ban4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent (getBaseContext(), Ban1_Admin_Activity.class));
//            }
//        });
//
//        ban5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent (getBaseContext(), Ban1_Admin_Activity.class));
//            }
//        });
//
//        ban6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent (getBaseContext(), Ban1_Admin_Activity.class));
//            }
//        });
//    }

    private void Anhxa() {
        recyclerView = findViewById(R.id.RC_View_table_Admin);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tableList = new ArrayList<>();
        Table table1 = new Table("Bàn 1", 1);
        Table table2 = new Table("Bàn 2", 2);
        Table table3 = new Table("Bàn 3", 3);
        Table table4 = new Table("Bàn 4", 4);
        Table table5 = new Table("Bàn 5", 5);
        Table table6 = new Table("Bàn 6", 6);
        tableList.add(table1);
        tableList.add(table2);
        tableList.add(table3);
        tableList.add(table4);
        tableList.add(table5);
        tableList.add(table6);

        tableAdapter = new TableAdapter(getApplicationContext(), tableList);

        recyclerView.setAdapter(tableAdapter);
//        ban1 = findViewById(R.id.Ban1);
//        ban2 = findViewById(R.id.Ban2);
//        ban3 = findViewById(R.id.Ban3);
//        ban4 = findViewById(R.id.Ban4);
//        ban5 = findViewById(R.id.Ban5);
//        ban6 = findViewById(R.id.Ban6);

    }
}