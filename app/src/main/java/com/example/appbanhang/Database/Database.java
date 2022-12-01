package com.example.appbanhang.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {


    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void INSERT_BAN(String ten){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Ban VALUES(null, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1,ten);

        sqLiteStatement.executeInsert();
    }

    public void UPDATE_BAN(String ten, String id){
        SQLiteDatabase database=getWritableDatabase();
        String sql="UPDATE Ban SET Ten=? WHERE Id=?";
        //BIEN DICH
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,ten);
        statement.bindString(5,id);

        statement.executeInsert();
    }

    public void DELETE_DOVAT(String id){
        SQLiteDatabase database=getWritableDatabase();
        String sql="DELETE FROM Ban  WHERE Id=?";
        //BIEN DICH
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1,id);

        statement.executeInsert();
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
