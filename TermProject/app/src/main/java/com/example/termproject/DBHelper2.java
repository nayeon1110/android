package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by 김나연 on 2016-12-17.
 */

public class DBHelper2 extends SQLiteOpenHelper {
    public DBHelper2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LOGGER2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, image BLOB);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void insertimage (byte[] bytes)
    {
        SQLiteDatabase db2 = getWritableDatabase();
        byte[] appli = bytes;
        SQLiteStatement p = db2.compileStatement("INSERT INTO LOGGER2(image) values(?);");
        p.bindBlob(1,appli);

    }
}
