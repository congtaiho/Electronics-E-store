package com.example.w17_application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.w17_application.entite.CartProduct;
import com.example.w17_application.entite.Product;
import com.example.w17_application.entite.User;
import com.example.w17_application.manager.CartProductManager;
import com.example.w17_application.manager.ProductManager;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    ScrollView scrollView;
    Context context;
    LinearLayout linearLayout;
    ImageButton btnDelete;
    Button btnCheckOut;
    TextView tvProductName, tvProductPrice, tvProductQuantity, tvTotalAmount;
    private double totalAmount = 0.0;
    User user;

    ArrayList<CartProduct> itemsCartProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //logged User
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");


        if (userId != "") {
            setContentView(R.layout.activity_cart);


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
                Intent intent = new Intent(CartActivity.this, CartActivity.class);
                startActivity(intent);
            });

            ImageView profile = customActionBar.findViewById(R.id.ProfilePic);
            Drawable profileDrawable = ContextCompat.getDrawable(this, R.drawable.no_user);
            profile.setImageDrawable(profileDrawable);
            profile.setOnClickListener(v -> {
                Intent intent = new Intent(CartActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            });

            TextView title = customActionBar.findViewById(R.id.TitleOfPage);
            title.setText("The Sac Team - Cart");

//            //NAV BAR
//            ImageView burgerMenu = findViewById(R.id.BurgerMenu);
//            Drawable burgerMenuDrawable = ContextCompat.getDrawable(this, R.drawable.burger_menu_light);
//            burgerMenu.setImageDrawable(burgerMenuDrawable);
//
//            Spinner burgerSpinnerPopUp = findViewById(R.id.burgerSpinnerPopUp);
//
//            burgerMenu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showPopupMenu(view);
//                }
//            });

            itemsCartProduct = CartProductManager.getAllByCartID(context, Integer.parseInt(userId));

            scrollView = findViewById(R.id.scroller);

            linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);


            for (CartProduct cartProduct : itemsCartProduct) {
                LinearLayout cartLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.single_row_design, null);
                tvProductName = cartLayout.findViewById(R.id.cart_product_name);
                tvProductPrice = cartLayout.findViewById(R.id.cart_product_prix);
                tvProductQuantity = cartLayout.findViewById(R.id.cart_product_quantity);

                Product product = ProductManager.getById(this, cartProduct.getProductId());

                btnDelete = cartLayout.findViewById(R.id.delete_btn);
                tvProductName.setText(String.valueOf(getFirstNWords(product.getName(), 5)));
                tvProductPrice.setText(String.valueOf(cartProduct.getProductPrice() + "$"));
                tvProductQuantity.setText(String.valueOf(cartProduct.getProductQuantity()));

                double productAmount = cartProduct.getProductPrice() * cartProduct.getProductQuantity();
                totalAmount += productAmount;

                btnDelete.setOnClickListener(v -> {
                    CartProductManager.delete(this, cartProduct.getProductId(), Integer.parseInt(userId));
                    linearLayout.removeView(cartLayout);

                    CartProductManager.delete(this, cartProduct.getProductId(), Integer.parseInt(userId));
                    linearLayout.removeView(cartLayout);
                    // Mettez à jour le montant total après la suppression du produit.
                    double productAmountDelete = cartProduct.getProductPrice() * cartProduct.getProductQuantity();
                    totalAmount -= productAmountDelete;
                    tvTotalAmount.setText("Total Amount : " + String.format("%.2f$", totalAmount));
                });

                linearLayout.addView(cartLayout);
            }
            scrollView.addView(linearLayout);
//        Intent intent = getIntent();
//        int userId = Integer.parseInt(intent.getStringExtra("userId"));
//        Toast.makeText(context, "user.getId() " + "1" + userId, Toast.LENGTH_LONG).show();

            tvTotalAmount = findViewById(R.id.tv_total_amount);
            tvTotalAmount.setText("Total Amount : " + String.format("%.2f$", totalAmount));
            btnCheckOut = findViewById(R.id.btn_checkout);
            btnCheckOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (totalAmount != 0) {

                        Intent intent = new Intent(context, PaymentActivity.class);
                        intent.putExtra("totalAmount", String.valueOf(totalAmount).toString());
                        intent.putExtra("page", "pageCart");
                        finish();
                        startActivity(intent);
                    }
                }
            });
        } else {
            Intent intent = new Intent(CartActivity.this, AccountActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(context, "You must log in to your account", Toast.LENGTH_SHORT).show();

        }
    }

    public String getFirstNWords(String input, int n) {
        String[] words = input.split("\\s+");
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i < Math.min(n, words.length); i++) {
            newString.append(words[i]).append(" ");
        }

        return newString.toString().trim();
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
                    Intent intent = new Intent(CartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_product) {
                    Intent intent = new Intent(CartActivity.this, ProductActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_account) {
                    Intent intent = new Intent(CartActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_cart) {
                    Intent intent = new Intent(CartActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }
}
