package com.ldm.cogetucita.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.ldm.cogetucita.bbdd.AdminSQLiteOpenHelper;

public class AppointmentRepository {
    private Context context;

    public AppointmentRepository(Context context){
        this.context = context;
    }

    public boolean registerAppointment(String productId, String name, String surname, String email, String date, String location){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        boolean done = false;

        if (!name.isEmpty() && !email.isEmpty() && !productId.isEmpty()){
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("surname", surname);
            contentValues.put("email", email);
            contentValues.put("date", date);
            contentValues.put("location", location);
            contentValues.put("product_id", productId);

            bd.insert("Product", null, contentValues);
            bd.close();

            done = true;
        }

        return done;
    }
}
