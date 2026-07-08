#ifndef NETWORKING_SYSTEM_H
#define NETWORKING_SYSTEM_H

#include <string>
#include <memory>
#include <functional>
#include <vector>
#include <glm/glm.hpp>

namespace PranjalEngine {

struct PlayerData {
    std::string playerId;
    glm::vec3 position;
    float health;
    int weaponId;
    int ammo;
};

struct GameState {
    std::vector<PlayerData> players;
    glm::vec3 zoneCenter;
    float zoneRadius;
    int remainingPlayers;
};

class NetworkingSystem {
public:
    NetworkingSystem();
    ~NetworkingSystem();
    
    bool Initialize();
    void Update();
    void Shutdown();
    
    // Connection management
    bool ConnectToServer(const std::string& serverAddress, int port);
    void Disconnect();
    bool IsConnected() const { return connected; }
    
    // Message sending
    void SendPlayerUpdate(const PlayerData& data);
    void SendGameEvent(const std::string& eventType, const std::string& eventData);
    void SendChatMessage(const std::string& message);
    
    // Message receiving
    void SetGameStateCallback(std::function<void(const GameState&)> callback);
    void SetPlayerUpdateCallback(std::function<void(const PlayerData&)> callback);
    void SetEventCallback(std::function<void(const std::string&)> callback);
    
private:
    bool connected;
    std::string serverAddress;
    int port;
    
    // Callbacks
    std::function<void(const GameState&)> gameStateCallback;
    std::function<void(const PlayerData&)> playerUpdateCallback;
    std::function<void(const std::string&)> eventCallback;
};

} // namespace PranjalEngine

#endif // NETWORKING_SYSTEM_H