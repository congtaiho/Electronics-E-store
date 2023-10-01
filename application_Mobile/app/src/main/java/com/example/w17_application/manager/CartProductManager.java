package com.example.w17_application.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.w17_application.entite.CartProduct;
import com.example.w17_application.service.ConnectionDB;

import java.util.ArrayList;

public class CartProductManager {
    public static void addProduct(Context context, int productId, int panierId, int quantity, int price) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        Cursor cursor = bd.rawQuery("SELECT * FROM ItemPanier WHERE produit_id = ?", new String[]{String.valueOf(productId)});
        if (cursor.getCount() > 0) {
            bd.execSQL("UPDATE ItemPanier SET quantity = quantity + ? WHERE produit_id = ?", new String[]{String.valueOf(quantity), String.valueOf(productId)});
        } else {
            bd.execSQL("INSERT INTO ItemPanier (produit_id,panier_id, quantity,prix) VALUES (?, ?, ?, ?)", new String[]{String.valueOf(productId), String.valueOf(panierId), String.valueOf(quantity), String.valueOf(price)});
        }
        cursor.close();
    }

    public static ArrayList<CartProduct> getAllByCartID(Context context, int cartId) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        ArrayList<CartProduct> retour = null;
        Cursor cursor = bd.rawQuery("select * from ItemPanier where panier_id = ?", new String[]{String.valueOf(cartId)});
        if (cursor.isBeforeFirst()) {
            retour = new ArrayList<>();
            while (cursor.moveToNext()) {
                retour.add(new CartProduct(cursor));
            }
        }
        return retour;
    }

    public static void delete(Context context, int idProduct, int carteId) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        String selection = "produit_id = ? AND panier_id = ?";
        String[] selectionArgs = {String.valueOf(idProduct), String.valueOf(carteId)};
        bd.delete("ItemPanier", selection, selectionArgs);
    }

    public static void deleteAllCartProducts(Context context, int carteId) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        bd.delete("ItemPanier", "panier_id = ?", new String[]{String.valueOf(carteId)});
    }
}
