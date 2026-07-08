#ifndef PHYSICS_SYSTEM_H
#define PHYSICS_SYSTEM_H

#include <glm/glm.hpp>
#include <memory>
#include <vector>

namespace PranjalEngine {

class RigidBody;

class PhysicsSystem {
public:
    PhysicsSystem();
    ~PhysicsSystem();
    
    bool Initialize();
    void Update(float deltaTime);
    
    // Rigid body management
    std::shared_ptr<RigidBody> CreateRigidBody(const glm::vec3& position, float mass);
    void RemoveRigidBody(const std::shared_ptr<RigidBody>& body);
    
    // Physics properties
    void SetGravity(const glm::vec3& gravity);
    void SetDamping(float damping);
    
    // Collision detection
    bool RaycastHit(const glm::vec3& origin, const glm::vec3& direction, 
                    float& distance, glm::vec3& normal);
    
private:
    std::vector<std::shared_ptr<RigidBody>> rigidBodies;
    glm::vec3 gravity;
    float damping;
    
    void SolveCollisions();
    void IntegrateForces(float deltaTime);
};

} // namespace PranjalEngine

#endif // PHYSICS_SYSTEM_H