package com.pranjalfreefireindia;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Random;

public class GameplayActivity extends AppCompatActivity {
    
    private static final String TAG = "Gameplay";
    private static final String DEVELOPER_EMAIL = "pranjalyadavdp9170@gmail.com";
    
    private TextView healthText, ammoText, diamondText, speedText, scoreText, killsText;
    private ProgressBar healthBar, ammoBar;
    private Button shootButton, grenadeButton, healButton, speedBoostButton, lootButton;
    
    private int playerHealth = 100;
    private int playerAmmo = 30;
    private int playerDiamonds = 0;
    private int playerSpeed = 1; // 1x speed
    private int playerScore = 0;
    private int playerKills = 0;
    private boolean isSpeedBoosted = false;
    
    private FirebaseUser currentUser;
    private DatabaseReference userRef;
    
    private Random random = new Random();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        
        Log.i(TAG, "Gameplay started - Developer: " + DEVELOPER_EMAIL);
        
        // Initialize Firebase
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userRef = FirebaseDatabase.getInstance().getReference("players/" + currentUser.getUid());
            loadPlayerStats();
        }
        
        // Initialize views
        initializeViews();
        
        // Setup buttons
        setupButtons();
        
        // Start game loop
        startGameLoop();
    }
    
    private void initializeViews() {
        healthText = findViewById(R.id.health_text);
        ammoText = findViewById(R.id.ammo_text);
        diamondText = findViewById(R.id.diamond_text);
        speedText = findViewById(R.id.speed_text);
        scoreText = findViewById(R.id.score_text);
        killsText = findViewById(R.id.kills_text);
        
        healthBar = findViewById(R.id.health_bar);
        ammoBar = findViewById(R.id.ammo_bar);
        
        shootButton = findViewById(R.id.shoot_button);
        grenadeButton = findViewById(R.id.grenade_button);
        healButton = findViewById(R.id.heal_button);
        speedBoostButton = findViewById(R.id.speed_boost_button);
        lootButton = findViewById(R.id.loot_button);
        
        updateUI();
    }
    
    private void loadPlayerStats() {
        if (userRef != null) {
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Object diamonds = task.getResult().child("diamonds").getValue();
                    playerDiamonds = diamonds != null ? ((Number) diamonds).intValue() : 0;
                    Log.i(TAG, "Player stats loaded - Diamonds: " + playerDiamonds);
                }
            });
        }
    }
    
    private void setupButtons() {
        // Shoot button
        shootButton.setOnClickListener(v -> shootWeapon());
        
        // Grenade button
        grenadeButton.setOnClickListener(v -> throwGrenade());
        
        // Heal button
        healButton.setOnClickListener(v -> useHealthKit());
        
        // Speed Boost button
        speedBoostButton.setOnClickListener(v -> activateSpeedBoost());
        
        // Loot button (gifts/drops)
        lootButton.setOnClickListener(v -> openLootDrop());
    }
    
    private void shootWeapon() {
        if (playerAmmo > 0) {
            playerAmmo--;
            playerScore += 10;
            
            // Simulate hit
            if (random.nextDouble() > 0.3) { // 70% hit rate
                playerKills++;
                playerDiamonds += random.nextInt(5) + 1; // 1-5 diamonds per kill
                Toast.makeText(this, "💥 Kill! +" + (random.nextInt(5) + 1) + "💎", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Missed!", Toast.LENGTH_SHORT).show();
            }
            
            updateUI();
            Log.i(TAG, "Weapon fired - Ammo: " + playerAmmo);
        } else {
            Toast.makeText(this, "No ammo!", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void throwGrenade() {
        playerScore += 25;
        int diamondsGained = random.nextInt(8) + 2; // 2-10 diamonds
        playerDiamonds += diamondsGained;
        Toast.makeText(this, "💣 Grenade! +" + diamondsGained + "💎", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Grenade thrown - Diamonds gained: " + diamondsGained);
        updateUI();
    }
    
    private void useHealthKit() {
        if (playerHealth < 100) {
            playerHealth = Math.min(100, playerHealth + 50);
            playerScore += 5;
            Toast.makeText(this, "🏥 Healed!", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Health used - Health: " + playerHealth);
            updateUI();
        }
    }
    
    private void activateSpeedBoost() {
        if (!isSpeedBoosted) {
            isSpeedBoosted = true;
            playerSpeed = 2; // 2x speed
            playerScore += 20;
            Toast.makeText(this, "⚡ Speed Boost Activated! 2x Speed", Toast.LENGTH_SHORT).show();
            
            // Deactivate after 10 seconds
            new android.os.Handler().postDelayed(() -> {
                isSpeedBoosted = false;
                playerSpeed = 1;
                updateUI();
            }, 10000);
            
            Log.i(TAG, "Speed boost activated");
            updateUI();
        }
    }
    
    private void openLootDrop() {
        // Simulate looting (like Free Fire)
        int[] lootRewards = {2, 4, 5, 8, 10, 15, 20, 25, 50, 100};
        int reward = lootRewards[random.nextInt(lootRewards.length)];
        
        playerDiamonds += reward;
        playerScore += 15;
        
        Toast.makeText(this, "🎁 Loot Drop! +" + reward + "💎", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Loot opened - Diamonds gained: " + reward);
        updateUI();
    }
    
    private void updateUI() {
        healthText.setText("❤️ Health: " + playerHealth + "/100");
        ammoText.setText("🔫 Ammo: " + playerAmmo);
        diamondText.setText("💎 Diamonds: " + playerDiamonds);
        speedText.setText("⚡ Speed: " + playerSpeed + "x" + (isSpeedBoosted ? " ACTIVE" : ""));
        scoreText.setText("📊 Score: " + playerScore);
        killsText.setText("💀 Kills: " + playerKills);
        
        healthBar.setProgress(playerHealth);
        ammoBar.setProgress((playerAmmo * 100) / 30);
    }
    
    private void startGameLoop() {
        // Simulate game events
        new Thread(() -> {
            while (isResumed()) {
                try {
                    Thread.sleep(1000);
                    
                    // Random events
                    if (random.nextDouble() < 0.1) { // 10% chance per second
                        int damage = random.nextInt(10) + 5;
                        playerHealth -= damage;
                        
                        runOnUiThread(() -> {
                            Toast.makeText(GameplayActivity.this, "💔 Damaged! -" + damage + "❤️", Toast.LENGTH_SHORT).show();
                            updateUI();
                        });
                    }
                    
                    if (playerHealth <= 0) {
                        runOnUiThread(this::gameOver);
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    private void gameOver() {
        // Save stats to Firebase
        if (userRef != null) {
            userRef.child("diamonds").setValue(playerDiamonds);
            userRef.child("totalScore").setValue(playerScore);
            userRef.child("totalKills").setValue(playerKills);
        }
        
        Toast.makeText(this, "Game Over! Score: " + playerScore + " Diamonds: " + playerDiamonds, 
                Toast.LENGTH_LONG).show();
        
        Log.i(TAG, "Game Over - Final Stats - Score: " + playerScore + ", Kills: " + playerKills + ", Diamonds: " + playerDiamonds);
        
        finish();
    }
    
    private boolean isResumed() {
        return !isDestroyed();
    }
}
