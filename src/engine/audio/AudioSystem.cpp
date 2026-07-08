#include "AudioSystem.h"
#include <iostream>

AudioClip::AudioClip(const std::string& path) 
    : filePath(path), volume(1.0f), playing(false) {}

void AudioClip::Play() {
    playing = true;
}

void AudioClip::Pause() {
    playing = false;
}

void AudioClip::Stop() {
    playing = false;
}

void AudioClip::SetVolume(float vol) {
    volume = vol;
}

AudioSystem::AudioSystem()
    : masterVolume(1.0f), musicVolume(0.8f), sfxVolume(1.0f), dialogueVolume(1.0f) {}

AudioSystem::~AudioSystem() {}

void AudioSystem::Initialize() {
    std::cout << "[Audio] Audio system initialized" << std::endl;
}

void AudioSystem::Shutdown() {
    audioClips.clear();
}

void AudioSystem::SetMasterVolume(float volume) {
    masterVolume = volume;
}

void AudioSystem::SetMusicVolume(float volume) {
    musicVolume = volume;
}

void AudioSystem::SetSFXVolume(float volume) {
    sfxVolume = volume;
}

void AudioSystem::SetDialogueVolume(float volume) {
    dialogueVolume = volume;
}

void AudioSystem::PlaySound(const std::string& soundName) {
    std::cout << "[Audio] Playing sound: " << soundName << std::endl;
}

void AudioSystem::PlayMusic(const std::string& musicName, bool loop) {
    std::cout << "[Audio] Playing music: " << musicName << std::endl;
}

void AudioSystem::StopMusic() {
    std::cout << "[Audio] Music stopped" << std::endl;
}
