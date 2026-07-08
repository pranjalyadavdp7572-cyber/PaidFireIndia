#include "GameLoop.h"
#include <iostream>

enum GameState {
    STATE_MENU = 0,
    STATE_LOBBY = 1,
    STATE_BR_GAME = 2,
    STATE_CS_GAME = 3,
    STATE_RESULT = 4
};

GameLoop::GameLoop() : currentState(STATE_MENU) {}

GameLoop::~GameLoop() {}

void GameLoop::Initialize() {
    std::cout << "[GameLoop] Initialized" << std::endl;
}

void GameLoop::Update(float deltaTime) {
    switch (currentState) {
        case STATE_MENU:
            // Handle menu logic
            break;
        case STATE_LOBBY:
            // Handle lobby logic
            break;
        case STATE_BR_GAME:
            // Update BR game
            break;
        case STATE_CS_GAME:
            // Update CS game
            break;
        case STATE_RESULT:
            // Show results
            break;
    }
}

void GameLoop::Render() {
    // Render based on current state
}

void GameLoop::SetGameState(int state) {
    currentState = state;
    std::cout << "[GameLoop] State changed to: " << state << std::endl;
}

void GameLoop::Shutdown() {}
