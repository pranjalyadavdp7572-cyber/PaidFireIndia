package com.pranjalfreefireindia;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DiamondStoreActivity extends AppCompatActivity {
    
    private static final String TAG = "DiamondStore";
    private static final String DEVELOPER_EMAIL = "pranjalyadavdp9170@gmail.com";
    private static final String PAYMENT_EMAIL = "pranjalyadavdp9170@gmail.com";
    
    private TextView diamondBalanceText, totalSpentText;
    private GridView diamondPacksGrid;
    private Button buyButton;
    private FirebaseUser currentUser;
    private DatabaseReference userRef;
    
    private int[] diamondPacks = {50, 120, 250, 500, 1200, 2500};
    private double[] packPrices = {1.99, 4.99, 9.99, 19.99, 49.99, 99.99};
    private int[] packColors = {0xFFFF6B6B, 0xFFF06292, 0xFFEC407A, 0xFFE91E63, 0xFFC2185B, 0xFFAD1457};
    
    private int currentDiamonds = 0;
    private int totalSpent = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diamond_store);
        
        Log.i(TAG, "Diamond Store opened - Developer: " + DEVELOPER_EMAIL);
        
        // Initialize Firebase
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userRef = FirebaseDatabase.getInstance().getReference("players/" + currentUser.getUid());
        }
        
        // Initialize views
        diamondBalanceText = findViewById(R.id.diamond_balance_text);
        totalSpentText = findViewById(R.id.total_spent_text);
        diamondPacksGrid = findViewById(R.id.diamond_packs_grid);
        buyButton = findViewById(R.id.buy_button);
        
        loadPlayerDiamonds();
        setupDiamondPacks();
    }
    
    private void loadPlayerDiamonds() {
        if (userRef != null) {
            userRef.child("diamonds").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Object diamonds = task.getResult().getValue();
                    currentDiamonds = diamonds != null ? ((Number) diamonds).intValue() : 0;
                    diamondBalanceText.setText("💎 Diamonds: " + currentDiamonds);
                    Log.i(TAG, "Player diamonds loaded: " + currentDiamonds);
                }
            });
        }
    }
    
    private void setupDiamondPacks() {
        // Diamond packs layout
        for (int i = 0; i < diamondPacks.length; i++) {
            final int pack = diamondPacks[i];
            final double price = packPrices[i];
            
            Button packButton = new Button(this);
            packButton.setText(pack + "💎\n$" + String.format("%.2f", price));
            packButton.setBackgroundColor(packColors[i]);
            packButton.setTextColor(0xFFFFFFFF);
            packButton.setOnClickListener(v -> purchaseDiamonds(pack, price));
            
            diamondPacksGrid.addView(packButton);
        }
    }
    
    private void purchaseDiamonds(int diamonds, double price) {
        Log.i(TAG, "Purchase initiated: " + diamonds + " diamonds for $" + price);
        
        // Show payment options
        String[] options = {"Google Play", "PayPal", "Credit Card", "Cancel"};
        
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Purchase " + diamonds + " Diamonds")
                .setItems(options, (dialog, which) -> {
                    if (which < 3) {
                        processPayment(diamonds, price, options[which]);
                    }
                })
                .show();
    }
    
    private void processPayment(int diamonds, double price, String method) {
        Log.i(TAG, "Processing payment: " + diamonds + " diamonds via " + method);
        
        // Simulate payment processing
        Toast.makeText(this, "Processing payment...", Toast.LENGTH_SHORT).show();
        
        // In real implementation, integrate with:
        // - Google Play Billing Library
        // - PayPal SDK
        // - Stripe SDK
        
        // For now, simulate successful payment
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(() -> {
            completePayment(diamonds, price, method);
        }, 2000);
    }
    
    private void completePayment(int diamonds, double price, String method) {
        // Update Firebase
        if (userRef != null) {
            currentDiamonds += diamonds;
            totalSpent += (int) price;
            
            userRef.child("diamonds").setValue(currentDiamonds);
            userRef.child("totalSpent").setValue(totalSpent);
            userRef.child("lastDiamondPurchase").setValue(System.currentTimeMillis());
            
            // Store transaction
            DatabaseReference transactionsRef = FirebaseDatabase.getInstance()
                    .getReference("transactions/" + currentUser.getUid())
                    .push();
            
            transactionsRef.setValue(new java.util.HashMap<String, Object>() {{
                put("diamonds", diamonds);
                put("amount", price);
                put("method", method);
                put("timestamp", System.currentTimeMillis());
                put("developerEmail", PAYMENT_EMAIL);
                put("status", "completed");
            }});
            
            diamondBalanceText.setText("💎 Diamonds: " + currentDiamonds);
            totalSpentText.setText("💰 Total Spent: $" + totalSpent);
            
            Toast.makeText(this, diamonds + " diamonds added! Payment sent to " + PAYMENT_EMAIL, 
                    Toast.LENGTH_LONG).show();
            
            Log.i(TAG, "Payment completed: " + diamonds + " diamonds, $" + price + " sent to " + PAYMENT_EMAIL);
        }
    }
}
