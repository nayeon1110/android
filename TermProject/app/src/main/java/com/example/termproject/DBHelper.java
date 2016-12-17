package com.example.termproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

/**
 * Created by 김나연 on 2016-12-05.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LOGGER (_id INTEGER PRIMARY KEY AUTOINCREMENT, event TEXT,latitude REAL, longitude REAL, s_year INTEGER, s_month INTEGER, s_day INTEGER, s_hour INTEGER, s_minute INTEGER, e_year INTEGER, e_month INTEGER, e_day INTEGER, e_hour INTEGER, e_minute INTEGER,location TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert (String event, Double latitude,Double longitude,int s_year,int s_month,int s_day,int s_hour,int s_minute, int e_year,int e_month, int e_day,int e_hour,int e_minute,String loca) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO LOGGER(event,latitude,longitude,s_year,s_month,s_day,s_hour,s_minute,e_year,e_month,e_day,e_hour,e_minute,location) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);",new Object[]{event,latitude,longitude,s_year,s_month,s_day,s_hour,s_minute,e_year,e_month,e_day,e_hour,e_minute,loca});
        db.close();


    }


    public void delete(long position, DBHelper help )
    {
        //Cursor cs =null;
        //String[] args ={String.valueOf(position)};
        SQLiteDatabase db = help.getWritableDatabase();
        db.delete("LOGGER","_ID=?"+position,null);
        //db.delete("LOGGER","_id=",args);

       /* String sq = "SELECT * FROM LOGGER";
        cs = db.rawQuery(sq,null);
        cs.moveToPosition(position+1);
        String id = cs.getString(0);
        db.execSQL("DELETE FROM LOGGER WHERE _id=" + id + ";");*/

        db.close();
    }



    public String getlocation_one()
    {
        Calendar rightnow = Calendar.getInstance();
        int year = rightnow.get(Calendar.YEAR);
        int month = rightnow.get(Calendar.MONTH)+1;
        int date = rightnow.get(Calendar.DATE);

        SQLiteDatabase db = getReadableDatabase();
        String result1 ="";
        String result2 ="";
        String result3 ="";
        String result4 ="";


        Cursor cursor = db.rawQuery("SELECT * FROM LOGGER",null);
        while (cursor.moveToNext()){
            if(cursor.getInt(9) == year)
            {
                if(cursor.getInt(10) == month)
                {
                    if(cursor.getInt(11) == date)
                    {

                        if(cursor.getString(14).equals("학교"))
                        {
                            result1 += "장소 :" + cursor.getString(14) +
                                    " 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) + " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                    + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) + "분  끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                    + cursor.getInt(12) +"시 " + cursor.getInt(13) +"분 "
                                    +"\n";
                        }
                        else if(cursor.getString(14).equals("여가생활"))
                        {
                            result2 += "장소 :" + cursor.getString(14) +
                                    " 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) + " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                    + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) + "분  끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                    + cursor.getInt(12) +"시 " + cursor.getInt(13) +"분 "
                                    +"\n";
                        }
                        else if(cursor.getString(14).equals("여행"))
                        {
                            result3 += "장소 :" + cursor.getString(14) +
                                    " 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) + " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                    + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) + "분  끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                    + cursor.getInt(12) +"시 " + cursor.getInt(13) +"분 "
                                    +"\n";
                        }
                        else if(cursor.getString(14).equals("기타"))
                        {
                            result4 += "장소 :" + cursor.getString(14) +
                                    " 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) + " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                    + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) + "분  끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                    + cursor.getInt(12) +"시 " + cursor.getInt(13) +"분 "
                                    +"\n";
                        }


                    }
                }
            }

        }




        return result1+"\n"+"\n"+result2+"\n"+"\n"+result3+"\n"+"\n"+result4+"\n";

    }

    public String getstarttime_one()
    {

        Calendar rightnow = Calendar.getInstance();
        int year = rightnow.get(Calendar.YEAR);
        int month = rightnow.get(Calendar.MONTH)+1;
        int date = rightnow.get(Calendar.DATE);

        String result="";
        SQLiteDatabase db22 = getReadableDatabase();

        Cursor cursor = db22.rawQuery("SELECT * FROM LOGGER",null);

        while (cursor.moveToNext())
        {
            if(cursor.getInt(9) == year)
            {
                if(cursor.getInt(10) == month)
                {
                    if(cursor.getInt(11) == date)
                    {
                        result += " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) +
                                "분 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) +  " 끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                + cursor.getInt(12) +"시 " + cursor.getInt(13) + "분 장소 : " + cursor.getString(14) +"\n";
                    }
                }
            }

        }
        return result+"\n";
    }

    public String getlocation_seven()
    {

        Calendar rightnow = Calendar.getInstance();
        int year = rightnow.get(Calendar.YEAR);
        int month = rightnow.get(Calendar.MONTH)+1;
        int date = rightnow.get(Calendar.DATE);

        String result1 ="";
        String result2 ="";
        String result3 ="";
        String result4 ="";
        SQLiteDatabase db22 = getReadableDatabase();

        Cursor cursor = db22.rawQuery("SELECT * FROM LOGGER",null);


        while (cursor.moveToNext()){
            if(cursor.getInt(9) == year)
            {
                if(cursor.getInt(10) == month)
                {
                    if(cursor.getInt(11) >= date)
                    {

                        if(cursor.getInt(11) <= date+7)
                        {

                            if(cursor.getString(14).equals("학교"))
                            {
                                result1 += "장소 :" + cursor.getString(14) +
                                        " 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) + " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                        + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) + "분  끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                        + cursor.getInt(12) +"시 " + cursor.getInt(13) +"분 "
                                        +"\n";
                            }
                            else if(cursor.getString(14).equals("여가생활"))
                            {
                                result2 += "장소 :" + cursor.getString(14) +
                                        " 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) + " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                        + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) + "분  끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                        + cursor.getInt(12) +"시 " + cursor.getInt(13) +"분 "
                                        +"\n";
                            }
                            else if(cursor.getString(14).equals("여행"))
                            {
                                result3 += "장소 :" + cursor.getString(14) +
                                        " 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) + " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                        + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) + "분  끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                        + cursor.getInt(12) +"시 " + cursor.getInt(13) +"분 "
                                        +"\n";
                            }
                            else if(cursor.getString(14).equals("기타"))
                            {
                                result4 += "장소 :" + cursor.getString(14) +
                                        " 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) + " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                        + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) + "분  끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                        + cursor.getInt(12) +"시 " + cursor.getInt(13) +"분 "
                                        +"\n";
                            }


                        }


                    }
                }
            }

        }


        return result1+"\n"+"\n"+result2+"\n"+"\n"+result3+"\n"+"\n"+result4+"\n";



    }


    public String getstarttime_seven()
    {

        Calendar rightnow = Calendar.getInstance();
        int year = rightnow.get(Calendar.YEAR);
        int month = rightnow.get(Calendar.MONTH)+1;
        int date = rightnow.get(Calendar.DATE);

        String result="";
        SQLiteDatabase db22 = getReadableDatabase();

        Cursor cursor = db22.rawQuery("SELECT * FROM LOGGER",null);

        while (cursor.moveToNext())
        {
            if(cursor.getInt(9) == year)
            {
                if(cursor.getInt(10) == month)
                {
                    if(cursor.getInt(11) >= date)
                    {
                        if(cursor.getInt(11) <= date+7)
                        {
                            result += " 시작: " + cursor.getInt(4) +"년 " + cursor.getInt(5)
                                    + "월 " +cursor.getInt(6) +"일 "+  cursor.getInt(7) + "시 " + cursor.getInt(8) +
                                    "분 내용: " + cursor.getString(1)+ " 위도: " + cursor.getDouble(2) + " 경도: " + cursor.getDouble(3) +  " 끝: " + cursor.getInt(9) + "년 " + cursor.getInt(10) + "월 " +cursor.getInt(11) + "일 "
                                    + cursor.getInt(12) +"시 " + cursor.getInt(13) + "분 장소 : " + cursor.getString(14) +"\n";
                        }

                    }
                }
            }

        }
        return result+"\n";
    }







}
