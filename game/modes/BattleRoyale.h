#ifndef BATTLE_ROYALE_H
#define BATTLE_ROYALE_H

#include <memory>
#include <vector>
#include <glm/glm.hpp>
#include "../../engine/core/Engine.h"

namespace PranjalEngine {

struct BRPlayer {
    std::string playerId;
    std::string playerName;
    glm::vec3 position;
    float health;
    float maxHealth;
    bool alive;
    int kills;
    int ping;
};

struct LootItem {
    std::string itemId;
    std::string itemName;
    std::string itemType; // weapon, ammo, health, shield
    glm::vec3 position;
    bool collected;
};

class BattleRoyale {
public:
    BattleRoyale(Engine* engine);
    ~BattleRoyale();
    
    bool Initialize();
    void Update(float deltaTime);
    void Render();
    
    // Player management
    void AddPlayer(const std::string& playerId, const std::string& playerName);
    void RemovePlayer(const std::string& playerId);
    void UpdatePlayerPosition(const std::string& playerId, const glm::vec3& position);
    void DamagePlayer(const std::string& playerId, float damage);
    
    // Zone management
    void UpdateZone(float deltaTime);
    glm::vec3 GetZoneCenter() const { return zoneCenter; }
    float GetZoneRadius() const { return zoneRadius; }
    
    // Loot system
    void SpawnLoot(const glm::vec3& position);
    void CollectLoot(const std::string& playerId, const std::string& lootId);
    
    // Game state
    int GetRemainingPlayers() const;
    bool IsGameActive() const { return gameActive; }
    
private:
    Engine* engine;
    std::vector<BRPlayer> players;
    std::vector<LootItem> lootItems;
    
    // Zone
    glm::vec3 zoneCenter;
    float zoneRadius;
    float zoneRadius_target;
    float zoneShrinkRate;
    
    // Game state
    bool gameActive;
    float gameTime;
    int totalPlayers;
    
    void HandlePlayerDeath(const std::string& playerId);
};

} // namespace PranjalEngine

#endif // BATTLE_ROYALE_H