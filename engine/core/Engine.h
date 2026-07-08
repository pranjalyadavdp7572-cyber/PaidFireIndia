#ifndef PRANJAL_ENGINE_H
#define PRANJAL_ENGINE_H

#include <memory>
#include <vector>
#include <string>
#include <glm/glm.hpp>

namespace PranjalEngine {

class GraphicsSystem;
class PhysicsSystem;
class AudioSystem;
class NetworkingSystem;
class InputSystem;
class GameLoop;

class Engine {
public:
    Engine();
    ~Engine();
    
    // Engine lifecycle
    bool Initialize();
    void Update(float deltaTime);
    void Render();
    void Shutdown();
    
    // System getters
    GraphicsSystem* GetGraphicsSystem() const { return graphics.get(); }
    PhysicsSystem* GetPhysicsSystem() const { return physics.get(); }
    AudioSystem* GetAudioSystem() const { return audio.get(); }
    NetworkingSystem* GetNetworkingSystem() const { return networking.get(); }
    InputSystem* GetInputSystem() const { return input.get(); }
    
    // Configuration
    void SetScreenResolution(int width, int height);
    void SetTargetFPS(int fps);
    bool IsRunning() const { return isRunning; }
    
private:
    std::unique_ptr<GraphicsSystem> graphics;
    std::unique_ptr<PhysicsSystem> physics;
    std::unique_ptr<AudioSystem> audio;
    std::unique_ptr<NetworkingSystem> networking;
    std::unique_ptr<InputSystem> input;
    std::unique_ptr<GameLoop> gameLoop;
    
    bool isRunning;
    int screenWidth;
    int screenHeight;
    int targetFPS;
};

} // namespace PranjalEngine

#endif // PRANJAL_ENGINE_H