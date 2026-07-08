cmake_minimum_required(VERSION 3.10)
project(PranjalFireIndia)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Engine source files
set(ENGINE_SOURCES
    src/engine/core/Engine.cpp
    src/engine/core/GameLoop.cpp
    src/engine/graphics/GraphicsSystem.cpp
    src/engine/graphics/Renderer.cpp
    src/engine/physics/PhysicsSystem.cpp
    src/engine/audio/AudioSystem.cpp
    src/engine/input/InputSystem.cpp
    src/engine/network/NetworkManager.cpp
    src/engine/network/ServerConnection.cpp
)

# Game mode sources
set(GAME_SOURCES
    src/game/modes/BattleRoyale.cpp
    src/game/modes/CSRanked.cpp
    src/game/world/Map.cpp
    src/game/world/Zone.cpp
    src/game/player/Player.cpp
    src/game/player/Inventory.cpp
    src/game/weapons/Weapon.cpp
    src/game/weapons/WeaponSystem.cpp
    src/game/ui/GameUI.cpp
    src/game/ui/Minimap.cpp
)

# Include directories
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/src)
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/src/engine)
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/src/game)

# Android specific
if(ANDROID)
    add_library(pranjal-fire-engine SHARED ${ENGINE_SOURCES} ${GAME_SOURCES})
    find_library(GLESv3 GLESv3)
    find_library(log log)
    target_link_libraries(pranjal-fire-engine ${GLESv3} ${log})
else()
    add_executable(pranjal-fire-engine ${ENGINE_SOURCES} ${GAME_SOURCES})
endif()

# Compiler flags
if(MSVC)
    target_compile_options(pranjal-fire-engine PRIVATE /W4)
else()
    target_compile_options(pranjal-fire-engine PRIVATE -Wall -Wextra -Wpedantic)
endif()
