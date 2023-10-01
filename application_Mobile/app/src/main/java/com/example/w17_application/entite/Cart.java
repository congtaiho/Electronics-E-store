package com.example.w17_application.entite;

import android.database.Cursor;

public class Cart {
    private int userId;
    private int cartId;

    public Cart() {
    }

    public Cart(int userId, int cartId) {
        this.userId = userId;
        this.cartId = cartId;
    }

    public Cart(Cursor cursor) {
        this.userId = cursor.getInt(cursor.getColumnIndexOrThrow("utilisateur_id"));
        this.cartId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}
