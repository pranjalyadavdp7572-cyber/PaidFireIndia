# OAuth Setup Guide - Pranjal Free Fire

**Developer Email**: pranjalyadavdp9170@gmail.com

## Overview

This guide covers setting up OAuth for Google, Facebook, and Apple sign-in without OTP.

## Google Sign-In Setup

### 1. Create Google OAuth Credentials
- Go to [Google Cloud Console](https://console.cloud.google.com)
- Create a new project or select existing
- Enable Google+ API
- Go to Credentials → Create OAuth 2.0 Client ID
- Choose "Android" and add your app SHA-1 fingerprint

### 2. Get SHA-1 Fingerprint
```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

### 3. Add to Android Project
- Copy `google-services.json` to `mobile/android/app/`
- Add dependency to `build.gradle`:
```gradle
implementation 'com.google.android.gms:play-services-auth:20.5.0'
implementation 'com.google.firebase:firebase-auth:22.0.0'
```

## Facebook Sign-In Setup

### 1. Create Facebook App
- Go to [Facebook Developers](https://developers.facebook.com)
- Create new app
- Select "Consumer" as app type
- Add Android platform

### 2. Configure Android Settings
- Add package name: `com.pranjalfreefireindia`
- Add SHA-1 hash and SHA-256 hash of your signing key
- Set App Domain

### 3. Add to Project
- Add dependency:
```gradle
implementation 'com.facebook.android:facebook-android-sdk:[latest]'
```

- Add to strings.xml:
```xml
<string name="facebook_app_id">YOUR_FACEBOOK_APP_ID</string>
<string name="facebook_app_secret">YOUR_FACEBOOK_APP_SECRET</string>
```

## Apple Sign-In Setup

### 1. Create Apple OAuth Credentials
- Go to Apple Developer Account
- Create new App ID
- Enable Sign in with Apple capability
- Create Services ID

### 2. Configure Redirect URLs
- Set redirect URI to your Firebase domain
- Example: `https://pranjal-free-fire.firebaseapp.com/__/auth/handler`

### 3. Add to iOS Project
- Import Sign in with Apple framework
- Configure credentials in Firebase

## Firebase Configuration

### Enable OAuth Providers in Firebase Console
1. Go to Firebase Console
2. Select "Authentication"
3. Enable:
   - Google
   - Facebook
   - Apple
4. Add OAuth app credentials

### Database Rules
```json
{
  "rules": {
    "players": {
      "$uid": {
        ".read": "auth.uid === $uid",
        ".write": "auth.uid === $uid"
      }
    }
  }
}
```

## No OTP Implementation

### User Profile Auto-Creation
When user signs in via OAuth:
1. Firebase automatically authenticates
2. User profile created in Realtime Database
3. No email verification needed
4. No OTP sent
5. User directly enters game

### Code Implementation
```java
// Auth automatically handles everything
AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
mAuth.signInWithCredential(credential)
    .addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            // User logged in without OTP
            startGameActivity();
        }
    });
```

## Testing

### Test OAuth Credentials
```bash
# Test Google Sign-In
sha1sum path/to/keystore

# Test Facebook Sign-In
hash_key=$(echo -e $'\x00'$(xxd -p ~/.android/debug.keystore | tr -d '\n' | sed 's/./&\n/2g' | sed -n '1~2p' | awk -F. '{print $1}') | base64)

# Test Apple Sign-In
# Use Xcode to test
```

## Troubleshooting

### Common Issues

1. **Google Sign-In fails with wrong SHA-1**
   - Verify SHA-1 matches in Google Console
   - Use debug keystore SHA-1 for testing

2. **Facebook login returns error**
   - Check Facebook app ID in strings.xml
   - Verify package name matches Facebook app

3. **Apple Sign-In not working**
   - Ensure Sign in with Apple is enabled
   - Check redirect URLs are correct

## Support

For issues: **pranjalyadavdp9170@gmail.com**
