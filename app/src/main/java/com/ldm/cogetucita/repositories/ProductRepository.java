package com.ldm.cogetucita.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ldm.cogetucita.bbdd.AdminSQLiteOpenHelper;
import com.ldm.cogetucita.models.Product;
import com.ldm.cogetucita.models.State;

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
            Cursor cursor = bd.rawQuery("SELECT id, name, description, price, image FROM Product WHERE id = " + id, null);

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

    public List<Product> findAllProducts(){
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

    public boolean insertProduct(String name, String description, float price, String image){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        boolean done = false;

        if (!name.isEmpty() && !description.isEmpty() && !image.isEmpty()){
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("description", description);
            contentValues.put("price", price);
            contentValues.put("image", image);

            bd.insert("Product", null, contentValues);
            bd.close();

            done = true;
        }

        return done;
    }

    public boolean updateProduct(String productId, String name, String description, float price, String image){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        boolean done = false;

        if (!productId.isEmpty()
                && !name.isEmpty()
                && !description.isEmpty()
                && !image.isEmpty()){
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("description", description);
            contentValues.put("price", price);
            contentValues.put("image", image);

            int nValue = bd.update("Product", contentValues, "id=" + productId, null);
            bd.close();

            if (nValue == 1){
                done = true;
            }
        }

        return done;
    }

    public boolean deleteProduct(String productId){
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "db", null, 1);
        SQLiteDatabase bd = adminSQLiteOpenHelper.getWritableDatabase();

        boolean done = false;

        if (!productId.isEmpty()){
            int nValue = bd.delete("Product", "id=" + productId, null);
            bd.close();

            if (nValue == 1){
                done = true;
            }
        }

        return done;
    }
}
