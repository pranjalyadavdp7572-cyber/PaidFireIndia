#ifndef PRANJAL_ENGINE_H
#define PRANJAL_ENGINE_H

#include <vector>
#include <memory>
#include <chrono>

class GameLoop;
class GraphicsSystem;
class PhysicsSystem;
class AudioSystem;
class InputSystem;
class NetworkManager;

class Engine {
public:
    Engine();
    ~Engine();
    
    void Initialize();
    void Run();
    void Shutdown();
    void Update(float deltaTime);
    void Render();
    
    // System getters
    GraphicsSystem* GetGraphics() const { return graphics.get(); }
    PhysicsSystem* GetPhysics() const { return physics.get(); }
    AudioSystem* GetAudio() const { return audio.get(); }
    InputSystem* GetInput() const { return input.get(); }
    NetworkManager* GetNetwork() const { return network.get(); }
    
    bool IsRunning() const { return isRunning; }
    float GetFPS() const { return currentFPS; }
    
private:
    std::unique_ptr<GameLoop> gameLoop;
    std::unique_ptr<GraphicsSystem> graphics;
    std::unique_ptr<PhysicsSystem> physics;
    std::unique_ptr<AudioSystem> audio;
    std::unique_ptr<InputSystem> input;
    std::unique_ptr<NetworkManager> network;
    
    bool isRunning;
    float currentFPS;
    std::chrono::high_resolution_clock::time_point lastFrameTime;
};

#endif // PRANJAL_ENGINE_H
