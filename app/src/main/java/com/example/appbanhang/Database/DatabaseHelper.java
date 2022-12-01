 package com.example.appbanhang.Database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

 /**
  * Created by admin on 6/7/18.
  */
 public class DatabaseHelper extends SQLiteOpenHelper {

     public static String DB_NAME = "dangki";

     public static int DB_VERSION = 4;


     //---------------- DANG KY TAI KHOAN ------------------
     public static String name="name";
     public static String TABLE_DANGKI="DANGKI";
     public static String email = "email";
     public static String taikhoan = "taikhoan";
     public static String matkhau = "matkhau";
     public static String sdt = "sdt";

     //---------------- TÊN NGƯỜI DÙNG ------------------
     public static String name_users = "name_users";
     public static String TABLE_NAMEUSERS ="DANGKI";

     //---------------- NHÂN VIÊN ------------------





     public DatabaseHelper(Context context) {
         super(context, DB_NAME, null, DB_VERSION);

     }

     @Override
     public void onCreate(SQLiteDatabase sqLiteDatabase) {
         String createDangkiTable = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",TABLE_DANGKI,name,email,taikhoan,matkhau,sdt);
         sqLiteDatabase.execSQL(createDangkiTable);

         String createNameUsersTable = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT)",TABLE_NAMEUSERS,name_users);
         sqLiteDatabase.execSQL(createNameUsersTable);

     }


     @Override
     public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         String dropProductTable = String.format("DROP TABLE IF EXISTS %s", TABLE_DANGKI);
         sqLiteDatabase.execSQL(dropProductTable);

         String dropNameUsersTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAMEUSERS);
         sqLiteDatabase.execSQL(dropNameUsersTable);

         onCreate(sqLiteDatabase);
     }

 }
