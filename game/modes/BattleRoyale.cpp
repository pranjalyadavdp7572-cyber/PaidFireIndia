#include "BattleRoyale.h"

namespace PranjalEngine {

BattleRoyale::BattleRoyale(Engine* e)
    : engine(e)
    , zoneCenter(0.0f, 0.0f, 0.0f)
    , zoneRadius(500.0f)
    , zoneRadius_target(500.0f)
    , zoneShrinkRate(1.0f)
    , gameActive(false)
    , gameTime(0.0f)
    , totalPlayers(50)
{
}

BattleRoyale::~BattleRoyale()
{
    players.clear();
    lootItems.clear();
}

bool BattleRoyale::Initialize()
{
    gameActive = true;
    gameTime = 0.0f;
    zoneCenter = glm::vec3(0.0f, 0.0f, 0.0f);
    zoneRadius = 500.0f;
    return true;
}

void BattleRoyale::Update(float deltaTime)
{
    if (!gameActive) return;
    
    gameTime += deltaTime;
    
    // Update zone
    UpdateZone(deltaTime);
    
    // Update players
    for (auto& player : players) {
        if (!player.alive) continue;
        
        // Check if player is in zone
        float distance = glm::distance(player.position, zoneCenter);
        if (distance > zoneRadius) {
            DamagePlayer(player.playerId, 5.0f * deltaTime);
        }
    }
    
    // Check win condition
    int alivePlayers = GetRemainingPlayers();
    if (alivePlayers <= 1) {
        gameActive = false;
    }
}

void BattleRoyale::Render()
{
    // TODO: Render zone, players, loot items
}

void BattleRoyale::AddPlayer(const std::string& playerId, const std::string& playerName)
{
    BRPlayer player;
    player.playerId = playerId;
    player.playerName = playerName;
    player.position = glm::vec3(0.0f, 0.0f, 0.0f);
    player.health = 100.0f;
    player.maxHealth = 100.0f;
    player.alive = true;
    player.kills = 0;
    player.ping = 0;
    
    players.push_back(player);
}

void BattleRoyale::RemovePlayer(const std::string& playerId)
{
    auto it = std::find_if(players.begin(), players.end(),
        [&playerId](const BRPlayer& p) { return p.playerId == playerId; });
    
    if (it != players.end()) {
        players.erase(it);
    }
}

void BattleRoyale::UpdatePlayerPosition(const std::string& playerId, const glm::vec3& position)
{
    auto it = std::find_if(players.begin(), players.end(),
        [&playerId](const BRPlayer& p) { return p.playerId == playerId; });
    
    if (it != players.end()) {
        it->position = position;
    }
}

void BattleRoyale::DamagePlayer(const std::string& playerId, float damage)
{
    auto it = std::find_if(players.begin(), players.end(),
        [&playerId](const BRPlayer& p) { return p.playerId == playerId; });
    
    if (it != players.end()) {
        it->health -= damage;
        if (it->health <= 0) {
            HandlePlayerDeath(playerId);
        }
    }
}

void BattleRoyale::UpdateZone(float deltaTime)
{
    // Gradually shrink zone
    if (gameTime > 30.0f) { // Start shrinking after 30 seconds
        zoneRadius_target -= zoneShrinkRate * deltaTime;
        zoneRadius = glm::mix(zoneRadius, zoneRadius_target, 0.1f);
    }
}

void BattleRoyale::SpawnLoot(const glm::vec3& position)
{
    LootItem item;
    item.itemId = "item_" + std::to_string(lootItems.size());
    item.itemName = "Weapon";
    item.itemType = "weapon";
    item.position = position;
    item.collected = false;
    
    lootItems.push_back(item);
}

void BattleRoyale::CollectLoot(const std::string& playerId, const std::string& lootId)
{
    auto it = std::find_if(lootItems.begin(), lootItems.end(),
        [&lootId](const LootItem& l) { return l.itemId == lootId; });
    
    if (it != lootItems.end()) {
        it->collected = true;
    }
}

int BattleRoyale::GetRemainingPlayers() const
{
    return std::count_if(players.begin(), players.end(),
        [](const BRPlayer& p) { return p.alive; });
}

void BattleRoyale::HandlePlayerDeath(const std::string& playerId)
{
    auto it = std::find_if(players.begin(), players.end(),
        [&playerId](const BRPlayer& p) { return p.playerId == playerId; });
    
    if (it != players.end()) {
        it->alive = false;
    }
}

} // namespace PranjalEngine