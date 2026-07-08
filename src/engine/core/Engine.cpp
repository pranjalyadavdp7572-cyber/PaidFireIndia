#include "Engine.h"
#include "GameLoop.h"
#include "../graphics/GraphicsSystem.h"
#include "../physics/PhysicsSystem.h"
#include "../audio/AudioSystem.h"
#include "../input/InputSystem.h"
#include "../network/NetworkManager.h"
#include <iostream>

Engine::Engine() : isRunning(false), currentFPS(0.0f) {}

Engine::~Engine() {
    Shutdown();
}

void Engine::Initialize() {
    std::cout << "[Engine] Initializing Pranjal Fire Engine..." << std::endl;
    
    // Initialize all systems
    graphics = std::make_unique<GraphicsSystem>();
    graphics->Initialize();
    
    physics = std::make_unique<PhysicsSystem>();
    physics->Initialize();
    
    audio = std::make_unique<AudioSystem>();
    audio->Initialize();
    
    input = std::make_unique<InputSystem>();
    input->Initialize();
    
    network = std::make_unique<NetworkManager>();
    network->Initialize();
    
    gameLoop = std::make_unique<GameLoop>();
    gameLoop->Initialize();
    
    isRunning = true;
    lastFrameTime = std::chrono::high_resolution_clock::now();
    
    std::cout << "[Engine] Initialization complete!" << std::endl;
}

void Engine::Run() {
    while (isRunning) {
        auto currentTime = std::chrono::high_resolution_clock::now();
        float deltaTime = std::chrono::duration<float>(
            currentTime - lastFrameTime
        ).count();
        lastFrameTime = currentTime;
        
        // Cap deltaTime to 0.016f (60 FPS)
        if (deltaTime > 0.033f) deltaTime = 0.033f;
        
        Update(deltaTime);
        Render();
        
        // Calculate FPS
        currentFPS = 1.0f / deltaTime;
    }
}

void Engine::Update(float deltaTime) {
    input->Update();
    physics->Update(deltaTime);
    gameLoop->Update(deltaTime);
    network->Update(deltaTime);
}

void Engine::Render() {
    graphics->BeginFrame();
    gameLoop->Render();
    graphics->EndFrame();
}

void Engine::Shutdown() {
    std::cout << "[Engine] Shutting down..." << std::endl;
    isRunning = false;
    
    if (gameLoop) gameLoop.reset();
    if (network) network->Shutdown();
    if (audio) audio->Shutdown();
    if (input) input.reset();
    if (physics) physics.reset();
    if (graphics) graphics->Shutdown();
    
    std::cout << "[Engine] Shutdown complete!" << std::endl;
}
