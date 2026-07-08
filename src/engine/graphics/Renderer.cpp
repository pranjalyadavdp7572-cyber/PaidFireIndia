#include "Renderer.h"
#include <GL/glew.h>
#include <iostream>

void ShaderProgram::Use() {
    glUseProgram(ID);
}

void ShaderProgram::SetUniformMatrix4fv(const std::string& name, glm::mat4 matrix) {
    GLint location = glGetUniformLocation(ID, name.c_str());
    glUniformMatrix4fv(location, 1, GL_FALSE, &matrix[0][0]);
}

void ShaderProgram::SetUniformVec3(const std::string& name, glm::vec3 vec) {
    GLint location = glGetUniformLocation(ID, name.c_str());
    glUniform3f(location, vec.x, vec.y, vec.z);
}

void ShaderProgram::SetUniformFloat(const std::string& name, float value) {
    GLint location = glGetUniformLocation(ID, name.c_str());
    glUniform1f(location, value);
}

void Renderer::RenderQuad(glm::vec2 position, glm::vec2 size, glm::vec4 color) {
    // Render quad using VAO/VBO
}

void Renderer::RenderCircle(glm::vec2 center, float radius, glm::vec4 color) {
    // Render circle
}

void Renderer::RenderLine(glm::vec2 start, glm::vec2 end, float width, glm::vec4 color) {
    // Render line
}

void Renderer::RenderTriangle(glm::vec2 p1, glm::vec2 p2, glm::vec2 p3, glm::vec4 color) {
    // Render triangle
}
