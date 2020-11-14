package com.ldm.cogetucita.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
