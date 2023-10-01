package com.example.w17_application.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.w17_application.entite.User;
import com.example.w17_application.service.ConnectionDB;

import java.util.ArrayList;

public class UserManager {
    public ArrayList<User> getAll(Context context) {
        SQLiteDatabase db = ConnectionDB.getBd(context);
        String request = "SELECT * FROM Utilisateur";
        ArrayList<User> users = null;
        Cursor cursor = db.rawQuery(request, null);
        if (cursor.isBeforeFirst()) {
            users = new ArrayList<>();
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                user.setNom(cursor.getString(cursor.getColumnIndexOrThrow("nom")));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                user.setImage_profil(cursor.getString(cursor.getColumnIndexOrThrow("image_profil")));
                user.setStreet(cursor.getString(cursor.getColumnIndexOrThrow("street")));
                user.setCity(cursor.getString(cursor.getColumnIndexOrThrow("city")));
                user.setPays(cursor.getString(cursor.getColumnIndexOrThrow("pays")));
                user.setNo_civique(cursor.getString(cursor.getColumnIndexOrThrow("no_civique")));
                users.add(user);
            }
        }
        return users;
    }

    public void AddUser(Context context, User newUser) {
        ArrayList<User> users = getAll(context);

        SQLiteDatabase db = ConnectionDB.getBd(context);
        int id = users.size() + 1;
        String nom = newUser.getNom();
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        String userImg = newUser.getImage_profil();
        String noCivique = newUser.getNo_civique();
        String street = newUser.getStreet();
        String city = newUser.getCity();
        String pays = newUser.getPays();


        String query = "INSERT INTO Utilisateur VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        SQLiteStatement statement = db.compileStatement(query);
        statement.bindLong(1, id);
        statement.bindString(2, nom);
        statement.bindString(3, email);
        statement.bindString(4, password);
        statement.bindString(5, noCivique);
        statement.bindString(6, street);
        statement.bindString(7, city);
        statement.bindString(8, pays);
        statement.bindString(9, userImg);


        statement.executeInsert();
    }

    public static User getById(Context context, int id) {

        SQLiteDatabase db = ConnectionDB.getBd(context);
        String sql = "SELECT * FROM Utilisateur WHERE id = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {

            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            user.setNom(cursor.getString(cursor.getColumnIndexOrThrow("nom")));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            user.setImage_profil(cursor.getString(cursor.getColumnIndexOrThrow("image_profil")));
            user.setStreet(cursor.getString(cursor.getColumnIndexOrThrow("street")));
            user.setCity(cursor.getString(cursor.getColumnIndexOrThrow("city")));
            user.setPays(cursor.getString(cursor.getColumnIndexOrThrow("pays")));
            user.setNo_civique(cursor.getString(cursor.getColumnIndexOrThrow("no_civique")));

            cursor.close();
            return user;
        } else {
            cursor.close();
            return null;
        }
    }

    public User getByEmailNPass(Context context, String email, String password) {

        SQLiteDatabase db = ConnectionDB.getBd(context);
        String sql = "SELECT * FROM Utilisateur WHERE email = ? AND password = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{email, password});

        if (cursor.moveToFirst()) {

            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            user.setNom(cursor.getString(cursor.getColumnIndexOrThrow("nom")));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            user.setImage_profil(cursor.getString(cursor.getColumnIndexOrThrow("image_profil")));
            user.setStreet(cursor.getString(cursor.getColumnIndexOrThrow("street")));
            user.setCity(cursor.getString(cursor.getColumnIndexOrThrow("city")));
            user.setPays(cursor.getString(cursor.getColumnIndexOrThrow("pays")));
            user.setNo_civique(cursor.getString(cursor.getColumnIndexOrThrow("no_civique")));

            cursor.close();
            return user;
        } else {
            cursor.close();
            return null;
        }
    }

    public static void updateUser(Context context, User userToUpdate) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", userToUpdate.getId());
        contentValues.put("nom", userToUpdate.getNom());
        contentValues.put("email", userToUpdate.getEmail());
        contentValues.put("password", userToUpdate.getPassword());
        contentValues.put("image_profil", userToUpdate.getImage_profil());
        contentValues.put("street", userToUpdate.getStreet());
        contentValues.put("city", userToUpdate.getCity());
        contentValues.put("pays", userToUpdate.getPays());
        contentValues.put("no_civique", userToUpdate.getNo_civique());
        SQLiteDatabase db = ConnectionDB.getBd(context);

        db.update("Utilisateur", contentValues, "id = ?", new String[]{String.valueOf(userToUpdate.getId())});
    }

    public static void delete(Context context, int idUser) {
        SQLiteDatabase db = ConnectionDB.getBd(context);
        db.delete("Utilisateur", "id = ?", new String[]{String.valueOf(idUser)});
    }
}
