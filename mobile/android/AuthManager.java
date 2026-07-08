package com.pranjalfreefireindia;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import java.util.Arrays;

public class AuthManager {
    private static final String TAG = "AuthManager";
    private static final String DEVELOPER_EMAIL = "pranjalyadavdp9170@gmail.com";
    
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    private AuthCallback authCallback;
    private Context context;
    
    public interface AuthCallback {
        void onAuthSuccess(FirebaseUser user);
        void onAuthFailure(String error);
    }
    
    public AuthManager(Context context, AuthCallback callback) {
        this.context = context;
        this.authCallback = callback;
        this.mAuth = FirebaseAuth.getInstance();
        this.mCallbackManager = CallbackManager.Factory.create();
        setupGoogleSignIn();
        setupFacebookSignIn();
    }
    
    // Google Sign-In Setup
    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        Log.i(TAG, "Google Sign-In configured");
    }
    
    // Facebook Sign-In Setup
    private void setupFacebookSignIn() {
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "Facebook login success");
                        handleFacebookAccessToken(loginResult.getAccessToken().getToken());
                    }
                    
                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Facebook login cancelled");
                        if (authCallback != null) {
                            authCallback.onAuthFailure("Facebook login cancelled");
                        }
                    }
                    
                    @Override
                    public void onError(FacebookException error) {
                        Log.e(TAG, "Facebook login error: " + error.getMessage());
                        if (authCallback != null) {
                            authCallback.onAuthFailure("Facebook login failed: " + error.getMessage());
                        }
                    }
                });
    }
    
    // Google Login
    public void signInWithGoogle(Activity activity) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, 100);
    }
    
    // Google Login Result Handler
    public void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(com.google.android.gms.common.api.ApiException.class);
            String idToken = account.getIdToken();
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i(TAG, "Google sign-in successful: " + user.getEmail());
                            if (authCallback != null) {
                                authCallback.onAuthSuccess(user);
                            }
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (authCallback != null) {
                                authCallback.onAuthFailure("Authentication failed");
                            }
                        }
                    });
        } catch (com.google.android.gms.common.api.ApiException e) {
            Log.w(TAG, "signInWithGoogle:failed", e);
            if (authCallback != null) {
                authCallback.onAuthFailure("Google sign-in failed: " + e.getMessage());
            }
        }
    }
    
    // Facebook Login
    public void signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(
                (Activity) context,
                Arrays.asList("public_profile", "email")
        );
    }
    
    // Facebook Access Token Handler
    private void handleFacebookAccessToken(String accessToken) {
        com.facebook.AccessToken token = new com.facebook.AccessToken(
                accessToken,
                "com.pranjalfreefireindia",
                com.facebook.AccessToken.getCurrentAccessToken().getUserId(),
                Arrays.asList("public_profile", "email"),
                null,
                null,
                null,
                null
        );
        
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.i(TAG, "Facebook sign-in successful: " + user.getEmail());
                        if (authCallback != null) {
                            authCallback.onAuthSuccess(user);
                        }
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (authCallback != null) {
                            authCallback.onAuthFailure("Facebook authentication failed");
                        }
                    }
                });
    }
    
    // Apple Sign-In (iOS style)
    public void signInWithApple() {
        // Apple Sign-In implementation for Android
        Log.i(TAG, "Apple Sign-In initiated");
        // Apple OAuth handling through Firebase
    }
    
    // Sign Out
    public void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
        LoginManager.getInstance().logOut();
        Log.i(TAG, "User signed out");
    }
    
    // Get Current User
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
    
    // Check if User is Logged In
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }
    
    // Get Callback Manager (for Fragment callback)
    public CallbackManager getCallbackManager() {
        return mCallbackManager;
    }
}
