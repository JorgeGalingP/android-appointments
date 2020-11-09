package com.ldm.cogetucita.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase myDatabase) {
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Product(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, price REAL, image TEXT)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Appointment(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT, date TEXT, location TEXT, product_id REFERENCES Product(id))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
