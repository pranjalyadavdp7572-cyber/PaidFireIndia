#include "CSRanked.h"
#include <algorithm>

namespace PranjalEngine {

CSRanked::CSRanked(Engine* e)
    : engine(e)
    , gameActive(false)
    , roundTime(0.0f)
    , totalRounds(16)
{
    currentRound.roundNumber = 0;
    currentRound.timeRemaining = 300.0f; // 5 minutes per round
    currentRound.teamAScore = 0;
    currentRound.teamBScore = 0;
}

CSRanked::~CSRanked()
{
    players.clear();
}

bool CSRanked::Initialize()
{
    gameActive = true;
    currentRound.roundNumber = 1;
    StartRound();
    return true;
}

void CSRanked::Update(float deltaTime)
{
    if (!gameActive) return;
    
    UpdateRoundTime(deltaTime);
    CheckRoundEnd();
    
    // Update players
    for (auto& player : players) {
        if (!player.alive) continue;
        // Update player logic
    }
}

void CSRanked::Render()
{
    // TODO: Render map, players, UI
}

void CSRanked::AddPlayer(const std::string& playerId, const std::string& playerName, int teamId)
{
    CSPlayer player;
    player.playerId = playerId;
    player.playerName = playerName;
    player.teamId = teamId;
    player.health = 100.0f;
    player.alive = true;
    player.kills = 0;
    player.deaths = 0;
    player.assists = 0;
    player.money = 2400; // Starting money
    
    players.push_back(player);
}

void CSRanked::RemovePlayer(const std::string& playerId)
{
    auto it = std::find_if(players.begin(), players.end(),
        [&playerId](const CSPlayer& p) { return p.playerId == playerId; });
    
    if (it != players.end()) {
        players.erase(it);
    }
}

void CSRanked::UpdatePlayerPosition(const std::string& playerId, const glm::vec3& position)
{
    auto it = std::find_if(players.begin(), players.end(),
        [&playerId](const CSPlayer& p) { return p.playerId == playerId; });
    
    if (it != players.end()) {
        it->position = position;
    }
}

void CSRanked::DamagePlayer(const std::string& attacker, const std::string& victim, float damage)
{
    auto it = std::find_if(players.begin(), players.end(),
        [&victim](const CSPlayer& p) { return p.playerId == victim; });
    
    if (it != players.end()) {
        it->health -= damage;
        if (it->health <= 0) {
            KillPlayer(attacker, victim);
        }
    }
}

void CSRanked::KillPlayer(const std::string& attacker, const std::string& victim)
{
    auto attackerIt = std::find_if(players.begin(), players.end(),
        [&attacker](const CSPlayer& p) { return p.playerId == attacker; });
    
    auto victimIt = std::find_if(players.begin(), players.end(),
        [&victim](const CSPlayer& p) { return p.playerId == victim; });
    
    if (attackerIt != players.end() && victimIt != players.end()) {
        attackerIt->kills++;
        victimIt->deaths++;
        victimIt->alive = false;
    }
}

void CSRanked::StartRound()
{
    currentRound.roundNumber++;
    currentRound.timeRemaining = 300.0f;
    
    // Respawn all players
    for (auto& player : players) {
        player.alive = true;
        player.health = 100.0f;
    }
}

void CSRanked::EndRound()
{
    int teamAAlive = std::count_if(players.begin(), players.end(),
        [](const CSPlayer& p) { return p.teamId == 0 && p.alive; });
    
    int teamBAlive = std::count_if(players.begin(), players.end(),
        [](const CSPlayer& p) { return p.teamId == 1 && p.alive; });
    
    if (teamAAlive == 0) {
        currentRound.teamBScore++;
    } else if (teamBAlive == 0) {
        currentRound.teamAScore++;
    }
    
    if (currentRound.roundNumber < totalRounds) {
        StartRound();
    } else {
        gameActive = false;
    }
}

void CSRanked::UpdateRoundTime(float deltaTime)
{
    currentRound.timeRemaining -= deltaTime;
}

void CSRanked::CheckRoundEnd()
{
    if (currentRound.timeRemaining <= 0.0f) {
        EndRound();
    }
}

void CSRanked::GiveWeapon(const std::string& playerId, const std::string& weaponType)
{
    // TODO: Implement weapon giving
}

void CSRanked::BuyWeapon(const std::string& playerId, const std::string& weaponType)
{
    // TODO: Implement weapon buying
}

} // namespace PranjalEngine