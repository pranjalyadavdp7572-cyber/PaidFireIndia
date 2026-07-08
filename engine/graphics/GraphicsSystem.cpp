#include "GraphicsSystem.h"

namespace PranjalEngine {

GraphicsSystem::GraphicsSystem()
    : screenWidth(1080)
    , screenHeight(1920)
    , clearColor(0.1f, 0.1f, 0.1f, 1.0f)
    , glContext(nullptr)
{
}

GraphicsSystem::~GraphicsSystem()
{
}

bool GraphicsSystem::Initialize(int width, int height)
{
    screenWidth = width;
    screenHeight = height;
    
    // Initialize OpenGL ES 3.0 context
    // TODO: Platform-specific OpenGL initialization
    
    // Set up projection matrix
    projectionMatrix = glm::perspective(glm::radians(45.0f), 
                                       (float)width / (float)height, 
                                       0.1f, 1000.0f);
    
    return true;
}

void GraphicsSystem::BeginFrame()
{
    // Clear buffers
    // TODO: glClearColor and glClear calls
}

void GraphicsSystem::EndFrame()
{
    // Present frame
    // TODO: Platform-specific presentation
}

void GraphicsSystem::SetResolution(int width, int height)
{
    screenWidth = width;
    screenHeight = height;
    projectionMatrix = glm::perspective(glm::radians(45.0f), 
                                       (float)width / (float)height, 
                                       0.1f, 1000.0f);
}

void GraphicsSystem::DrawMesh(const Mesh* mesh, const glm::mat4& transform)
{
    // TODO: Implement mesh rendering
}

void GraphicsSystem::DrawParticles(const std::vector<glm::vec3>& positions, float size)
{
    // TODO: Implement particle rendering
}

void GraphicsSystem::SetProjectionMatrix(const glm::mat4& proj)
{
    projectionMatrix = proj;
}

void GraphicsSystem::SetViewMatrix(const glm::mat4& view)
{
    viewMatrix = view;
}

void GraphicsSystem::SetClearColor(const glm::vec4& color)
{
    clearColor = color;
}

} // namespace PranjalEngine