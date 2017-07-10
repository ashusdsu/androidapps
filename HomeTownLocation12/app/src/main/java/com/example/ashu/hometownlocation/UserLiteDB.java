package com.example.ashu.hometownlocation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashu on 10/4/17.
 */

public class UserLiteDB extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "name5.db";
    private static final int DATABASE_VERSION = 1;


    public UserLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase nameDb) {
        nameDb.execSQL("CREATE TABLE IF NOT EXISTS " + "NAMES" + " ("
                + "ID" + " INTEGER PRIMARY KEY,"
                + "NICKNAME" + " TEXT,"
                + "COUNTRY" + " TEXT,"
                + "STATE" + " TEXT,"
                + "CITY" + " TEXT,"
                + "YEAR" + " TEXT,"
                + "LONGITUDE" + " TEXT,"
                + "LATITUDE" + " TEXT"
                + ");");

        //nameDb.execSQL("INSERT INTO NAMES ( name) VALUES ('Roger' );");

    }
    
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {

    }
}
