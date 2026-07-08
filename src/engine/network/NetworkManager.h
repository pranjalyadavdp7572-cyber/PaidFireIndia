#ifndef NETWORK_MANAGER_H
#define NETWORK_MANAGER_H

#include <string>
#include <memory>
#include <vector>
#include <functional>
#include <glm/glm.hpp>

enum ConnectionState {
    CONN_DISCONNECTED = 0,
    CONN_CONNECTING = 1,
    CONN_CONNECTED = 2,
    CONN_ERROR = 3
};

struct NetworkPlayer {
    unsigned int id;
    std::string username;
    glm::vec3 position;
    float health;
    unsigned int kills;
    unsigned int deaths;
};

class NetworkManager {
public:
    NetworkManager();
    ~NetworkManager();
    
    void Initialize();
    void Shutdown();
    void Update(float deltaTime);
    
    // Connection management
    void Connect(const std::string& serverAddress, int port);
    void Disconnect();
    bool IsConnected() const { return connectionState == CONN_CONNECTED; }
    ConnectionState GetConnectionState() const { return connectionState; }
    
    // Game commands
    void SendPlayerPosition(glm::vec3 position);
    void SendPlayerHealth(float health);
    void SendPlayerAction(const std::string& action);
    void SendChatMessage(const std::string& message);
    
    // Receive data
    const std::vector<NetworkPlayer>& GetNetworkPlayers() const { return networkPlayers; }
    
    // Callbacks
    typedef std::function<void(const NetworkPlayer&)> PlayerUpdateCallback;
    void RegisterPlayerUpdateCallback(PlayerUpdateCallback callback);
    
private:
    ConnectionState connectionState;
    std::string serverAddress;
    int serverPort;
    
    std::vector<NetworkPlayer> networkPlayers;
    std::vector<PlayerUpdateCallback> playerCallbacks;
    
    float connectionTimeout;
    float lastHeartbeat;
};

#endif // NETWORK_MANAGER_H
