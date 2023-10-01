package com.example.w17_application.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.w17_application.entite.Category;
import com.example.w17_application.entite.Product;
import com.example.w17_application.service.ConnectionDB;

import java.util.ArrayList;

public class CategoryManager {
    public static ArrayList<Category> getAll(Context context) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        String query = "select * from Categorie";
        ArrayList<Category> categories = null;
        Cursor cursor = bd.rawQuery(query, null);
        if (cursor.isBeforeFirst()) {
            categories = new ArrayList<>();
            while (cursor.moveToNext()) {
                categories.add(new Category(cursor));
            }
        }
        return categories;
    }
}
