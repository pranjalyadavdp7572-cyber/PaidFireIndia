# Pranjal Free Fire - Development Guide

## Getting Started

### System Requirements
- CMake 3.10 or higher
- C++ 17 compiler
- Android SDK 21+ (for Android)
- iOS 12+ SDK (for iOS)
- Firebase Admin SDK

### Building the Project

#### Android
```bash
mkdir build-android
cd build-android
cmake .. -DCMAKE_TOOLCHAIN_FILE=<android-ndk-path>/build/cmake/android.toolchain.cmake
make
```

#### iOS
```bash
mkdir build-ios
cd build-ios
cmake .. -DCMAKE_SYSTEM_NAME=iOS
make
```

## Game Modes

### Battle Royale (50v50)
- 50 players drop from the sky
- Shrinking zone system
- Full looting mechanics
- Last person/team standing wins

### CS Ranked (4v4)
- Team-based tactical gameplay
- Economic system (buy weapons with in-game money)
- Best of 16 rounds
- Skill-based ranking system

## Architecture

### Engine Core
The engine is built from scratch with these systems:
- **Graphics**: OpenGL ES 3.0 rendering pipeline
- **Physics**: 3D rigid body dynamics
- **Audio**: OpenAL audio system
- **Networking**: WebSocket + Firebase Realtime DB
- **Input**: Touch and gesture recognition

### Backend
- Firebase Realtime Database for player data
- Firebase Authentication
- Cloud Functions for game logic
- Leaderboard system

## Development Roadmap

### Phase 1: Engine Foundation ✅
- [x] Core engine structure
- [x] Graphics system design
- [x] Physics system design
- [x] Networking architecture

### Phase 2: Game Logic (In Progress)
- [ ] Battle Royale mode implementation
- [ ] CS Ranked mode implementation
- [ ] Weapon systems
- [ ] UI framework

### Phase 3: Firebase Integration
- [ ] Authentication system
- [ ] Player database schema
- [ ] Leaderboard system
- [ ] Game session management

### Phase 4: Mobile Development
- [ ] Android client
- [ ] iOS client
- [ ] Touch controls
- [ ] Platform optimization

### Phase 5: Testing & Launch
- [ ] Unit tests
- [ ] Integration tests
- [ ] Performance optimization
- [ ] Beta testing
- [ ] Official launch

## Contributing

When contributing to this project:
1. Create a feature branch
2. Make your changes
3. Test thoroughly
4. Submit a pull request

## Resources

- [GLM Mathematics Library](https://glm.g-truc.net/)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Android NDK Guide](https://developer.android.com/ndk)
- [OpenGL ES Documentation](https://www.khronos.org/opengles/)
