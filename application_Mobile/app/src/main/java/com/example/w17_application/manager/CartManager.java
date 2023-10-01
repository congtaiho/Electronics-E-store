package com.example.w17_application.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.w17_application.entite.Cart;
import com.example.w17_application.entite.CartProduct;
import com.example.w17_application.entite.Product;
import com.example.w17_application.service.ConnectionDB;

import java.util.ArrayList;

public class CartManager {

    public static Cart getCartByUserId(Context context, int userId) {
        SQLiteDatabase db = ConnectionDB.getBd(context);
        String query = "SELECT * FROM Panier WHERE utilisateur_id = ?";
        Cart cart = null;
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToNext()) {
            cart = new Cart(cursor);
        }
        cursor.close();
        return cart;
    }

    public static void createCart(Context context, int userId) {

        SQLiteDatabase db = ConnectionDB.getBd(context);
        db.execSQL("INSERT INTO Panier (id,utilisateur_id) VALUES (?, ?)", new String[]{String.valueOf(userId), String.valueOf(userId)});

    }


}
