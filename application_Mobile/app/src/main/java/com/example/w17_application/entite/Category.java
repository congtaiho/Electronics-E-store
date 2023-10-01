package com.example.w17_application.entite;

import android.database.Cursor;

public class Category {
    private int categoryId;
    private String categoryName;


    public Category() {
    }

    public Category(Cursor cursor) {
        this.categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        this.categoryName = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
