#ifndef CS_RANKED_H
#define CS_RANKED_H

#include <memory>
#include <vector>
#include <glm/glm.hpp>
#include "../../engine/core/Engine.h"

namespace PranjalEngine {

struct CSPlayer {
    std::string playerId;
    std::string playerName;
    glm::vec3 position;
    glm::vec3 viewDirection;
    float health;
    int teamId; // 0 = Team A, 1 = Team B
    bool alive;
    int kills;
    int deaths;
    int assists;
    int money;
};

struct CSRound {
    int roundNumber;
    float timeRemaining;
    int teamAScore;
    int teamBScore;
};

class CSRanked {
public:
    CSRanked(Engine* engine);
    ~CSRanked();
    
    bool Initialize();
    void Update(float deltaTime);
    void Render();
    
    // Player management
    void AddPlayer(const std::string& playerId, const std::string& playerName, int teamId);
    void RemovePlayer(const std::string& playerId);
    void UpdatePlayerPosition(const std::string& playerId, const glm::vec3& position);
    void DamagePlayer(const std::string& attacker, const std::string& victim, float damage);
    void KillPlayer(const std::string& attacker, const std::string& victim);
    
    // Round management
    void StartRound();
    void EndRound();
    CSRound GetCurrentRound() const { return currentRound; }
    
    // Weapon management
    void GiveWeapon(const std::string& playerId, const std::string& weaponType);
    void BuyWeapon(const std::string& playerId, const std::string& weaponType);
    
    // Game state
    int GetTeamAScore() const { return currentRound.teamAScore; }
    int GetTeamBScore() const { return currentRound.teamBScore; }
    bool IsGameActive() const { return gameActive; }
    
private:
    Engine* engine;
    std::vector<CSPlayer> players;
    CSRound currentRound;
    
    bool gameActive;
    float roundTime;
    int totalRounds;
    
    void UpdateRoundTime(float deltaTime);
    void CheckRoundEnd();
};

} // namespace PranjalEngine

#endif // CS_RANKED_H