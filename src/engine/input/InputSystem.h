#ifndef INPUT_SYSTEM_H
#define INPUT_SYSTEM_H

#include <glm/glm.hpp>
#include <vector>
#include <functional>

enum KeyCode {
    KEY_W, KEY_A, KEY_S, KEY_D,
    KEY_SPACE, KEY_SHIFT, KEY_CTRL,
    KEY_E, KEY_R, KEY_F, KEY_1, KEY_2, KEY_3, KEY_4,
    KEY_ESC, KEY_TAB, KEY_ENTER
};

enum InputType {
    INPUT_TOUCH,
    INPUT_KEYBOARD,
    INPUT_GAMEPAD
};

struct TouchInput {
    glm::vec2 position;
    glm::vec2 delta;
    bool pressed;
    bool released;
};

class InputSystem {
public:
    InputSystem();
    ~InputSystem();
    
    void Initialize();
    void Update();
    
    // Keyboard input
    bool IsKeyPressed(KeyCode key) const;
    bool IsKeyDown(KeyCode key) const;
    bool IsKeyReleased(KeyCode key) const;
    
    // Touch input
    const TouchInput& GetTouchInput() const { return touchInput; }
    bool IsTouching() const { return touchInput.pressed; }
    glm::vec2 GetTouchPosition() const { return touchInput.position; }
    glm::vec2 GetTouchDelta() const { return touchInput.delta; }
    
    // Gamepad input (future)
    float GetGamepadAxis(int axis) const;
    bool IsGamepadButtonPressed(int button) const;
    
    // Input callbacks
    typedef std::function<void(const TouchInput&)> TouchCallback;
    void RegisterTouchCallback(TouchCallback callback);
    
private:
    std::vector<bool> keyPressed;
    std::vector<bool> keyDown;
    std::vector<bool> keyReleased;
    
    TouchInput touchInput;
    std::vector<TouchCallback> touchCallbacks;
};

#endif // INPUT_SYSTEM_H
