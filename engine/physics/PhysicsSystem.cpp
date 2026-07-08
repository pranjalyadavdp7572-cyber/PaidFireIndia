#include "PhysicsSystem.h"

namespace PranjalEngine {

PhysicsSystem::PhysicsSystem()
    : gravity(0.0f, -9.81f, 0.0f)
    , damping(0.99f)
{
}

PhysicsSystem::~PhysicsSystem()
{
    rigidBodies.clear();
}

bool PhysicsSystem::Initialize()
{
    return true;
}

void PhysicsSystem::Update(float deltaTime)
{
    IntegrateForces(deltaTime);
    SolveCollisions();
}

std::shared_ptr<RigidBody> PhysicsSystem::CreateRigidBody(const glm::vec3& position, float mass)
{
    // TODO: Create and return rigid body
    return nullptr;
}

void PhysicsSystem::RemoveRigidBody(const std::shared_ptr<RigidBody>& body)
{
    // TODO: Remove rigid body from simulation
}

void PhysicsSystem::SetGravity(const glm::vec3& g)
{
    gravity = g;
}

void PhysicsSystem::SetDamping(float d)
{
    damping = d;
}

bool PhysicsSystem::RaycastHit(const glm::vec3& origin, const glm::vec3& direction, 
                               float& distance, glm::vec3& normal)
{
    // TODO: Implement raycast collision detection
    return false;
}

void PhysicsSystem::SolveCollisions()
{
    // TODO: Implement collision solving
}

void PhysicsSystem::IntegrateForces(float deltaTime)
{
    // TODO: Apply forces and integrate velocities
}

} // namespace PranjalEngine