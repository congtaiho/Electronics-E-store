package com.example.w17_application;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;


public class MainActivity extends AppCompatActivity {

    Context context;// NOTE: Replace the below with your own ONESIGNAL_APP_ID
    private static final String ONESIGNAL_APP_ID = "ac1741a2-7998-40c8-9631-167ebbae69b2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        OneSignal.getNotifications().requestPermission(true, Continue.with(r -> {
            if (r.isSuccess()) {
                if (r.getData()) {
                    // `requestPermission` completed successfully and the user has accepted permission
                }
                else {
                    // `requestPermission` completed successfully but the user has rejected permission
                }
            }
            else {
                // `requestPermission` completed unsuccessfully, check `r.getThrowable()` for more info on the failure reason
            }
        }));


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
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        ImageView profile = customActionBar.findViewById(R.id.ProfilePic);
        Drawable profileDrawable = ContextCompat.getDrawable(this, R.drawable.no_user);
        profile.setImageDrawable(profileDrawable);
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intent);
            finish();
        });

        TextView title = customActionBar.findViewById(R.id.TitleOfPage);
        title.setText("The Sac Team - Home");

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


        //WhyUs
        int black = Color.parseColor("#000000");

        ImageView whyUsImgOne = findViewById(R.id.whyUsImgOne);
        Drawable whyUsImgOneDrawable = ContextCompat.getDrawable(this, R.drawable.w1);
        whyUsImgOne.setImageDrawable(whyUsImgOneDrawable);
        whyUsImgOne.setColorFilter(black);

        ImageView whyUsImgTwo = findViewById(R.id.whyUsImgTwo);
        Drawable whyUsImgTwoDrawable = ContextCompat.getDrawable(this, R.drawable.w2);
        whyUsImgTwo.setImageDrawable(whyUsImgTwoDrawable);
        whyUsImgTwo.setColorFilter(black);

        ImageView whyUsImgThree = findViewById(R.id.whyUsImgThree);
        Drawable whyUsImgThreeDrawable = ContextCompat.getDrawable(this, R.drawable.w3);
        whyUsImgThree.setImageDrawable(whyUsImgThreeDrawable);
        whyUsImgThree.setColorFilter(black);

        //Magasiner
        Button btnMagasin = findViewById(R.id.shopButton);
        btnMagasin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            startActivity(intent);
            finish();
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
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_product) {
                    Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_account) {
                    Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_cart) {
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }

}