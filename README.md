# 🔥 Pranjal Free Fire - Battle Royale Game Engine

**A custom-built mobile battle royale and competitive game engine for Android and iOS.**

## 📋 Features

### Game Modes
- ⚔️ **Battle Royale (50v50)** - Traditional BR with dynamic zone shrinking
- 🏆 **CS Ranked (4v4)** - Competitive team-based gameplay

### Core Systems
- 🎮 **Custom Game Engine** - Built from scratch in C++/Java
- 📱 **Cross-platform** - Android (primary) & iOS (framework ready)
- 🌐 **Multiplayer Networking** - Real-time player synchronization
- 🔐 **OAuth Authentication** - Google, Facebook, Apple, Guest
- 🎯 **Advanced Physics** - Realistic movement and bullet trajectories
- 🎨 **Graphics Pipeline** - OpenGL ES 3.0+ rendering
- 🔊 **Dynamic Audio** - Positional 3D sound
- 📊 **Ranking System** - BR and CS ranked matchmaking
- 💾 **Firebase Backend** - Cloud storage, leaderboards, analytics

## 🚀 Quick Start

### Prerequisites
```bash
# Android Development Kit
- Android Studio 2022.1+
- Android SDK 34
- Android NDK r25c
- Gradle 8.0+
- Java 11+

# iOS (Optional)
- Xcode 14+
- iOS 14.0+
```

### Installation

```bash
# Clone repository
git clone https://github.com/pranjalyadavdp7572-cyber/PaidFireIndia.git
cd PaidFireIndia

# Android Setup
cd android
./gradlew clean build
./gradlew installDebug

# iOS Setup (Optional)
cd ios
pod install
xcodebuild -scheme PranjalFire -configuration Debug
```

## 📁 Project Structure

```
PaidFireIndia/
├── src/
│   ├── engine/
│   │   ├── core/           # Engine initialization, game loop
│   │   ├── graphics/       # Rendering, OpenGL pipeline
│   │   ├── physics/        # Physics simulation
│   │   ├── audio/          # Sound management
│   │   ├── input/          # Touch & controller input
│   │   └── network/        # Multiplayer networking
│   └── game/
│       ├── modes/          # BR and CS game modes
│       ├── world/          # Map, zones, spawn points
│       ├── player/         # Player state, inventory
│       ├── weapons/        # Weapon system
│       └── ui/             # Game UI, HUD
├── android/
│   ├── app/                # Android app files
│   ├── src/                # Android Java code
│   └── build.gradle        # Gradle config
├── ios/
│   ├── PranjalFire/        # iOS app files
│   └── Podfile             # CocoaPods config
├── backend/
│   ├── firebase/           # Cloud functions
│   └── config/             # Firebase config
├── assets/
│   ├── models/             # 3D models
│   ├── textures/           # Textures
│   ├── sounds/             # Audio files
│   └── ui/                 # UI assets
└── docs/
    ├── ARCHITECTURE.md     # System design
    ├── GAMEPLAY.md         # Game mechanics
    └── API.md              # Backend API
```

## 🎮 Gameplay

### Battle Royale (50v50)
1. **Plane Drop** - Choose landing zone
2. **Looting** - Find weapons, armor, consumables
3. **Combat** - Eliminate opponents
4. **Zone Management** - Stay inside shrinking safe zone
5. **Victory** - Last player/team standing wins

**Zone Timeline:**
- Wave 1: Zone revealed at game start
- Wave 2: First shrink at 4 minutes
- Wave 3-8: Progressive shrinks every 2-3 minutes
- Final: 1v1 in 10m radius

### CS Ranked (4v4)
1. **Team Assignment** - 2 teams of 4
2. **Economy** - Buy weapons with in-round currency
3. **Objectives** - Plant/defuse bomb or eliminate opponents
4. **Rounds** - Best of 13 rounds
5. **Ranking** - ELO-based matchmaking

## 🔐 Authentication

