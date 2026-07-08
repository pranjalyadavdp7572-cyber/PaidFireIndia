#ifndef RENDERER_H
#define RENDERER_H

#include <glm/glm.hpp>
#include <string>
#include <map>
#include <memory>

struct ShaderProgram {
    unsigned int ID;
    void Use();
    void SetUniformMatrix4fv(const std::string& name, glm::mat4 matrix);
    void SetUniformVec3(const std::string& name, glm::vec3 vec);
    void SetUniformFloat(const std::string& name, float value);
};

class Renderer {
public:
    static void RenderQuad(glm::vec2 position, glm::vec2 size, glm::vec4 color);
    static void RenderCircle(glm::vec2 center, float radius, glm::vec4 color);
    static void RenderLine(glm::vec2 start, glm::vec2 end, float width, glm::vec4 color);
    static void RenderTriangle(glm::vec2 p1, glm::vec2 p2, glm::vec2 p3, glm::vec4 color);
};

#endif // RENDERER_H
