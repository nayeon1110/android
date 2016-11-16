package com.example.mylogger22;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 김나연 on 2016-11-16.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STATISTICS (_id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT,latitude REAL, longitude REAL, title TEXT, content TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert (String category, Double lat,Double lon,String title, String content) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO STATISTICS(category, latitude,longitude, title ,content) VALUES(?,?,?,?,?);",new Object[]{category, lat, lon,title, content});
        db.close();
    }

    public String getResult()
    {
        SQLiteDatabase db = getReadableDatabase();
        String result1 ="";
        String result2 ="";
        String result3 ="";
        String result4 ="";
        String result5 ="";
        String result6 ="";
        String result7 ="";



        Cursor cursor = db.rawQuery("SELECT * FROM STATISTICS",null);
        while (cursor.moveToNext()){
            if(cursor.getString(1).equals("집"))
            {
                result1 += "위치: " + cursor.getString(1) + "  위도:" +
                        cursor.getDouble(2) + "  경도:" + cursor.getDouble(3) + "  제목 :" + cursor.getString(4)
                        + "  내용 :" + cursor.getString(5)+"\n";
            }
            else if(cursor.getString(1).equals("학교"))
            {
                result2 += "위치: " + cursor.getString(1) + "  위도:" +
                        cursor.getDouble(2) + "  경도:" + cursor.getDouble(3) + "  제목 :" + cursor.getString(4)
                        + "  내용 :" + cursor.getString(5)+"\n";
            }
            else if(cursor.getString(1).equals("영화관"))
            {
                result3 += "위치: " + cursor.getString(1) + "  위도:" +
                        cursor.getDouble(2) + "  경도:" + cursor.getDouble(3) + "  제목 :" + cursor.getString(4)
                        + "  내용 :" + cursor.getString(5)+"\n";
            }
            else if(cursor.getString(1).equals("카페"))
            {
                result4 += "위치: " + cursor.getString(1) + "  위도:" +
                        cursor.getDouble(2) + "  경도:" + cursor.getDouble(3) + "  제목 :" + cursor.getString(4)
                        + "  내용 :" + cursor.getString(5)+"\n";
            }
            else if(cursor.getString(1).equals("식당"))
            {
                result5 += "위치: " + cursor.getString(1) + "  위도:" +
                        cursor.getDouble(2) + "  경도:" + cursor.getDouble(3) + "  제목 :" + cursor.getString(4)
                        + "  내용 :" + cursor.getString(5)+"\n";
            }
            else if(cursor.getString(1).equals("여행지"))
            {
                result6 += "위치: " + cursor.getString(1) + "  위도:" +
                        cursor.getDouble(2) + "  경도:" + cursor.getDouble(3) + "  제목 :" + cursor.getString(4)
                        + "  내용 :" + cursor.getString(5)+"\n";
            }
            else
            {
                result7 += "위치: " + cursor.getString(1) + "  위도:" +
                        cursor.getDouble(2) + "  경도:" + cursor.getDouble(3) + "  제목 :" + cursor.getString(4)
                        + "  내용 :" + cursor.getString(5)+"\n";
            }


        }





        return result1+"\n"+"\n"+result2+"\n"+"\n"+result3+"\n"+"\n"+result4+"\n"+"\n"+result5+"\n"+"\n"+result6+"\n"+"\n"+result7;
    }






}
