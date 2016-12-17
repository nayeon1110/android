package com.example.termproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

/**
 * Created by 김나연 on 2016-12-17.
 */

public class DBHelper3 extends SQLiteOpenHelper{
    public DBHelper3(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LOGGER3 (_id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, month INTEHER, day INTEGER , walking INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void Insertwalk (int ye,int mo,int da, int walk)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO LOGGER3(year,month,day,walking) VALUES(?,?,?,?);",new Object[]{ye,mo,da,walk});
        db.close();
    }

    public String getwalk_seven()
    {


        Calendar rightnow = Calendar.getInstance();
        int years = rightnow.get(Calendar.YEAR);
        int months = rightnow.get(Calendar.MONTH)+1;
        int dates = rightnow.get(Calendar.DATE);
        SQLiteDatabase db = getReadableDatabase();
        String result ="";




        Cursor cursor = db.rawQuery("SELECT * FROM LOGGER3",null);


        while (cursor.moveToNext())
        {
            if(cursor.getInt(1) == years)
            {
                if(cursor.getInt(2) == months)
                {
                    if(cursor.getInt(3) >= dates-7)
                    {
                        if(cursor.getInt(3) <= dates)
                        {
                            result += cursor.getInt(1) + "년 " + cursor.getInt(2) +"월 " + cursor.getInt(3) +"일  걸음수 : "
                                    + cursor.getInt(4) +"\n";
                        }

                    }
                }
            }

        }
        return result+"\n";



    }


    public String getwalk_one()
    {


        Calendar rightnow = Calendar.getInstance();
        int years = rightnow.get(Calendar.YEAR);
        int months = rightnow.get(Calendar.MONTH)+1;
        int dates = rightnow.get(Calendar.DATE);
        SQLiteDatabase db = getReadableDatabase();
        String result ="";




        Cursor cursor = db.rawQuery("SELECT * FROM LOGGER3",null);


        while (cursor.moveToNext())
        {
            if(cursor.getInt(1) == years)
            {
                if(cursor.getInt(2) == months)
                {
                    if(cursor.getInt(3) == dates)
                    {
                        result += "오늘 총 걸음수 : "
                                + cursor.getInt(4) +"\n";

                    }
                }
            }

        }
        return result+"\n";



    }



}
