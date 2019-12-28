package com.example.listviewandalertdialogwithsql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static  final String Database_Name="landmarks.db";
    public static  final String Database_Table="landmark_list";
    public static  final String Column_ID="ID";
    public static  final String Column_Name="LIST";


    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ Database_Table + " ("+Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT"+","+Column_Name+" TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Database_Table);
        onCreate(db);
    }

    public boolean insertData(String landmarks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_Name,landmarks);
        long result=db.insert(Database_Table,null,contentValues);
        if(result==-1){
            return false;
        }
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Database_Table,null);
        return cursor;
    }

    public boolean UpdateData(String ID,String Landmark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_ID,ID);
        contentValues.put(Column_Name,Landmark);
        db.update(Database_Table,contentValues,"ID = ?",new String[] {ID});
        return  true;
    }

    public Integer DeleteData(String LIST){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Database_Table,"LIST = ?",new String[]{LIST});
    }
}