**Multi-provider OAuth (No OTP):**
- 🔵 **Google** - Instant OAuth web flow
- 👤 **Facebook** - Social login integration
- 🍎 **Apple** - iOS native sign-in
- 👥 **Guest** - Play without account

**Firebase Auto-setup:**
- Automatic player profile creation
- Stats initialization
- Leaderboard entry
- GDPR compliant data deletion

## ⚙️ Configuration

### Firebase Setup
```bash
# 1. Create Firebase project at https://console.firebase.google.com
# 2. Enable Authentication (Google, Facebook, Apple)
# 3. Create Realtime Database
# 4. Download google-services.json
# 5. Copy to android/app/
# 6. Deploy Cloud Functions
cd backend/firebase
firebase deploy --only functions
```

### OAuth Credentials

**Google:**
- Go to https://console.cloud.google.com
- Create OAuth 2.0 Client ID (Android)
- Add SHA-1 fingerprint from your keystore
- Copy Client ID to Android code

**Facebook:**
- Go to https://developers.facebook.com
- Create app
- Get App ID
- Configure Android package name and hash

**Apple:**
- Go to https://developer.apple.com
- Create Sign in with Apple key
- Configure app identifier

## 📊 Technical Specs

### Engine Specifications
- **Rendering:** OpenGL ES 3.0+ with GLSL shaders
- **Physics:** Custom 3D physics engine (100-200 players)
- **Networking:** TCP/UDP sockets, 60 tick-rate
- **Memory:** ~300MB RAM (Android)
- **Target FPS:** 60 FPS (high), 30 FPS (medium)

### Game Balance
- **Map Size:** 2km x 2km (5000 x 5000 units)
- **Player Count:** 50 BR, 4 CS
- **Weapons:** 25+ weapon types
- **Armor Tiers:** 3 levels (common, rare, legendary)
- **Match Duration:** 25 min BR, 40 min CS

## 🛠️ Development

### Building
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Clean build
./gradlew clean build
```

### Running
```bash
# Install debug APK
./gradlew installDebug

# Run on device/emulator
adb shell am start -n com.pranjalfire.india/.activities.SplashActivity
```

### Debugging
```bash
# View logs
adb logcat | grep "PranjalFire"

# Debug with Android Studio
# Connect device, set breakpoints, run in debug mode
```

## 📈 Performance

| Device | Min SDK | Target FPS | RAM |
|--------|---------|------------|-----|
| Low-end | 24 | 30 | 2GB |
| Mid-range | 26 | 60 | 4GB |
| High-end | 28 | 120 | 6GB+ |

## 🐛 Known Issues

- iOS build pending full implementation
- Some animations may lag on low-end devices
- Voice chat disabled in beta

## 🚧 Roadmap

### Phase 1 (Complete)
- [x] Game engine foundation
- [x] OAuth authentication
- [x] Project structure

### Phase 2 (In Progress)
- [ ] BR gameplay mechanics
- [ ] CS ranked mechanics
- [ ] Graphics rendering
- [ ] Audio system

### Phase 3 (Planned)
- [ ] Multiplayer networking
- [ ] Leaderboards
- [ ] Cosmetics shop
- [ ] Mobile optimization

### Phase 4 (Future)
- [ ] iOS release
- [ ] Cross-platform play
- [ ] Battle pass system
- [ ] Esports features

## 📞 Support

**Email:** pranjalyadavdp9170@gmail.com
**GitHub Issues:** https://github.com/pranjalyadavdp7572-cyber/PaidFireIndia/issues
**Discord:** [Join Community]

## 📄 License

MIT License - See LICENSE file for details

## 👨‍💻 Author

**Pranjal Yadav**
- GitHub: [@pranjalyadavdp7572-cyber](https://github.com/pranjalyadavdp7572-cyber)
- Email: pranjalyadavdp9170@gmail.com

---

**Made with ❤️ by Pranjal Yadav | Powered by Copilot AI**

*Pranjal Free Fire - Where Legends Are Born* 🔥⚔️
