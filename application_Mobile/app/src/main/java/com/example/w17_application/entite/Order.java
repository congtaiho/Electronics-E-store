package com.example.w17_application.entite;

import android.database.Cursor;

import java.util.Date;

public class Order {
    private int orderId;
    private int userId;
    private double price;
    private String OrderDate;

    public Order() {
    }

    public Order(int id, int userId, double price, String orderDate) {
        this.orderId = id;
        this.userId = userId;
        this.price = price;
        OrderDate = orderDate;
    }

    public Order(Cursor cursor) {
        this.orderId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        this.userId =cursor.getInt(cursor.getColumnIndexOrThrow("utilisateur_id"));
        this.price = cursor.getDouble(cursor.getColumnIndexOrThrow("montantTotal"));
        OrderDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }
}
