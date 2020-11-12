package com.ldm.cogetucita.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ldm.cogetucita.bbdd.AdminSQLiteOpenHelper;
import com.ldm.cogetucita.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private Context context;

    public ProductRepository(Context context){
        this.context = context;
    }

    public Product findProduct(String id){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        Product product = new Product();

        if (!id.isEmpty()) {
            Cursor cursor = bd.rawQuery("SELECT id, name, description, price, image from Product WHERE id = " + id, null);

            if (cursor.moveToFirst()){
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setName(cursor.getString(1));
                product.setDescription(cursor.getString(2));
                product.setPrice(Float.parseFloat(cursor.getString(3)));
                product.setImage(cursor.getString(4));
            }

            cursor.close();
            bd.close();
        }

        return product;
    }

    public List<Product> searchAllProducts(){
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
}
