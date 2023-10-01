package com.example.w17_application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.w17_application.entite.Order;
import com.example.w17_application.entite.User;
import com.example.w17_application.manager.OrderManager;
import com.example.w17_application.manager.UserManager;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {

    Context context;
    UserManager userManager = new UserManager();
    Button connect;
    ScrollView scrollView;
    LinearLayout llOrders;
    TextView tvOrderNumber, tvOrderPrice, tvOrderDate;
    Button btnLogOut;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

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
            Intent intent = new Intent(AccountActivity.this, CartActivity.class);
            startActivity(intent);
        });

        ImageView profile = customActionBar.findViewById(R.id.ProfilePic);
        Drawable profileDrawable = ContextCompat.getDrawable(this, R.drawable.no_user);
        profile.setImageDrawable(profileDrawable);
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AccountActivity.class);
            startActivity(intent);
            finish();
        });

        TextView title = customActionBar.findViewById(R.id.TitleOfPage);
        title.setText("The Sac Team - Account");

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

        //Recup info
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        connect = findViewById(R.id.connection);

        connect.setOnClickListener(v -> {
            if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() || userId != "") {

                User user;
                if (userId != "") {
                    user = userManager.getById(context, Integer.parseInt(userId));
                } else {
                    user = userManager.getByEmailNPass(context, email.getText().toString(), password.getText().toString());
                }
                if (user == null) {
                    Toast.makeText(context, "Wrong password or email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId", String.valueOf(user.getId()));
                    editor.apply();

                    btnLogOut = findViewById(R.id.btnLogOut);
                    btnLogOut.setOnClickListener(v1 -> {
                        editor.clear();
                        editor.apply();

                        Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show();

                        Intent intentReload = new Intent(AccountActivity.this, AccountActivity.class);
                        finish();
                        startActivity(intentReload);
                    });

                    LinearLayout pageLogin = findViewById(R.id.pageUser);
                    pageLogin.setVisibility(View.GONE);
                    LinearLayout pageUserLoggedIn = findViewById(R.id.pageUserLoggedIn);
                    pageUserLoggedIn.setVisibility(View.VISIBLE);

                    EditText loggedName = findViewById(R.id.inputNameUser);
                    loggedName.setText(user.getNom());
                    EditText loggedEmail = findViewById(R.id.inputEmailUser);
                    loggedEmail.setText(user.getEmail());
                    EditText loggedPass = findViewById(R.id.inputPasswordUser);
                    loggedPass.setText(user.getPassword());
                    EditText loggedImgName = findViewById(R.id.inputImgUser);
                    loggedImgName.setText(user.getImage_profil());

                    ImageView imgUser = findViewById(R.id.imgUser);
                    Drawable imgUserDrawable = ContextCompat.getDrawable(this, R.drawable.no_user);
                    imgUser.setImageDrawable(imgUserDrawable);

                    Button btnMyOrders = findViewById(R.id.btn_my_orders);
                    ArrayList<Order> getAllorder = OrderManager.getAllByUserId(context, user.getId());

                    btnMyOrders.setOnClickListener(v1 -> {
                        setContentView(R.layout.list_orders);
                        scrollView = findViewById(R.id.orders_scroller);
                        llOrders = new LinearLayout(context);
                        llOrders.setOrientation(LinearLayout.VERTICAL);
                        for (Order order : getAllorder) {
                            LinearLayout orderLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.single_row_order, null);
                            tvOrderNumber = orderLayout.findViewById(R.id.order_number);
                            tvOrderPrice = orderLayout.findViewById(R.id.order_price);
                            tvOrderDate = orderLayout.findViewById(R.id.order_date);

                            tvOrderNumber.setText("#" + String.valueOf(order.getOrderId()));
                            tvOrderPrice.setText(String.valueOf(order.getPrice()) + "$");
                            tvOrderDate.setText(order.getOrderDate());
                            llOrders.addView(orderLayout);
                        }
                        scrollView.addView(llOrders);
                    });

                    Button btnSave = findViewById(R.id.saveBtn);
                    btnSave.setOnClickListener(v1 -> {
                        if (loggedName.getText().toString().isEmpty() || loggedEmail.getText().toString().isEmpty() || loggedPass.getText().toString().isEmpty() || loggedImgName.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Enter all Information", Toast.LENGTH_SHORT).show();
                        } else {
                            User updateUser = new User();
                            updateUser.setId(user.getId());
                            updateUser.setNom(loggedName.getText().toString());
                            updateUser.setEmail(loggedEmail.getText().toString());
                            updateUser.setPassword(loggedPass.getText().toString());
                            updateUser.setImage_profil(loggedImgName.getText().toString());
                            userManager.updateUser(context, updateUser);

                            Intent intentReload = new Intent(AccountActivity.this, AccountActivity.class);
                            intentReload.getIntExtra("userId", user.getId());
                            finish();
                            startActivity(intentReload);
                        }
                    });

                    Button btnDelete = findViewById(R.id.deleteBtn);
                    btnDelete.setOnClickListener(v1 -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {

                            userManager.delete(context, user.getId());

                            dialog.dismiss();

                            Intent intentToMain = new Intent(AccountActivity.this, MainActivity.class);
                            finish();
                            startActivity(intentToMain);
                        });

                        builder.setNegativeButton("No", (dialog, which) -> {
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    });
                }
            } else {
                Toast.makeText(context, "Fill all boxes", Toast.LENGTH_SHORT).show();
            }
        });

        if (userId != "") {
            connect.performClick();
        }

        TextView inscription = findViewById(R.id.inscription);
        inscription.setOnClickListener(v -> {
            View dialogView = getLayoutInflater().inflate(R.layout.new_user_input_alert, null);

            EditText editTextName = dialogView.findViewById(R.id.inputNameUser);
            EditText editTextEmail = dialogView.findViewById(R.id.inputEmailUser);
            EditText editTextPassword = dialogView.findViewById(R.id.inputPasswordUser);
            EditText editTextImgUser = dialogView.findViewById(R.id.inputImgUser);
            EditText editTextCivicNumber = dialogView.findViewById(R.id.inputCivicNumberUser);
            EditText editTextStreet = dialogView.findViewById(R.id.inputStreetUser);
            EditText editTextCity = dialogView.findViewById(R.id.inputCityUser);
            EditText editTextCountry = dialogView.findViewById(R.id.inputCountryUser);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("New User");
            builder.setMessage("Fill out info :");
            builder.setView(dialogView);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String nameUser = editTextName.getText().toString();
                String emailUser = editTextEmail.getText().toString();
                String passwordUser = editTextPassword.getText().toString();
                String imgUser = editTextImgUser.getText().toString();
                String civicNumber = editTextCivicNumber.getText().toString();
                String street = editTextStreet.getText().toString();
                String city = editTextCity.getText().toString();
                String country = editTextCountry.getText().toString();

                if (!nameUser.isEmpty() && !emailUser.isEmpty() && !passwordUser.isEmpty() && !imgUser.isEmpty()) {
                    createUserAndRefresh(nameUser, emailUser, passwordUser, imgUser, civicNumber, street, city, country);
                    Toast.makeText(context, "succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Fill all boxes", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    private void createUserAndRefresh(String name, String email, String password, String userImg, String civicNumber, String street, String city, String country) {
        User newUser = new User();
        newUser.setNom(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setImage_profil(userImg);
        newUser.setNo_civique(civicNumber);
        newUser.setStreet(street);
        newUser.setCity(city);
        newUser.setPays(country);

        userManager.AddUser(context, newUser);

        Intent intent = getIntent();
        finish();
        startActivity(intent);
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
                    Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_product) {
                    Intent intent = new Intent(AccountActivity.this, ProductActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_account) {
                    Intent intent = new Intent(AccountActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_cart) {
                    Intent intent = new Intent(AccountActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }

}
