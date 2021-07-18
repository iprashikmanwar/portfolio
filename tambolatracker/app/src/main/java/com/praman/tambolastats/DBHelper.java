package com.praman.tambolastats;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/*This is a helper class to manage database creation and version management*/
public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "TamDetail";

    public DBHelper(@Nullable Context context) {
        super(context, "TamTrackers.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table "+TABLE_NAME+"(hmy INTEGER primary key autoincrement,  name TEXT , nameMod TEXT,currLen INTEGER )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    public int addrecord(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name",name);
        cv.put("nameMod",name);
        cv.put("currLen",name.trim().length());
        float res = db.insert(TABLE_NAME,null,cv);

        if(res==-1)
            return 0;
        return 1;
    }

    public Cursor readalldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "Select * from "+TABLE_NAME+" order by currLen, nameMod";
        return db.rawQuery(qry,null);
    }

    public Cursor countElement(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "Select * from "+TABLE_NAME+" where name = '"+ name.trim() + "' COLLATE NOCASE";
        return db.rawQuery(qry,null);
    }

    @SuppressLint("Recycle")
    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from "+TABLE_NAME);
    }

    @SuppressLint("Recycle")
    public void removeElement(String elma) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String qry = "Select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(qry,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int hmy = cursor.getInt(0);
            String UnameMod = cursor.getString(2);
            UnameMod = UnameMod.replace(elma.toUpperCase(),"").replace(elma.toLowerCase(),"");
            contentValues.put("nameMod",UnameMod);
            contentValues.put("currLen",UnameMod.length());
            db.update(TABLE_NAME,contentValues,"hmy = ?",new String[]{String.valueOf(hmy)});
            cursor.moveToNext();
        }

    }

    public void resetGame(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String qry = "Select * from "+TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(qry,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int hmy = cursor.getInt(0);
            String OName = cursor.getString(1);
            contentValues.put("nameMod",OName);
            contentValues.put("currLen",OName.length());
            db.update(TABLE_NAME,contentValues,"hmy = ?",new String[]{String.valueOf(hmy)});
            cursor.moveToNext();
        }
    }
}
