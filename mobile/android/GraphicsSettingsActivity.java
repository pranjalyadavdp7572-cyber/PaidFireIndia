package com.pranjalfreefireindia;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class GraphicsSettingsActivity extends AppCompatActivity {
    
    private static final String TAG = "GraphicsSettings";
    private static final String DEVELOPER_EMAIL = "pranjalyadavdp9170@gmail.com";
    
    private SeekBar resolutionSeekBar, shadowQualitySeekBar, textureQualitySeekBar;
    private SeekBar drawDistanceSeekBar, fpsTargetSeekBar;
    private Switch bloomSwitch, motionBlurSwitch, aoSwitch;
    private TextView resolutionText, shadowText, textureText, drawDistanceText, fpsText, storageSizeText;
    private Button applyButton, presetLowButton, presetMediumButton, presetHighButton, presetUltraButton;
    
    private GraphicsSettings graphicsSettings;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_settings);
        
        Log.i(TAG, "Graphics Settings Activity opened - Developer: " + DEVELOPER_EMAIL);
        
        // Initialize graphics settings manager
        graphicsSettings = new GraphicsSettings(this);
        
        // Initialize views
        initializeViews();
        
        // Load current settings
        loadCurrentSettings();
        
        // Set listeners
        setupListeners();
        
        Log.i(TAG, "Graphics Settings initialized");
    }
    
    private void initializeViews() {
        resolutionSeekBar = findViewById(R.id.resolution_seekbar);
        shadowQualitySeekBar = findViewById(R.id.shadow_quality_seekbar);
        textureQualitySeekBar = findViewById(R.id.texture_quality_seekbar);
        drawDistanceSeekBar = findViewById(R.id.draw_distance_seekbar);
        fpsTargetSeekBar = findViewById(R.id.fps_target_seekbar);
        
        bloomSwitch = findViewById(R.id.bloom_switch);
        motionBlurSwitch = findViewById(R.id.motion_blur_switch);
        aoSwitch = findViewById(R.id.ao_switch);
        
        resolutionText = findViewById(R.id.resolution_text);
        shadowText = findViewById(R.id.shadow_text);
        textureText = findViewById(R.id.texture_text);
        drawDistanceText = findViewById(R.id.draw_distance_text);
        fpsText = findViewById(R.id.fps_text);
        storageSizeText = findViewById(R.id.storage_size_text);
        
        applyButton = findViewById(R.id.apply_button);
        presetLowButton = findViewById(R.id.preset_low_button);
        presetMediumButton = findViewById(R.id.preset_medium_button);
        presetHighButton = findViewById(R.id.preset_high_button);
        presetUltraButton = findViewById(R.id.preset_ultra_button);
    }
    
    private void loadCurrentSettings() {
        resolutionSeekBar.setProgress(graphicsSettings.getResolutionLevel());
        shadowQualitySeekBar.setProgress(graphicsSettings.getShadowQuality());
        textureQualitySeekBar.setProgress(graphicsSettings.getTextureQuality());
        drawDistanceSeekBar.setProgress(graphicsSettings.getDrawDistance());
        fpsTargetSeekBar.setProgress(graphicsSettings.getFpsTarget());
        
        bloomSwitch.setChecked(graphicsSettings.isBloomEnabled());
        motionBlurSwitch.setChecked(graphicsSettings.isMotionBlurEnabled());
        aoSwitch.setChecked(graphicsSettings.isAOEnabled());
        
        updateTextValues();
    }
    
    private void setupListeners() {
        // Resolution SeekBar
        resolutionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resolutionText.setText(getResolutionName(progress));
                updateStorageSize();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // Shadow Quality SeekBar
        shadowQualitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shadowText.setText(getShadowQualityName(progress));
                updateStorageSize();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // Texture Quality SeekBar
        textureQualitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textureText.setText(getTextureQualityName(progress));
                updateStorageSize();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // Draw Distance SeekBar
        drawDistanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawDistanceText.setText(getDrawDistanceName(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // FPS Target SeekBar
        fpsTargetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fpsText.setText(getFpsName(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        // Apply button
        applyButton.setOnClickListener(v -> applySettings());
        
        // Preset buttons
        presetLowButton.setOnClickListener(v -> applyPreset("low"));
        presetMediumButton.setOnClickListener(v -> applyPreset("medium"));
        presetHighButton.setOnClickListener(v -> applyPreset("high"));
        presetUltraButton.setOnClickListener(v -> applyPreset("ultra"));
    }
    
    private void updateTextValues() {
        resolutionText.setText(getResolutionName(resolutionSeekBar.getProgress()));
        shadowText.setText(getShadowQualityName(shadowQualitySeekBar.getProgress()));
        textureText.setText(getTextureQualityName(textureQualitySeekBar.getProgress()));
        drawDistanceText.setText(getDrawDistanceName(drawDistanceSeekBar.getProgress()));
        fpsText.setText(getFpsName(fpsTargetSeekBar.getProgress()));
    }
    
    private void updateStorageSize() {
        long estimatedSize = calculateEstimatedSize();
        String sizeStr = estimatedSize > 1024 ? 
            (estimatedSize / 1024) + " GB" : estimatedSize + " MB";
        storageSizeText.setText("Estimated Size: " + sizeStr);
    }
    
    private long calculateEstimatedSize() {
        long baseSize = 500; // MB
        baseSize += resolutionSeekBar.getProgress() * 300;
        baseSize += textureQualitySeekBar.getProgress() * 400;
        baseSize += shadowQualitySeekBar.getProgress() * 100;
        return baseSize;
    }
    
    private void applySettings() {
        graphicsSettings.setResolutionLevel(resolutionSeekBar.getProgress());
        graphicsSettings.setShadowQuality(shadowQualitySeekBar.getProgress());
        graphicsSettings.setTextureQuality(textureQualitySeekBar.getProgress());
        graphicsSettings.setDrawDistance(drawDistanceSeekBar.getProgress());
        graphicsSettings.setFpsTarget(fpsTargetSeekBar.getProgress());
        graphicsSettings.setBloomEnabled(bloomSwitch.isChecked());
        graphicsSettings.setMotionBlurEnabled(motionBlurSwitch.isChecked());
        graphicsSettings.setAOEnabled(aoSwitch.isChecked());
        graphicsSettings.saveSettings();
        
        Toast.makeText(this, "Graphics settings applied!", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Settings applied");
    }
    
    private void applyPreset(String preset) {
        switch(preset) {
            case "low":
                resolutionSeekBar.setProgress(0);
                shadowQualitySeekBar.setProgress(0);
                textureQualitySeekBar.setProgress(0);
                drawDistanceSeekBar.setProgress(0);
                fpsTargetSeekBar.setProgress(0);
                break;
            case "medium":
                resolutionSeekBar.setProgress(1);
                shadowQualitySeekBar.setProgress(1);
                textureQualitySeekBar.setProgress(1);
                drawDistanceSeekBar.setProgress(1);
                fpsTargetSeekBar.setProgress(1);
                break;
            case "high":
                resolutionSeekBar.setProgress(2);
                shadowQualitySeekBar.setProgress(2);
                textureQualitySeekBar.setProgress(2);
                drawDistanceSeekBar.setProgress(2);
                fpsTargetSeekBar.setProgress(2);
                break;
            case "ultra":
                resolutionSeekBar.setProgress(3);
                shadowQualitySeekBar.setProgress(3);
                textureQualitySeekBar.setProgress(3);
                drawDistanceSeekBar.setProgress(3);
                fpsTargetSeekBar.setProgress(2);
                break;
        }
        updateTextValues();
        updateStorageSize();
        Toast.makeText(this, preset.toUpperCase() + " preset applied!", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Preset " + preset + " applied");
    }
    
    private String getResolutionName(int level) {
        String[] names = {"720p (Low)", "1080p (Medium)", "1440p (High)", "2K (Ultra)"};
        return level < names.length ? names[level] : names[0];
    }
    
    private String getShadowQualityName(int level) {
        String[] names = {"Off", "Low", "Medium", "High"};
        return level < names.length ? names[level] : names[0];
    }
    
    private String getTextureQualityName(int level) {
        String[] names = {"512px", "1024px", "2048px", "4K"};
        return level < names.length ? names[level] : names[0];
    }
    
    private String getDrawDistanceName(int level) {
        String[] names = {"50m", "100m", "200m", "500m+"};
        return level < names.length ? names[level] : names[0];
    }
    
    private String getFpsName(int level) {
        String[] names = {"30 FPS", "60 FPS", "60 FPS High", "120 FPS"};
        return level < names.length ? names[level] : names[0];
    }
}
