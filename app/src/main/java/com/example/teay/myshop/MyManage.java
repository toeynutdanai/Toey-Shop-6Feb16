package com.example.teay.myshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by teay on 2/6/2016.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public MyManage(Context context) {

        //Create & Connected Database
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();


    }//Constructor
} //main class
