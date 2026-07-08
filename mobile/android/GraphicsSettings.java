package com.pranjalfreefireindia;

import android.content.Context;
import android.content.SharedPreferences;

public class GraphicsSettings {
    
    private static final String PREFS_NAME = "GraphicsSettings";
    private static final String PREF_RESOLUTION = "resolution_level";
    private static final String PREF_SHADOW = "shadow_quality";
    private static final String PREF_TEXTURE = "texture_quality";
    private static final String PREF_DRAW_DISTANCE = "draw_distance";
    private static final String PREF_FPS_TARGET = "fps_target";
    private static final String PREF_BLOOM = "bloom_enabled";
    private static final String PREF_MOTION_BLUR = "motion_blur_enabled";
    private static final String PREF_AO = "ao_enabled";
    
    private SharedPreferences prefs;
    
    public GraphicsSettings(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    // Resolution
    public void setResolutionLevel(int level) {
        prefs.edit().putInt(PREF_RESOLUTION, level).apply();
    }
    
    public int getResolutionLevel() {
        return prefs.getInt(PREF_RESOLUTION, 1); // Default: 1080p
    }
    
    // Shadow Quality
    public void setShadowQuality(int quality) {
        prefs.edit().putInt(PREF_SHADOW, quality).apply();
    }
    
    public int getShadowQuality() {
        return prefs.getInt(PREF_SHADOW, 1);
    }
    
    // Texture Quality
    public void setTextureQuality(int quality) {
        prefs.edit().putInt(PREF_TEXTURE, quality).apply();
    }
    
    public int getTextureQuality() {
        return prefs.getInt(PREF_TEXTURE, 1);
    }
    
    // Draw Distance
    public void setDrawDistance(int distance) {
        prefs.edit().putInt(PREF_DRAW_DISTANCE, distance).apply();
    }
    
    public int getDrawDistance() {
        return prefs.getInt(PREF_DRAW_DISTANCE, 1);
    }
    
    // FPS Target
    public void setFpsTarget(int fps) {
        prefs.edit().putInt(PREF_FPS_TARGET, fps).apply();
    }
    
    public int getFpsTarget() {
        return prefs.getInt(PREF_FPS_TARGET, 1); // Default: 60 FPS
    }
    
    // Bloom
    public void setBloomEnabled(boolean enabled) {
        prefs.edit().putBoolean(PREF_BLOOM, enabled).apply();
    }
    
    public boolean isBloomEnabled() {
        return prefs.getBoolean(PREF_BLOOM, true);
    }
    
    // Motion Blur
    public void setMotionBlurEnabled(boolean enabled) {
        prefs.edit().putBoolean(PREF_MOTION_BLUR, enabled).apply();
    }
    
    public boolean isMotionBlurEnabled() {
        return prefs.getBoolean(PREF_MOTION_BLUR, false);
    }
    
    // Ambient Occlusion
    public void setAOEnabled(boolean enabled) {
        prefs.edit().putBoolean(PREF_AO, enabled).apply();
    }
    
    public boolean isAOEnabled() {
        return prefs.getBoolean(PREF_AO, true);
    }
    
    public void saveSettings() {
        // Settings are auto-saved via SharedPreferences
    }
}
