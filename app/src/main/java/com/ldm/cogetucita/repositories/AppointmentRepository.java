package com.ldm.cogetucita.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ldm.cogetucita.bbdd.AdminSQLiteOpenHelper;
import com.ldm.cogetucita.models.Appointment;
import com.ldm.cogetucita.models.Product;
import com.ldm.cogetucita.models.State;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {
    private Context context;

    public AppointmentRepository(Context context){
        this.context = context;
    }

    public List<Appointment> findAllAppointments(){
        return findAllAppointmentsByState(null);
    }

    public List<Appointment> findAllAppointmentsByState(State state){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        List<Appointment> appointmentList = new ArrayList<>();
        String query = "SELECT id, name, surname, email, date, location, state, product_id FROM Appointment";

        if (state != null) {
            query = "SELECT id, name, surname, email, date, location, state, product_id FROM Appointment WHERE state = " + state.name();
        }

        Cursor cursor = bd.rawQuery(query, null);

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String productId = cursor.getString(7);
                ProductRepository productRepository = new ProductRepository(context);
                Product product = productRepository.findProduct(productId);

                Appointment appointment = new Appointment();
                appointment.setId(Integer.parseInt(cursor.getString(0)));
                appointment.setName(cursor.getString(1));
                appointment.setSurname(cursor.getString(2));
                appointment.setEmail(cursor.getString(3));
                appointment.setDate(cursor.getString(4));
                appointment.setLocation(cursor.getString(5));
                appointment.setState(State.valueOf(cursor.getString(6)));
                appointment.setProduct(product);

                appointmentList.add(appointment);

                cursor.moveToNext();
            }
        }

        cursor.close();
        bd.close();

        return appointmentList;
    }

    public boolean insertAppointment(String productId, String name, String surname, String email, String date, String location){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        boolean done = false;

        if (!productId.isEmpty() && !name.isEmpty() && !email.isEmpty() && !date.isEmpty()){
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("surname", surname);
            contentValues.put("email", email);
            contentValues.put("date", date);
            contentValues.put("location", location);
            contentValues.put("state", State.PENDING.name());
            contentValues.put("product_id", productId);

            bd.insert("Appointment", null, contentValues);
            bd.close();

            done = true;
        }

        return done;
    }
}
