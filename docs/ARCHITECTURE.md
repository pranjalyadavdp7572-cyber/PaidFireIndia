# Pranjal Free Fire - Architecture Documentation

**Developer Email**: pranjalyadavdp9170@gmail.com

## System Architecture

### High-Level Overview
```
┌─────────────────────────────────────────────────┐
│         Mobile Client Layer                     │
│  ┌───────────────────────────────────────────┐  │
│  │  Android App   │   iOS App                 │  │
│  │  - Game Engine │   - Game Engine           │  │
│  │  - UI Framework│   - UI Framework          │  │
│  │  - Input System│   - Input System          │  │
│  └───────────────────────────────────────────┘  │
└────────────────────┬────────────────────────────┘
                     │
        ┌────────────┴────────────┐
        │                         │
   ┌────▼───────────┐    ┌───────▼────────┐
   │ Firebase Auth  │    │ Firebase DB    │
   │ - OAuth        │    │ - Player Data  │
   │ - No OTP       │    │ - Game Stats   │
   │ - Providers:   │    │ - Leaderboards │
   │  • Google      │    │ - Active Games │
   │  • Facebook    │    │                │
   │  • Apple       │    └────────────────┘
   └────────────────┘
        │
   ┌────▼──────────────────────┐
   │ Cloud Functions           │
   │ - User Profile Creation   │
   │ - Stats Management        │
   │ - Game Result Recording   │
   │ - Leaderboard Updates     │
   └───────────────────────────┘
```

## Authentication Flow

### Step 1: User Selects Login Provider
- User opens app
- Sees login screen with 4 options:
  - Google Sign-In
  - Facebook Sign-In
  - Apple Sign-In
  - Guest Mode

### Step 2: OAuth Authentication
- User clicks provider button
- Browser/WebView opens provider login
- User enters credentials
- Provider returns authentication token

### Step 3: Firebase Authentication
- App sends token to Firebase
- Firebase verifies token
- User session created
- **NO OTP SENT** ✓

### Step 4: Auto User Profile Creation
- Cloud Function triggers on new user
- Player profile automatically created
- Default stats initialized
- Leaderboard entry added
- User enters game immediately

## Game Architecture

### Engine Systems
```
Pranjal Engine
├── Core Engine
│   ├── Initialization
│   ├── Game Loop
│   └── Resource Management
├── Graphics System
│   ├── OpenGL ES 3.0
│   ├── Shader Management
│   ├── Texture Loading
│   └── Particle System
├── Physics System
│   ├── Rigid Bodies
│   ├── Collision Detection
│   ├── Raycasting
│   └── Force Application
├── Networking System
│   ├── WebSocket Connection
│   ├── Player Synchronization
│   ├── Game State Updates
│   └── Event Broadcasting
├── Audio System
│   ├── Background Music
│   ├── SFX Playback
│   ├── 3D Audio
│   └── Volume Control
└── Input System
    ├── Touch Detection
    ├── Gesture Recognition
    ├── Keyboard Input
    └── Controller Support
```

## Game Modes

### Battle Royale (50v50)
- **Players**: 50 per game
- **Map**: Large open world
- **Looting**: Full inventory system
- **Zone**: Dynamically shrinking
- **Duration**: 30-40 minutes
- **Win Condition**: Last player/team standing

### CS Ranked (4v4)
- **Players**: 8 per game (4v4)
- **Map**: Tactical arena
- **Rounds**: Best of 16
- **Duration**: 30-45 minutes
- **Economy**: Buy system
- **Win Condition**: Win majority of rounds

## Database Schema

### Players Collection
```json
{
  "userId": "string",
  "email": "string",
  "displayName": "string",
  "provider": "google|facebook|apple",
  "level": "number",
  "xp": "number",
  "brRanking": "number",
  "csRanking": "number",
  "totalWins": "number",
  "totalKills": "number",
  "brStats": {
    "games": "number",
    "wins": "number",
    "topTen": "number",
    "kills": "number",
    "deaths": "number"
  },
  "csStats": {
    "games": "number",
    "wins": "number",
    "kills": "number",
    "deaths": "number",
    "assists": "number"
  },
  "createdAt": "timestamp",
  "lastLoginAt": "timestamp"
}
```

### Active Games Collection
```json
{
  "gameId": "string",
  "gameMode": "br|cs",
  "players": ["userId1", "userId2"],
  "status": "waiting|active|finished",
  "startedAt": "timestamp",
  "createdAt": "timestamp"
}
```

### Leaderboard Collection
```json
{
  "userId": "string",
  "displayName": "string",
  "brRanking": "number",
  "csRanking": "number",
  "totalWins": "number",
  "totalKills": "number",
  "rank": "number",
  "updatedAt": "timestamp"
}
```

## Data Flow

### Game Session Flow
1. User signs in (no OTP)
2. Player profile loaded
3. User selects game mode
4. Matchmaking starts
5. Game instance created
6. Real-time networking established
7. Game loop runs
8. Game ends
9. Stats recorded
10. Leaderboard updated
11. User returns to lobby

## Security Measures

- **OAuth Security**: Provider handles password
- **Firebase Auth**: Secure token validation
- **Database Rules**: User can only read/write own data
- **HTTPS Only**: All connections encrypted
- **No OTP**: Reduces attack surface
- **GDPR Compliant**: User data deletion function

## Performance Considerations

- **Graphics**: 60 FPS target
- **Network**: <100ms latency preferred
- **Memory**: Optimized for low-end devices
- **Battery**: Power-efficient rendering
- **Storage**: Minimal local cache

## Development Roadmap

### Phase 1: ✅ Foundation
- Engine core
- OAuth integration
- Database schema
- Cloud functions

### Phase 2: 🔄 Gameplay
- BR mode mechanics
- CS mode mechanics
- Weapon systems
- UI implementation

### Phase 3: 🔲 Launch Preparation
- Testing & QA
- Performance optimization
- Beta testing
- App store submission

## Support

**Developer**: pranjalyadavdp9170@gmail.com
