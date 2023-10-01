package com.example.w17_application.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.w17_application.R;
import com.example.w17_application.entite.Product;

import java.io.IOException;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    int mRessource;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        mRessource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mRessource, parent, false);
        } else {
            view = convertView;
        }
        Product product = getItem(position);
        TextView tvNameProduct = view.findViewById(R.id.tv_product_name);
        TextView tvPriceProduct = view.findViewById(R.id.tv_product_price);

        ImageView imgProduct = view.findViewById(R.id.img_product);
        try {
            Bitmap bitmapImage = BitmapFactory.decodeStream(getContext().getAssets().open("images/" + product.getImageUrl()));
            imgProduct.setImageBitmap(bitmapImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tvNameProduct.setText(getFirstNWords(product.getName(),3));
        tvPriceProduct.setText(String.valueOf(product.getPrice()) + "$");
        return view;

    }

    public String getFirstNWords(String input, int n) {
        String[] words = input.split("\\s+");
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i < Math.min(n, words.length); i++) {
            newString.append(words[i]).append(" ");
        }

        return newString.toString().trim();
    }
}
