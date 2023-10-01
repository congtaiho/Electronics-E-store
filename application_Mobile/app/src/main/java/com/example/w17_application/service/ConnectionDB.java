package com.example.w17_application.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.w17_application.helper.ProductHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectionDB {
    private static int version = 1;
    private static String bnName = "database.db";
    private static SQLiteDatabase bd = null;
    private static ProductHelper helper;

    public static SQLiteDatabase getBd(Context context) {
        if (helper == null) {
            copyDatabaseFromAssets(context);
            helper = new ProductHelper(context, bnName, null, version);
        }
        bd = helper.getWritableDatabase();
        return bd;
    }

    public static void close() {
        if (bd != null && !bd.isOpen()) {
            bd.close();
        }
    }

    public static void copyDatabaseFromAssets(Context context) {
        if (!isDataBase(context)) {
            try {
                int bufferSize = 256;
                OutputStream out = new FileOutputStream(context.getDatabasePath(bnName));
                InputStream in = context.getAssets().open("bd/" + bnName);
                byte[] buffer = new byte[bufferSize];
                while (in.read(buffer) != -1) {
                    out.write(buffer);
                    buffer = new byte[bufferSize];
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isDataBase(Context context) {
        return context.getDatabasePath(bnName).exists();
    }
}
