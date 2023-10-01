package com.example.w17_application.entite;

import android.database.Cursor;

public class Product {

    private int id;
    private String name;
    private String description;
    private int price;
    private int categoryId;
    private String brand;
    private String imageUrl;


    public Product() {
    }

    public Product(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        this.name = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
        this.brand = cursor.getString(cursor.getColumnIndexOrThrow("marque"));
        this.description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        this.categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("categorie_id"));
        this.price = cursor.getInt(cursor.getColumnIndexOrThrow("prix"));
        String imgUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));
        this.imageUrl = imgUrl.substring(15);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
