cmake_minimum_required(VERSION 3.10)
project(PranjalFreeFire)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Engine core
add_library(pranjal_engine STATIC
    engine/core/Engine.cpp
    engine/graphics/GraphicsSystem.cpp
    engine/physics/PhysicsSystem.cpp
    engine/audio/AudioSystem.cpp
    engine/networking/NetworkingSystem.cpp
    engine/input/InputSystem.cpp
)

# Game modes
add_library(game_modes STATIC
    game/modes/BattleRoyale.cpp
    game/modes/CSRanked.cpp
)

# Game library
add_library(pranjal_game SHARED
    game/GameMain.cpp
)

target_link_libraries(pranjal_game 
    pranjal_engine
    game_modes
)

# Platform-specific
if(ANDROID)
    add_library(pranjal_engine_android SHARED
        mobile/android/PranjalGameEngine.cpp
    )
    target_link_libraries(pranjal_engine_android
        pranjal_game
        log
        android
        EGL
        GLESv3
    )
elif(APPLE)
    add_library(pranjal_engine_ios SHARED
        mobile/ios/PranjalGameEngine.mm
    )
endif()
