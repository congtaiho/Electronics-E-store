package com.example.w17_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w17_application.entite.CartProduct;
import com.example.w17_application.entite.Product;
import com.example.w17_application.manager.CartProductManager;
import com.example.w17_application.manager.ProductManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private TextView paymentProductName, paymentProductPrice, paymentProductQuantity, subtotalHT, TVQ, TPS, totalTTC;
    private Button payButton;
    private EditText etFirstName, etLastName, etCardNumber, etExpiryDate, etCvv;
    double priceHT, priceTVQ, priceTPS, priceTTC;
    LinearLayout linearLayout, productLayout;

    LinearLayout linearLayoutViewProducts;
    ArrayList<CartProduct> itemsCartProduct;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    Context context;
    Intent intent;
    String totalAmount;
    int idValue;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        intent = getIntent();
        totalAmount = intent.getStringExtra("totalAmount");
        idValue = intent.getIntExtra("id", -1);

        String page = intent.getStringExtra("page");

        //logged User
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        if (userId != "") {
            itemsCartProduct = CartProductManager.getAllByCartID(context, Integer.parseInt(userId));
        }
        context = this;
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayoutViewProducts = findViewById(R.id.scroller_payment);


        subtotalHT = findViewById(R.id.sous_total_ht);
        TVQ = findViewById(R.id.tvq);
        TPS = findViewById(R.id.tps);
        totalTTC = findViewById(R.id.total_ttc);
        payButton = findViewById(R.id.payButton);
        etFirstName = findViewById(R.id.firstNameEditText);
        etLastName = findViewById(R.id.lastNameEditText);
        etCardNumber = findViewById(R.id.cardNumberEditText);
        etExpiryDate = findViewById(R.id.expiryDateEditText);
        etCvv = findViewById(R.id.cvvEditText);


        if (page.equals("pageDetails")) {
            //    Intent intent;

            String quantityValue = intent.getStringExtra("quantity");
            Product product = ProductManager.getById(context, idValue);
            productLayout = findViewById(R.id.ll_payment_product_detail);
            productLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.single_row_product_payment, null);

            paymentProductName = productLayout.findViewById(R.id.payment_product_name);
            paymentProductPrice = productLayout.findViewById(R.id.payment_product_price);
            paymentProductQuantity = productLayout.findViewById(R.id.payment_product_quantity);

            priceHT = product.getPrice() * Integer.parseInt(quantityValue);
            priceTVQ = priceHT * 0.09975;
            priceTPS = priceHT * 0.05;
            priceTTC = priceHT + priceTVQ + priceTPS;

            paymentProductName.setText(product.getName());
            paymentProductPrice.setText(String.valueOf(product.getPrice()) + "$");
            paymentProductQuantity.setText(quantityValue);
            subtotalHT.setText("Subtotal (HT)     " + decimalFormat.format(priceHT) + "$");
            TVQ.setText("TVQ     " + decimalFormat.format(priceTVQ) + "$");
            TPS.setText("TPS     " + decimalFormat.format(priceTPS) + "$");
            totalTTC.setText("Total TTC     " + decimalFormat.format(priceTTC) + "$");
            linearLayout.addView(productLayout);


        } else {
//            int totalAmount = intent.getIntExtra("totalAmount", -1);
            for (CartProduct cartProduct : itemsCartProduct) {
                productLayout = findViewById(R.id.ll_payment_product_detail);
                productLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.single_row_product_payment, null);

                paymentProductName = productLayout.findViewById(R.id.payment_product_name);
                paymentProductPrice = productLayout.findViewById(R.id.payment_product_price);
                paymentProductQuantity = productLayout.findViewById(R.id.payment_product_quantity);


                Product product = ProductManager.getById(this, cartProduct.getProductId());
                paymentProductName.setText(product.getName());
                paymentProductPrice.setText(String.valueOf(cartProduct.getProductPrice()) + "$");
                paymentProductQuantity.setText(String.valueOf(cartProduct.getProductQuantity()));

                linearLayout.addView(productLayout);

            }
            double totalAmountToDouble = Double.parseDouble(totalAmount);
            priceHT = totalAmountToDouble;
            priceTVQ = totalAmountToDouble * 0.09975;
            priceTPS = totalAmountToDouble * 0.05;
            priceTTC = totalAmountToDouble + priceTVQ + priceTPS;
            subtotalHT.setText("Subtotal (HT)     " + decimalFormat.format(priceHT) + "$");
            TVQ.setText("TVQ     " + decimalFormat.format(priceTVQ) + "$");
            TPS.setText("TPS     " + decimalFormat.format(priceTPS) + "$");
            totalTTC.setText("Total TTC     " + decimalFormat.format(priceTTC) + "$");
        }
        linearLayoutViewProducts.addView(linearLayout);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etFirstName.getText().toString().isEmpty() && !etLastName.getText().toString().isEmpty() && !etCardNumber.getText().toString().isEmpty() && !etExpiryDate.getText().toString().isEmpty() && !etCvv.getText().toString().isEmpty()) {
                    intent = new Intent(context, ThankYouActivity.class);
                    intent.putExtra("totalTTC", totalTTC.getText().toString());
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(context, "Please fill out all the required fields before proceeding. ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}