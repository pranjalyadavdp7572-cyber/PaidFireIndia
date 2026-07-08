#ifndef AUDIO_SYSTEM_H
#define AUDIO_SYSTEM_H

#include <string>
#include <map>
#include <memory>

class AudioClip {
public:
    AudioClip(const std::string& path);
    void Play();
    void Pause();
    void Stop();
    void SetVolume(float volume);
    bool IsPlaying() const { return playing; }
    
private:
    std::string filePath;
    float volume;
    bool playing;
};

class AudioSystem {
public:
    AudioSystem();
    ~AudioSystem();
    
    void Initialize();
    void Shutdown();
    void Update(float deltaTime) {}
    
    // Volume control
    void SetMasterVolume(float volume);
    void SetMusicVolume(float volume);
    void SetSFXVolume(float volume);
    void SetDialogueVolume(float volume);
    
    float GetMasterVolume() const { return masterVolume; }
    float GetMusicVolume() const { return musicVolume; }
    float GetSFXVolume() const { return sfxVolume; }
    float GetDialogueVolume() const { return dialogueVolume; }
    
    // Audio playback
    void PlaySound(const std::string& soundName);
    void PlayMusic(const std::string& musicName, bool loop = true);
    void StopMusic();
    
private:
    float masterVolume;
    float musicVolume;
    float sfxVolume;
    float dialogueVolume;
    
    std::map<std::string, std::shared_ptr<AudioClip>> audioClips;
};

#endif // AUDIO_SYSTEM_H
