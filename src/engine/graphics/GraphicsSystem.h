#ifndef GRAPHICS_SYSTEM_H
#define GRAPHICS_SYSTEM_H

#include <glm/glm.hpp>
#include <vector>
#include <memory>

class Shader;
class Texture;

class GraphicsSystem {
public:
    GraphicsSystem();
    ~GraphicsSystem();
    
    void Initialize();
    void Shutdown();
    void BeginFrame();
    void EndFrame();
    void Clear(glm::vec4 color = glm::vec4(0.1f, 0.1f, 0.1f, 1.0f));
    
    // Graphics settings
    void SetGraphicsQuality(int level); // 0: Low, 1: Medium, 2: High, 3: Ultra
    int GetGraphicsQuality() const { return graphicsQuality; }
    
    void SetResolution(int width, int height);
    void GetResolution(int& width, int& height) const;
    
    void SetShadowQuality(bool enabled) { shadowsEnabled = enabled; }
    void SetAntialias(bool enabled) { antialiasEnabled = enabled; }
    void SetBloom(bool enabled) { bloomEnabled = enabled; }
    
    // Rendering
    void RenderMesh(void* mesh, glm::mat4 transform);
    void RenderText(const std::string& text, float x, float y, float scale);
    void RenderUI(void* uiElement);
    
private:
    int graphicsQuality; // 0-3
    int screenWidth, screenHeight;
    bool shadowsEnabled;
    bool antialiasEnabled;
    bool bloomEnabled;
    float fpsLimit;
    
    std::vector<std::shared_ptr<Shader>> shaders;
    std::vector<std::shared_ptr<Texture>> textures;
};

#endif // GRAPHICS_SYSTEM_H
