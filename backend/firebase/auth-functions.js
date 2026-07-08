// Firebase Cloud Functions for Authentication
// Developer: pranjalyadavdp9170@gmail.com

const functions = require('firebase-functions');
const admin = require('firebase-admin');

const DEVELOPER_EMAIL = 'pranjalyadavdp9170@gmail.com';

// Initialize Firebase
admin.initializeApp();

const db = admin.database();
const auth = admin.auth();

// Create user profile on sign-up (no OTP)
exports.createUserProfile = functions.auth.user().onCreate(async (user) => {
  try {
    console.log(`New user created: ${user.uid} - ${user.email}`);
    
    // Get provider info
    let provider = 'email';
    if (user.providerData.length > 0) {
      provider = user.providerData[0].providerId; // google.com, facebook.com, apple.com
    }
    
    // Create user profile without OTP
    const userProfile = {
      userId: user.uid,
      email: user.email || '',
      displayName: user.displayName || 'Player',
      photoURL: user.photoURL || '',
      provider: provider,
      level: 1,
      xp: 0,
      coins: 0,
      brRanking: 0,
      csRanking: 0,
      totalWins: 0,
      totalKills: 0,
      totalDeaths: 0,
      brStats: {
        games: 0,
        wins: 0,
        topTen: 0,
        kills: 0,
        deaths: 0
      },
      csStats: {
        games: 0,
        wins: 0,
        kills: 0,
        deaths: 0,
        assists: 0
      },
      createdAt: new Date().toISOString(),
      lastLoginAt: new Date().toISOString(),
      status: 'active',
      developerId: DEVELOPER_EMAIL
    };
    
    // Store in database
    await db.ref(`players/${user.uid}`).set(userProfile);
    
    // Create leaderboard entry
    await db.ref(`leaderboard/${user.uid}`).set({
      userId: user.uid,
      displayName: user.displayName || 'Player',
      brRanking: 0,
      csRanking: 0,
      totalWins: 0,
      totalKills: 0,
      updatedAt: new Date().toISOString()
    });
    
    console.log(`User profile created successfully for ${user.uid}`);
    return { success: true };
    
  } catch (error) {
    console.error('Error creating user profile:', error);
    return { error: error.message };
  }
});

// Update last login
exports.updateLastLogin = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'User not authenticated');
  }
  
  try {
    const userId = context.auth.uid;
    await db.ref(`players/${userId}`).update({
      lastLoginAt: new Date().toISOString()
    });
    
    console.log(`Last login updated for user: ${userId}`);
    return { success: true };
    
  } catch (error) {
    console.error('Error updating last login:', error);
    throw new functions.https.HttpsError('internal', error.message);
  }
});

// Get user profile
exports.getUserProfile = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'User not authenticated');
  }
  
  try {
    const userId = context.auth.uid;
    const snapshot = await db.ref(`players/${userId}`).once('value');
    const profile = snapshot.val();
    
    if (!profile) {
      throw new Error('User profile not found');
    }
    
    console.log(`Profile fetched for user: ${userId}`);
    return profile;
    
  } catch (error) {
    console.error('Error fetching user profile:', error);
    throw new functions.https.HttpsError('internal', error.message);
  }
});

// Update user stats
exports.updateUserStats = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'User not authenticated');
  }
  
  try {
    const userId = context.auth.uid;
    const { gameMode, stats } = data;
    
    if (gameMode === 'br') {
      await db.ref(`players/${userId}/brStats`).update(stats);
      await db.ref(`players/${userId}`).update({
        totalWins: admin.database.ServerValue.increment(stats.wins || 0),
        totalKills: admin.database.ServerValue.increment(stats.kills || 0),
        brRanking: admin.database.ServerValue.increment(100 * (stats.wins || 0))
      });
    } else if (gameMode === 'cs') {
      await db.ref(`players/${userId}/csStats`).update(stats);
      await db.ref(`players/${userId}`).update({
        totalKills: admin.database.ServerValue.increment(stats.kills || 0),
        csRanking: admin.database.ServerValue.increment(50 * (stats.wins || 0))
      });
    }
    
    console.log(`Stats updated for user: ${userId}`);
    return { success: true };
    
  } catch (error) {
    console.error('Error updating stats:', error);
    throw new functions.https.HttpsError('internal', error.message);
  }
});

// Get leaderboard
exports.getLeaderboard = functions.https.onCall(async (data, context) => {
  try {
    const { gameMode = 'br', limit = 100 } = data;
    
    const snapshot = await db.ref('leaderboard')
      .orderByChild(`${gameMode}Ranking`)
      .limitToLast(limit)
      .once('value');
    
    const leaderboard = [];
    snapshot.forEach(child => {
      leaderboard.unshift(child.val());
    });
    
    console.log(`Leaderboard fetched for ${gameMode} mode`);
    return leaderboard;
    
  } catch (error) {
    console.error('Error fetching leaderboard:', error);
    throw new functions.https.HttpsError('internal', error.message);
  }
});

// Delete user data (GDPR compliance)
exports.deleteUserData = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'User not authenticated');
  }
  
  try {
    const userId = context.auth.uid;
    
    // Delete user profile
    await db.ref(`players/${userId}`).remove();
    
    // Delete leaderboard entry
    await db.ref(`leaderboard/${userId}`).remove();
    
    // Delete user from auth
    await auth.deleteUser(userId);
    
    console.log(`User data deleted: ${userId}`);
    return { success: true };
    
  } catch (error) {
    console.error('Error deleting user data:', error);
    throw new functions.https.HttpsError('internal', error.message);
  }
});
