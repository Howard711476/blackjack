package com.example.howard0702.blackjack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zoo88115 on 2015/5/27 0027.
 */
public class MyDBHelper  extends SQLiteOpenHelper {

    static String name ="BlackjackDB";
    static SQLiteDatabase.CursorFactory factory = null;
    static int version = 1;

    public MyDBHelper(Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "CREATE TABLE IF NOT EXISTS  SystemUser (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Email TEXT, Password TEXT) ";
        db.execSQL(SQL);
        String SQL2 = "CREATE TABLE IF NOT EXISTS  GameSetting (ID INTEGER PRIMARY KEY, Title TEXT, DeckCount INTEGER, Amount INTEGER,Gamblabe INTEGER) ";
        db.execSQL(SQL2);
        String SQL3 = "CREATE TABLE IF NOT EXISTS  TempLogin (ID INTEGER PRIMARY KEY, Email TEXT) ";
        db.execSQL(SQL3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
