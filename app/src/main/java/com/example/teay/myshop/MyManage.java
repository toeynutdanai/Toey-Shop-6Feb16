package com.example.teay.myshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 2/6/16 AD.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String user_TABLE = "userTABLE";
    public static final String food_TABLE = "foodTABLE";
    public static final String column_id = "_id";
    public static final String column_user = "User";
    public static final String column_pass = "Password";
    public static final String column_name = "Name";
    public static final String column_food = "Food";
    public static final String column_price = "Price";
    public static final String column_source = "Source";

    public MyManage(Context context) {

        //Create & Connected Database
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    public long addNewValue(int intTable,
                            String strColumn2,
                            String strColumn3,
                            String strColumn4) {

        ContentValues objContentValues = new ContentValues();
        long longReturn = 0;

        switch (intTable) {
            case 0:

                objContentValues.put(column_user, strColumn2);
                objContentValues.put(column_pass, strColumn3);
                objContentValues.put(column_name, strColumn4);
                writeSqLiteDatabase.insert(user_TABLE, null, objContentValues);

                break;
            case 1:

                objContentValues.put(column_food, strColumn2);
                objContentValues.put(column_price, strColumn3);
                objContentValues.put(column_source, strColumn4);
                writeSqLiteDatabase.insert(food_TABLE, null, objContentValues);

                break;
        }   // switch

        return longReturn;
    }


}   // Main Class