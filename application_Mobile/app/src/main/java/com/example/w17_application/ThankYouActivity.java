package com.example.w17_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w17_application.entite.User;
import com.example.w17_application.manager.CartProductManager;
import com.example.w17_application.manager.OrderManager;
import com.example.w17_application.manager.ProductManager;
import com.example.w17_application.manager.UserManager;
import com.example.w17_application.service.EmailSender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ThankYouActivity extends AppCompatActivity {

    private TextView orderNumber, orderDate, orderEmail;
    Intent intent;
    Context context;
    Button btnHome;

    public static String genererNumeroCommande() {
        String chiffresPossibles = "0123456789";
        int longueurNumero = 8;

        Random random = new Random();
        StringBuilder numeroCommande = new StringBuilder();

        for (int i = 0; i < longueurNumero; i++) {
            int index = random.nextInt(chiffresPossibles.length());
            char chiffreAleatoire = chiffresPossibles.charAt(index);
            numeroCommande.append(chiffreAleatoire);
        }

        return numeroCommande.toString();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        context = this;
        //logged User
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        Date maintenant = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateEtHeure = format.format(maintenant);
        intent = getIntent();
        String totalTTC = intent.getStringExtra("totalTTC");
        String newTotalTTC = totalTTC.substring(14, totalTTC.length() - 1);
        orderNumber = findViewById(R.id.order_number);
        btnHome = findViewById(R.id.btn_to_home);
        orderDate = findViewById(R.id.order_date);
        orderEmail = findViewById(R.id.order_email_sent);
        User user = UserManager.getById(context,Integer.parseInt(userId));
        String receiverEmail = user.getEmail();
        int orderId = Integer.parseInt(genererNumeroCommande());
        orderNumber.setText("Your Order: #" + orderId);
        orderDate.setText("Order Date: " + dateEtHeure);
        orderEmail.setText("We have sent the order confirmation details to " + receiverEmail);
        OrderManager.addOrder(context, orderId, Integer.parseInt(userId), Double.parseDouble(newTotalTTC), dateEtHeure);

        String subject = "Order Confirmation - #" + orderId;
        String msgBody = "Thank you for choosing The Sac Team for your recent purchase. We are excited to confirm your order and provide you with the details below:\n" +
            "\n" +
            "Order Number: #" + orderId + "\n" +
            "Order Date: " + dateEtHeure + "\n";
        EmailSender.sendEmail(receiverEmail, subject, msgBody);

        CartProductManager.deleteAllCartProducts(context, Integer.parseInt(userId));

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}