// Firebase initialization for Pranjal Free Fire
// This file should be used in the backend services

const firebase = require('firebase-admin');
const serviceAccount = require('./service-account-key.json');

firebase.initializeApp({
  credential: firebase.credential.cert(serviceAccount),
  databaseURL: 'https://pranjal-free-fire.firebaseio.com',
  projectId: 'pranjal-free-fire'
});

const db = firebase.database();
const auth = firebase.auth();

// User management functions
async function createUser(email, password, userData) {
  try {
    const userRecord = await auth.createUser({
      email: email,
      password: password,
      displayName: userData.username
    });
    
    // Store user data in database
    await db.ref(`players/${userRecord.uid}`).set({
      userId: userRecord.uid,
      email: email,
      username: userData.username,
      level: 1,
      xp: 0,
      brRanking: 0,
      csRanking: 0,
      totalWins: 0,
      totalKills: 0,
      createdAt: new Date().toISOString()
    });
    
    return userRecord.uid;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
}

// Update player ranking
async function updatePlayerRanking(userId, gameMode, rankingScore) {
  try {
    await db.ref(`players/${userId}`).update({
      [`${gameMode}Ranking`]: rankingScore,
      updatedAt: new Date().toISOString()
    });
  } catch (error) {
    console.error('Error updating ranking:', error);
    throw error;
  }
}

// Get leaderboard
async function getLeaderboard(gameMode, limit = 100) {
  try {
    const snapshot = await db.ref('leaderboard')
      .orderByChild('gameMode')
      .equalTo(gameMode)
      .limitToFirst(limit)
      .once('value');
    
    return snapshot.val();
  } catch (error) {
    console.error('Error fetching leaderboard:', error);
    throw error;
  }
}

// Store game result
async function storeGameResult(gameId, gameMode, players, winner) {
  try {
    await db.ref(`games/${gameId}`).set({
      gameId: gameId,
      gameMode: gameMode,
      players: players,
      winner: winner,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString()
    });
  } catch (error) {
    console.error('Error storing game result:', error);
    throw error;
  }
}

module.exports = {
  firebase,
  db,
  auth,
  createUser,
  updatePlayerRanking,
  getLeaderboard,
  storeGameResult
};
