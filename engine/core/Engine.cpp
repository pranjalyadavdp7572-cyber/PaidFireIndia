#include "Engine.h"
#include "../graphics/GraphicsSystem.h"
#include "../physics/PhysicsSystem.h"
#include "../audio/AudioSystem.h"
#include "../networking/NetworkingSystem.h"
#include "../input/InputSystem.h"
#include "GameLoop.h"

namespace PranjalEngine {

Engine::Engine()
    : isRunning(false)
    , screenWidth(1080)
    , screenHeight(1920)
    , targetFPS(60)
{
}

Engine::~Engine()
{
    Shutdown();
}

bool Engine::Initialize()
{
    // Initialize graphics system
    graphics = std::make_unique<GraphicsSystem>();
    if (!graphics->Initialize(screenWidth, screenHeight)) {
        return false;
    }
    
    // Initialize physics system
    physics = std::make_unique<PhysicsSystem>();
    if (!physics->Initialize()) {
        return false;
    }
    
    // Initialize audio system
    audio = std::make_unique<AudioSystem>();
    if (!audio->Initialize()) {
        return false;
    }
    
    // Initialize networking system
    networking = std::make_unique<NetworkingSystem>();
    if (!networking->Initialize()) {
        return false;
    }
    
    // Initialize input system
    input = std::make_unique<InputSystem>();
    if (!input->Initialize()) {
        return false;
    }
    
    // Initialize game loop
    gameLoop = std::make_unique<GameLoop>(this);
    
    isRunning = true;
    return true;
}

void Engine::Update(float deltaTime)
{
    if (!isRunning) return;
    
    // Update input
    input->Update();
    
    // Update physics
    physics->Update(deltaTime);
    
    // Update game logic (handled by GameLoop)
    gameLoop->Update(deltaTime);
    
    // Update audio
    audio->Update();
}

void Engine::Render()
{
    if (!isRunning) return;
    
    graphics->BeginFrame();
    gameLoop->Render();
    graphics->EndFrame();
}

void Engine::Shutdown()
{
    isRunning = false;
    gameLoop.reset();
    input.reset();
    networking.reset();
    audio.reset();
    physics.reset();
    graphics.reset();
}

void Engine::SetScreenResolution(int width, int height)
{
    screenWidth = width;
    screenHeight = height;
    if (graphics) {
        graphics->SetResolution(width, height);
    }
}

void Engine::SetTargetFPS(int fps)
{
    targetFPS = fps;
}

} // namespace PranjalEngine