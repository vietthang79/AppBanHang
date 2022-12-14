package com.example.appbanhang.SQL;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.appbanhang.model.User;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SQLiteHelper extends SQLiteOpenHelper {
    //DATABASE NAME
    public static final String DATABASE_NAME = "db";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;
    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN email
    public static final String KEY_EMAIL = "email";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

//    public static final String KEY_NEWPASSWORD = "newpassword";

    //COLUMN ngaysinh
    public static final String KEY_NGAYSINH = "ngaysinh";

    //COLUMN gioitinh
    public static final String KEY_GIOITINH = "gioitinh";

    //SQL for creating users table
//    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS + " ( "
//                    + KEY_ID + " INTEGER PRIMARY KEY, "
//                         + KEY_USER_NAME + " TEXT, "
//                        + KEY_EMAIL + " TEXT, "
//                        + KEY_PASSWORD + " TEXT"
//                         + KEY_NGAYSINH + "TEXT" + " ) ";

    Context context;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //using this method we can add users to user table
    public void addUser(@NonNull User user) {
        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in @values
        values.put(KEY_EMAIL, user.email);

        //Put password in @values
        values.put(KEY_PASSWORD, user.password);

        //Put newpassword in @values
//        values.put(KEY_NEWPASSWORD, user.Newpassword);

        //Put ngaysinh in @values
        values.put(KEY_NGAYSINH, user.ngaysinh);

        //Put gioitinh in @values
        values.put(KEY_GIOITINH, user.gioitinh);
        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);

        if (db.insert(TABLE_USERS, null, values) == -1) {
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

    public boolean checkuserpassword(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users where userName = ? and password = ? ", new String[]{user, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkemail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users where email = ? ",new String[] {email});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean updatepass(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        long result = db.update("users",contentValues,"email = ? ",new String[]{email});
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
                + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_USER_NAME + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_PASSWORD + " TEXT ,"
//                + KEY_NEWPASSWORD + " TEXT ,"
                + KEY_NGAYSINH + " TEXT, "
                + KEY_GIOITINH + " TEXT " + ")";
        db.execSQL(SQL_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  " + TABLE_USERS);
        onCreate(db);
    }
}

