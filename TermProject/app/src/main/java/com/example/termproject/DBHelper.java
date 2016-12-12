package com.example.termproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 김나연 on 2016-12-05.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LOGGER (_id INTEGER PRIMARY KEY AUTOINCREMENT, event TEXT,latitude REAL, longitude REAL, s_year INTEGER, s_month INTEGER, s_day INTEGER, s_hour INTEGER, s_minute INTEGER, e_year INTEGER, e_month INTEGER, e_day INTEGER, e_hour INTEGER, e_minute INTEGER);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert (String event, Double latitude,Double longitude,int s_year,int s_month,int s_day,int s_hour,int s_minute, int e_year,int e_month, int e_day,int e_hour,int e_minute) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO LOGGER(event,latitude,longitude,s_year,s_month,s_day,s_hour,s_minute,e_year,e_month,e_day,e_hour,e_minute) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);",new Object[]{event,latitude,longitude,s_year,s_month,s_day,s_hour,s_minute,e_year,e_month,e_day,e_hour,e_minute});
        db.close();


    }

    public void delete(int position)
    {
        Cursor cs =null;
        SQLiteDatabase db = getWritableDatabase();
        String sq = "SELECT * FROM LOGGER";
        cs = db.rawQuery(sq,null);
        cs.moveToPosition(position+1);
        int id = position+1;
        db.execSQL("DELETE FROM LOGGER WHERE _id=" + id + ";");
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



        Cursor cursor = db.rawQuery("SELECT * FROM LOGGER",null);
        while (cursor.moveToNext())
        {
            result1 += "사건: " + cursor.getString(1)+"\n";
        }
       /* while (cursor.moveToNext()){
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


        }*/





        return result1+"\n"+"\n"+result2+"\n"+"\n"+result3+"\n"+"\n"+result4+"\n"+"\n"+result5+"\n"+"\n"+result6+"\n"+"\n"+result7;

    }


}
