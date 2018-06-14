package com.deimzbet.android.neworder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.deimzbet.android.neworder.database.OrderDBSchema.*;

public class OrderBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "orders.db";

    public OrderBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + OrderTable.NAME + "(" +
        " _id integer primary key autoincrement, " +
        OrderTable.Cols.UUID + ", " +
        OrderTable.Cols.TITLE + ", " +
        OrderTable.Cols.TYPE + ", " +
        OrderTable.Cols.DATE + ", " +
        OrderTable.Cols.FINISHED + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
