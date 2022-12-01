package com.example.appbanhang.SQL;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.appbanhang.model.Name_Users;
import com.example.appbanhang.model.NhanVien;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.sql.Date;

public class SQLite_NhanVien_Helper extends SQLiteOpenHelper {
    //DATABASE NAME
    public static final String DATABASE_NAME = "db_nhanvien";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 2;
    //TABLE NAME
    public static final String TABLE_NHANVIEN = "nhanvien";

    //TABLE USERS COLUMNS
    public static final String KEY_ID = "id";
    public static final String KEY_MA_NHANVIEN = "MaNV";
    public static final String KEY_NAME_NHANVIEN = "TenNV";
    public static final String KEY_LUONG= "LuongNV";
    public static final String KEY_CALAM = "CaLam";
    public static final String KEY_QUYENTRUYCAP = "quyen_truy_cap";
    public static final String KEY_TRANGTHAI = "TrangThaiNV";

    Context context;

    public SQLite_NhanVien_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //using this method we can add users to user table
    public void add_NV(@NonNull NhanVien nhanVien) {
        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in @values
        values.put(KEY_NAME_NHANVIEN, nhanVien.NameNV);
        values.put(KEY_MA_NHANVIEN, nhanVien.maNV);
        values.put(String.valueOf(KEY_LUONG), nhanVien.luongNV);
        values.put(KEY_CALAM, nhanVien.calam);
        values.put(KEY_QUYENTRUYCAP, nhanVien.Quyen_Truy_Cap);
        values.put(String.valueOf(KEY_TRANGTHAI), nhanVien.TrangThai);


        // insert row
        long todo_id = db.insert(TABLE_NHANVIEN, null, values);

        if (db.insert(TABLE_NHANVIEN, null, values) == -1) {
            FancyToast.makeText(context,"Save failed",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        } else {
            FancyToast.makeText(context,"Save successfully", FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        }
    }

//    public boolean checkusernewpassword(String user, String newpassword) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM users where userName = ? and newpassword = ? ", new String[]{user, newpassword});
//        if (cursor.getCount() > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

//    public boolean checkuserpassword(String user, String password) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM users where userName = ? and password = ? ", new String[]{user, password});
//        if (cursor.getCount() > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean checkemail(String email){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM users where email = ? ",new String[] {email});
//        if (cursor.getCount()>0){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    public boolean updatepass(String email, String password){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("password",password);
//        long result = db.update("users",contentValues,"email = ? ",new String[]{email});
//        if (result==-1){
//            return false;
//        }else {
//            return true;
//        }
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_NHANVIEN
                + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_MA_NHANVIEN + " TEXT, "
                + KEY_NAME_NHANVIEN + " TEXT, "
                + KEY_LUONG + " TEXT ,"
                + KEY_QUYENTRUYCAP + " TEXT ,"
                + KEY_CALAM + " TEXT, "
                + KEY_TRANGTHAI + " TEXT " + ")";

        db.execSQL(SQL_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_NHANVIEN);
        onCreate(db);
    }


}

