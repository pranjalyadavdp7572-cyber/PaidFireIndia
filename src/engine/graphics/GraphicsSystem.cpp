#include "GraphicsSystem.h"
#include <iostream>
#include <GL/glew.h>

GraphicsSystem::GraphicsSystem() 
    : graphicsQuality(2), screenWidth(1080), screenHeight(2340),
      shadowsEnabled(true), antialiasEnabled(true), bloomEnabled(false),
      fpsLimit(60.0f) {}

GraphicsSystem::~GraphicsSystem() {}

void GraphicsSystem::Initialize() {
    std::cout << "[Graphics] Initializing graphics system..." << std::endl;
    
    // Initialize OpenGL ES
    std::cout << "[Graphics] OpenGL ES 3.0 enabled" << std::endl;
    std::cout << "[Graphics] Resolution: " << screenWidth << "x" << screenHeight << std::endl;
    
    // Set viewport
    glViewport(0, 0, screenWidth, screenHeight);
    
    // Enable depth testing
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    
    std::cout << "[Graphics] Initialization complete!" << std::endl;
}

void GraphicsSystem::Shutdown() {
    std::cout << "[Graphics] Shutting down..." << std::endl;
    shaders.clear();
    textures.clear();
}

void GraphicsSystem::BeginFrame() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
}

void GraphicsSystem::EndFrame() {
    // Swap buffers (handled by platform layer)
}

void GraphicsSystem::Clear(glm::vec4 color) {
    glClearColor(color.r, color.g, color.b, color.a);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
}

void GraphicsSystem::SetGraphicsQuality(int level) {
    graphicsQuality = level;
    std::cout << "[Graphics] Quality set to level " << level << std::endl;
    
    switch(level) {
        case 0: // Low
            shadowsEnabled = false;
            antialiasEnabled = false;
            bloomEnabled = false;
            fpsLimit = 30.0f;
            break;
        case 1: // Medium
            shadowsEnabled = false;
            antialiasEnabled = true;
            bloomEnabled = false;
            fpsLimit = 60.0f;
            break;
        case 2: // High
            shadowsEnabled = true;
            antialiasEnabled = true;
            bloomEnabled = false;
            fpsLimit = 60.0f;
            break;
        case 3: // Ultra
            shadowsEnabled = true;
            antialiasEnabled = true;
            bloomEnabled = true;
            fpsLimit = 120.0f;
            break;
    }
}

void GraphicsSystem::SetResolution(int width, int height) {
    screenWidth = width;
    screenHeight = height;
    glViewport(0, 0, screenWidth, screenHeight);
    std::cout << "[Graphics] Resolution changed to " << width << "x" << height << std::endl;
}

void GraphicsSystem::GetResolution(int& width, int& height) const {
    width = screenWidth;
    height = screenHeight;
}

void GraphicsSystem::RenderMesh(void* mesh, glm::mat4 transform) {
    // Render 3D mesh
}

void GraphicsSystem::RenderText(const std::string& text, float x, float y, float scale) {
    // Render text at position
}

void GraphicsSystem::RenderUI(void* uiElement) {
    // Render UI element
}
