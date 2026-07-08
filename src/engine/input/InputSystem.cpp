#include "InputSystem.h"
#include <iostream>

InputSystem::InputSystem() : keyPressed(256, false), keyDown(256, false), keyReleased(256, false) {
    touchInput = {{0.0f, 0.0f}, {0.0f, 0.0f}, false, false};
}

InputSystem::~InputSystem() {}

void InputSystem::Initialize() {
    std::cout << "[Input] Input system initialized" << std::endl;
}

void InputSystem::Update() {
    // Reset key events
    std::fill(keyPressed.begin(), keyPressed.end(), false);
    std::fill(keyReleased.begin(), keyReleased.end(), false);
    touchInput.delta = {0.0f, 0.0f};
    touchInput.released = false;
}

bool InputSystem::IsKeyPressed(KeyCode key) const {
    return keyPressed[key];
}

bool InputSystem::IsKeyDown(KeyCode key) const {
    return keyDown[key];
}

bool InputSystem::IsKeyReleased(KeyCode key) const {
    return keyReleased[key];
}

float InputSystem::GetGamepadAxis(int axis) const {
    return 0.0f; // Placeholder
}

bool InputSystem::IsGamepadButtonPressed(int button) const {
    return false; // Placeholder
}

void InputSystem::RegisterTouchCallback(TouchCallback callback) {
    touchCallbacks.push_back(callback);
}
