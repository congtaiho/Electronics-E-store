package com.example.w17_application.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.w17_application.entite.Order;
import com.example.w17_application.entite.Product;
import com.example.w17_application.service.ConnectionDB;

import java.util.ArrayList;

public class OrderManager {

    public static ArrayList<Order> getAllByUserId(Context context, int userId) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        String query = "select * from Commande where utilisateur_id = ?";
        ArrayList<Order> orders = null;
        Cursor cursor = bd.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.isBeforeFirst()) {
            orders = new ArrayList<>();
            while (cursor.moveToNext()) {
                orders.add(new Order(cursor));
            }
        }
        return orders;
    }

    public static Order getById(Context context, int id) {
        SQLiteDatabase bd = ConnectionDB.getBd(context);
        String query = "SELECT * FROM Commande where id = ?";
        Order order = null;
        Cursor cursor = bd.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            order = new Order(cursor);
        }
        return order;
    }

    public static void addOrder(Context context, int orderId, int userId, double price, String orderDate) {
        SQLiteDatabase db = ConnectionDB.getBd(context);

        String query = "INSERT INTO Commande (id, utilisateur_id, montantTotal, date) VALUES (?, ?, ?, ?)";
        db.execSQL(query, new Object[]{orderId, userId, price, orderDate});
    }

}
