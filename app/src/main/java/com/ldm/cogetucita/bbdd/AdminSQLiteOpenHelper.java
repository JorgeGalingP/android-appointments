package com.ldm.cogetucita.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.ldm.cogetucita.models.Product;

import java.util.ArrayList;
import java.util.List;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Product(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, price REAL, image TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Appointment(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT, email TEXT, date TEXT, location TEXT, product_id REFERENCES Product(id))");

        db.execSQL("INSERT INTO Product(name, description, price, image) VALUES ('Maquillaje', 'El maquillaje es la práctica de decorar la piel y otras partes visibles del cuerpo para resaltarlas o mejorar su aspecto. Tambien designa también los cosméticos que se emplean, tales como los lápices de labios y sombras de ojos.', 25.50, 'makeup')");
        db.execSQL("INSERT INTO Product(name, description, price, image) VALUES ('Manicura', 'Una manicura es un tratamiento de belleza cosmético para las uñas y manos que suele realizarse en casa o en un salón de belleza. En una manicura se cortan o liman los bordes de las uñas, se realizan masajes a las manos y se aplica esmalte de uñas.', 17.50, 'nails')");
        db.execSQL("INSERT INTO Product(name, description, price, image) VALUES ('Lifting', 'El lifting facial es una intervención estética que tiene como finalidad principal combatir el envejecimiento facial. Para eliminar las arrugas e imperfecciones de la cara y el cuello la piel se estira mediante una cirugía mínimamente invasiva.', 23.75, 'lifting')");
        db.execSQL("INSERT INTO Product(name, description, price, image) VALUES ('Pedicura', 'La pedicura es el tratamiento de las afecciones cutáneas córneas propias de los pies. Un tratamiento de pedicura también es una manera de mejorar el aspecto de los pies y las uñas.', 35.00, 'pedicure')");
        db.execSQL("INSERT INTO Product(name, description, price, image) VALUES ('Peluquería', 'Una peluquería o barbería es un local donde se ofrecen varios servicios estéticos, principalmente el corte de pelo, pero también suelen realizarse otros como afeitado, depilado, manicura, pedicura, etc.', 10.25, 'hair')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Product searchProduct(String name, Context context){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        Product product = new Product();

        if (!name.isEmpty()) {
            Cursor cursor = bd.rawQuery("SELECT id, name, description, price, image from Product WHERE name = " + name, null);

            if (cursor.moveToFirst()){
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setName(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setPrice(Float.parseFloat(cursor.getString(3)));
                product.setDescription(cursor.getString(4));
            }

            cursor.close();
            bd.close();
        }

        return product;
    }

    public List<Product> searchAllProducts(Context context){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        List<Product> productList = new ArrayList<>();

        Cursor cursor = bd.rawQuery("SELECT id, name, description, price, image from Product", null);

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setName(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setPrice(Float.parseFloat(cursor.getString(3)));
                product.setImage(cursor.getString(4));

                productList.add(product);

                cursor.moveToNext();
            }
        }

        cursor.close();
        bd.close();

        return productList;
    }

    public boolean registerAppointment(String name, String surname, String email, String date, String location, Product product, Context context){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        boolean done = false;

        if (!name.isEmpty() && !email.isEmpty() && product.getId() != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("surname", surname);
            contentValues.put("email", email);
            contentValues.put("date", date);
            contentValues.put("location", location);
            contentValues.put("product_id", product.getId());

            bd.insert("Product", null, contentValues);
            bd.close();

            done = true;
        }

        return done;
    }
}
