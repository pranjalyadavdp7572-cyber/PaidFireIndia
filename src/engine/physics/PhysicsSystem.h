#ifndef PHYSICS_SYSTEM_H
#define PHYSICS_SYSTEM_H

#include <glm/glm.hpp>
#include <vector>
#include <memory>

struct RigidBody {
    glm::vec3 position;
    glm::vec3 velocity;
    glm::vec3 acceleration;
    float mass;
    float drag;
    bool isStatic;
};

class PhysicsSystem {
public:
    PhysicsSystem();
    ~PhysicsSystem();
    
    void Initialize();
    void Shutdown();
    void Update(float deltaTime);
    
    // Gravity and environment
    void SetGravity(glm::vec3 gravity) { this->gravity = gravity; }
    glm::vec3 GetGravity() const { return gravity; }
    
    // Collision detection
    bool CheckCollision(const glm::vec3& pos1, float radius1, const glm::vec3& pos2, float radius2);
    glm::vec3 GetCollisionNormal(const glm::vec3& pos1, const glm::vec3& pos2);
    
    // Raycast
    bool Raycast(glm::vec3 start, glm::vec3 direction, float maxDistance, glm::vec3& hitPoint);
    
private:
    glm::vec3 gravity;
    std::vector<std::shared_ptr<RigidBody>> rigidBodies;
    float fixedTimeStep;
};

#endif // PHYSICS_SYSTEM_H
