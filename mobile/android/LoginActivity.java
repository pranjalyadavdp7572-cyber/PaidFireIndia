package com.pranjalfreefireindia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements AuthManager.AuthCallback {
    
    private static final String TAG = "LoginActivity";
    private static final String DEVELOPER_EMAIL = "pranjalyadavdp9170@gmail.com";
    
    private AuthManager authManager;
    private Button googleLoginBtn, facebookLoginBtn, appleLoginBtn, guestLoginBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        Log.i(TAG, "Login Activity created - Developer: " + DEVELOPER_EMAIL);
        
        // Initialize AuthManager
        authManager = new AuthManager(this, this);
        
        // Check if already logged in
        if (authManager.isUserLoggedIn()) {
            startGameActivity();
            return;
        }
        
        // Initialize buttons
        googleLoginBtn = findViewById(R.id.btn_google_login);
        facebookLoginBtn = findViewById(R.id.btn_facebook_login);
        appleLoginBtn = findViewById(R.id.btn_apple_login);
        guestLoginBtn = findViewById(R.id.btn_guest_login);
        
        // Set click listeners
        googleLoginBtn.setOnClickListener(v -> authManager.signInWithGoogle(LoginActivity.this));
        facebookLoginBtn.setOnClickListener(v -> authManager.signInWithFacebook());
        appleLoginBtn.setOnClickListener(v -> authManager.signInWithApple());
        guestLoginBtn.setOnClickListener(v -> startGameActivity());
        
        Log.i(TAG, "Login buttons configured");
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // Handle Google Sign-In result
        if (requestCode == 100) {
            com.google.android.gms.tasks.Task<com.google.android.gms.auth.api.signin.GoogleSignInAccount> task = 
                    com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            authManager.handleGoogleSignInResult(task);
        }
        
        // Handle Facebook result
        authManager.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public void onAuthSuccess(FirebaseUser user) {
        Log.i(TAG, "Authentication successful for user: " + user.getEmail());
        Toast.makeText(LoginActivity.this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        startGameActivity();
    }
    
    @Override
    public void onAuthFailure(String error) {
        Log.e(TAG, "Authentication failed: " + error);
        Toast.makeText(LoginActivity.this, "Login failed: " + error, Toast.LENGTH_LONG).show();
    }
    
    private void startGameActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
