package com.example.w17_application.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.w17_application.entite.Product;
import com.example.w17_application.service.ConnectionDB;

import java.util.ArrayList;

public class ProductManager {

    public static ArrayList<Product> getAll(Context context) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        String query = "select * from Produit";
        ArrayList<Product> products = null;
        Cursor cursor = bd.rawQuery(query, null);
        if (cursor.isBeforeFirst()) {
            products = new ArrayList<>();
            while (cursor.moveToNext()) {
                products.add(new Product(cursor));
            }
        }
        return products;
    }

    public static Product getById(Context context, int id) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        String query = "SELECT * FROM Produit where id = ?";
        Product product = null;
        Cursor cursor = bd.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            product = new Product(cursor);
        }

        return product;
    }

    public static ArrayList<Product> getByCategoryId(Context context, int categoryId) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        String query = "SELECT * FROM Produit where categorie_id = ?";
        ArrayList<Product> products = null;
        Cursor cursor = bd.rawQuery(query, new String[]{String.valueOf(categoryId)});
        if (cursor.isBeforeFirst()) {
            products = new ArrayList<>();
            while (cursor.moveToNext()) {
                products.add(new Product(cursor));
            }
        }
        return products;
    }
}
