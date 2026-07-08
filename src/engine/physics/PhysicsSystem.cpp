#include "PhysicsSystem.h"
#include <iostream>
#include <glm/geometric.hpp>

PhysicsSystem::PhysicsSystem() 
    : gravity(glm::vec3(0.0f, -9.81f, 0.0f)), fixedTimeStep(0.016f) {}

PhysicsSystem::~PhysicsSystem() {}

void PhysicsSystem::Initialize() {
    std::cout << "[Physics] Physics system initialized" << std::endl;
}

void PhysicsSystem::Shutdown() {
    rigidBodies.clear();
}

void PhysicsSystem::Update(float deltaTime) {
    for (auto& body : rigidBodies) {
        if (body->isStatic) continue;
        
        // Apply gravity
        body->acceleration += gravity;
        
        // Apply velocity
        body->velocity += body->acceleration * deltaTime;
        
        // Apply drag
        body->velocity *= (1.0f - body->drag * deltaTime);
        
        // Update position
        body->position += body->velocity * deltaTime;
        
        // Reset acceleration
        body->acceleration = glm::vec3(0.0f);
    }
}

bool PhysicsSystem::CheckCollision(const glm::vec3& pos1, float radius1, const glm::vec3& pos2, float radius2) {
    float distance = glm::distance(pos1, pos2);
    return distance < (radius1 + radius2);
}

glm::vec3 PhysicsSystem::GetCollisionNormal(const glm::vec3& pos1, const glm::vec3& pos2) {
    return glm::normalize(pos2 - pos1);
}

bool PhysicsSystem::Raycast(glm::vec3 start, glm::vec3 direction, float maxDistance, glm::vec3& hitPoint) {
    // Simple raycast implementation
    return false;
}
