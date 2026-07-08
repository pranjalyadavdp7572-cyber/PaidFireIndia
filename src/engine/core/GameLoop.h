#ifndef GAME_LOOP_H
#define GAME_LOOP_H

#include <memory>

class GameState;
class GameScene;

class GameLoop {
public:
    GameLoop();
    ~GameLoop();
    
    void Initialize();
    void Update(float deltaTime);
    void Render();
    void Shutdown();
    
    void SetGameState(int state);
    int GetGameState() const { return currentState; }
    
private:
    std::unique_ptr<GameScene> currentScene;
    int currentState; // 0: Menu, 1: Lobby, 2: BR Game, 3: CS Game
};

#endif // GAME_LOOP_H
