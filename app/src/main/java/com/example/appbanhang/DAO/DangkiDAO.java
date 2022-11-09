package com.example.appbanhang.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbanhang.Database.DatabaseHelper;
import com.example.appbanhang.model.Dangki;


import java.util.ArrayList;
import java.util.List;

public class DangkiDAO {
    private SQLiteDatabase db;

    public static String name="name";
    public static String TABLE_DANGKI="DANGKI";
    public static String email ="email";
    public static String taikhoan ="taikhoan";
    public static String matkhau ="matkhau";
    public static String sdt ="sdt";
    public DangkiDAO(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.db = databaseHelper.getReadableDatabase();
    }
    @SuppressLint("Range")
    public List<Dangki> getList (String ...selectArgs){
        List<Dangki> list = new ArrayList<>();
        String queryString = "SELECT * FROM DANGKI";
        Cursor cursor = db.rawQuery(queryString,selectArgs);
        while (cursor.moveToNext()){
            Dangki dangki = new Dangki();
            dangki.setName(cursor.getString(cursor.getColumnIndex("name")));
            dangki.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            dangki.setTaikhoan(cursor.getString(cursor.getColumnIndex("taikhoan")));
            dangki.setMatkhau(cursor.getString(cursor.getColumnIndex("matkhau")));
            dangki.setSdt(cursor.getString(cursor.getColumnIndex("sdt")));
            list.add(dangki);
        }
        return list;
    }
    @SuppressLint("Range")
    public Dangki getDetail (String ...selectArgs) {
        String queryString = "SELECT * FROM DANGKI where id=?";
        Cursor cursor = db.rawQuery(queryString, selectArgs);
        cursor.moveToFirst();
        Dangki dangki = new Dangki();
        dangki.setName(cursor.getString(cursor.getColumnIndex("name")));
        dangki.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        dangki.setTaikhoan(cursor.getString(cursor.getColumnIndex("taikhoan")));
        dangki.setMatkhau(cursor.getString(cursor.getColumnIndex("matkhau")));
        dangki.setSdt(cursor.getString(cursor.getColumnIndex("sdt")));
        return dangki;
    }
    public boolean create(Dangki dangki) {
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(name, dangki.getName());
            contentValues.put(email, dangki.getEmail());
            contentValues.put(taikhoan, dangki.getTaikhoan());
            contentValues.put(matkhau, dangki.getMatkhau());
            contentValues.put(sdt, dangki.getSdt());
            db.insert(TABLE_DANGKI, null, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkLogin(String taikhoan, String matkhau){
        try {
            String queryString = "SELECT * FROM DANGKI where taikhoan=? and matkhau=?";
            Cursor cursor = db.rawQuery(queryString, new String[]{taikhoan,matkhau});
            if (cursor.moveToNext()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean checkExistUsername(String taikhoan) {
        try{
            String queryString = "SELECT * FROM DANGKI where taikhoan=?";
            Cursor cursor = db.rawQuery(queryString, new String[] {taikhoan});
            if(cursor.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean checkExistUsernameForget(String taikhoan, String email) {
        try{
            String queryString = "SELECT * FROM DANGKI where taikhoan = ? and  email = ?";
            Cursor cursor = db.rawQuery(queryString, new String[] {taikhoan, email});
            if(cursor.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public boolean updateUSER(Dangki dangki){
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(matkhau, dangki.getMatkhau());
            db.update(TABLE_DANGKI, contentValues, "TaiKhoan=?",
                    new String[] {String.valueOf(dangki.getTaikhoan())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



}
