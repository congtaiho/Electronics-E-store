package com.example.w17_application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.w17_application.entite.Cart;
import com.example.w17_application.entite.Product;
import com.example.w17_application.manager.CartManager;
import com.example.w17_application.manager.CartProductManager;
import com.example.w17_application.manager.ProductManager;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {
    Context context;
    private ImageView productImage;
    private TextView productName, productPrice, productDescription;
    private EditText quantityEditText;
    private Button minusButton, plusButton, addToCartButton, buyNowButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;

        //logged User
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        // ACTION BAR
        View customActionBar = getLayoutInflater().inflate(R.layout.custom_action_bar, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(customActionBar, layoutParams);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView cart = customActionBar.findViewById(R.id.CartImg);
        Drawable cartDrawable = ContextCompat.getDrawable(this, R.drawable.yellow_shopping_cart);
        cart.setImageDrawable(cartDrawable);
        cart.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, CartActivity.class);
            startActivity(intent);
        });

        ImageView profile = customActionBar.findViewById(R.id.ProfilePic);
        Drawable profileDrawable = ContextCompat.getDrawable(this, R.drawable.no_user);
        profile.setImageDrawable(profileDrawable);
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, AccountActivity.class);
            startActivity(intent);
            finish();
        });

        TextView title = customActionBar.findViewById(R.id.TitleOfPage);
        title.setText("The Sac Team - Products");

        //NAV BAR
        ImageView burgerMenu = findViewById(R.id.BurgerMenu);
        Drawable burgerMenuDrawable = ContextCompat.getDrawable(this, R.drawable.burger_menu_light);
        burgerMenu.setImageDrawable(burgerMenuDrawable);

        Spinner burgerSpinnerPopUp = findViewById(R.id.burgerSpinnerPopUp);

        burgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        quantityEditText = findViewById(R.id.quantityEditText);
        minusButton = findViewById(R.id.minusButton);
        plusButton = findViewById(R.id.plusButton);
        addToCartButton = findViewById(R.id.addToCartButton);
        buyNowButton = findViewById(R.id.buyNowButton);


        Intent intent = getIntent();
        int idValue = intent.getIntExtra("id", -1);
        Product product = ProductManager.getById(this, idValue);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(String.valueOf(product.getPrice()) + "$");
        try {
            Bitmap bitmapImage = BitmapFactory.decodeStream(context.getAssets().open("images/" + product.getImageUrl()));
            productImage.setImageBitmap(bitmapImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int initialQuantity = 1; // Quantité initiale
        quantityEditText.setText(String.valueOf(initialQuantity));

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQuantity = Integer.parseInt(quantityEditText.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    quantityEditText.setText(String.valueOf(currentQuantity));
                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQuantity = Integer.parseInt(quantityEditText.getText().toString());
                currentQuantity++;
                quantityEditText.setText(String.valueOf(currentQuantity));
            }
        });
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // quantityEditText.getText().toString();
                // ajout au panier avec la quantité spécifiée
//                Intent intent = new Intent(context, CartActivity.class);
//                intent.putExtra("id", product.getId());
//                intent.putExtra("quantity", quantityEditText.getText().toString());
//                finish();
//                startActivity(intent);
                if (userId != "") {
                    Cart existingCart = CartManager.getCartByUserId(context, Integer.parseInt(userId));
                    if (existingCart == null) {
                        CartManager.createCart(context, Integer.parseInt(userId));
                    }

                    Cart cartUser = CartManager.getCartByUserId(context, Integer.parseInt(userId));
                    Log.d("tata", "getCartId : " + cartUser.getCartId());
                    int quantity = Integer.parseInt(String.valueOf(quantityEditText.getText()));
                    CartProductManager.addProduct(context, product.getId(), cartUser.getCartId(), quantity, product.getPrice());
                    finish();
                    Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(DetailsActivity.this, AccountActivity.class);
                    startActivity(intent);
                    Toast.makeText(context, "You must log in to your account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // acheter immédiatement
//                Toast.makeText(context, "buy now", Toast.LENGTH_SHORT).show();
                if (userId != "") {
                    Intent intent = new Intent(context, PaymentActivity.class);
                    intent.putExtra("id", product.getId());
                    intent.putExtra("quantity", quantityEditText.getText().toString());
                    intent.putExtra("page", "pageDetails");
                    finish();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DetailsActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(context, "You must log in to your account", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_image_popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_home) {
                    Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_product) {
                    Intent intent = new Intent(DetailsActivity.this, ProductActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_account) {
                    Intent intent = new Intent(DetailsActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_cart) {
                    Intent intent = new Intent(DetailsActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }
}
