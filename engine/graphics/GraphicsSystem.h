#ifndef GRAPHICS_SYSTEM_H
#define GRAPHICS_SYSTEM_H

#include <glm/glm.hpp>
#include <memory>
#include <vector>

namespace PranjalEngine {

class Shader;
class Texture;
class Mesh;

class GraphicsSystem {
public:
    GraphicsSystem();
    ~GraphicsSystem();
    
    bool Initialize(int width, int height);
    void BeginFrame();
    void EndFrame();
    void SetResolution(int width, int height);
    
    // Rendering
    void DrawMesh(const Mesh* mesh, const glm::mat4& transform);
    void DrawParticles(const std::vector<glm::vec3>& positions, float size);
    void SetProjectionMatrix(const glm::mat4& proj);
    void SetViewMatrix(const glm::mat4& view);
    void SetClearColor(const glm::vec4& color);
    
    // Resource management
    std::shared_ptr<Shader> CreateShader(const std::string& vertexPath, const std::string& fragmentPath);
    std::shared_ptr<Texture> LoadTexture(const std::string& path);
    std::shared_ptr<Mesh> LoadMesh(const std::string& path);
    
private:
    int screenWidth;
    int screenHeight;
    glm::mat4 projectionMatrix;
    glm::mat4 viewMatrix;
    glm::vec4 clearColor;
    
    // OpenGL context
    void* glContext;
};

} // namespace PranjalEngine

#endif // GRAPHICS_SYSTEM_H