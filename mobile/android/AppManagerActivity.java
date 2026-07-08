package com.pranjalfreefireindia;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppManagerActivity extends AppCompatActivity {
    
    private static final String TAG = "AppManager";
    private static final String DEVELOPER_EMAIL = "pranjalyadavdp9170@gmail.com";
    
    private ListView gameModesListView;
    private TextView storageUsedText, storageTotalText;
    private Button cleanCacheButton;
    private ProgressBar downloadProgressBar;
    
    private List<GameModePackage> gameModules;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        
        Log.i(TAG, "App Manager opened - Developer: " + DEVELOPER_EMAIL);
        
        // Initialize views
        gameModesListView = findViewById(R.id.game_modes_list);
        storageUsedText = findViewById(R.id.storage_used_text);
        storageTotalText = findViewById(R.id.storage_total_text);
        cleanCacheButton = findViewById(R.id.clean_cache_button);
        downloadProgressBar = findViewById(R.id.download_progress);
        
        // Setup game modules
        setupGameModules();
        
        // Display storage info
        updateStorageInfo();
        
        // Clean cache button
        cleanCacheButton.setOnClickListener(v -> cleanCache());
    }
    
    private void setupGameModules() {
        gameModules = new ArrayList<>();
        
        // Battle Royale Module
        gameModules.add(new GameModePackage(
                "Battle Royale (50v50)",
                "1.8 GB",
                1800,
                true,
                "The ultimate battle royale experience with 50 players"
        ));
        
        // CS Ranked Module
        gameModules.add(new GameModePackage(
                "CS Ranked (4v4)",
                "800 MB",
                800,
                false,
                "Team-based competitive tactical gameplay"
        ));
        
        // Cosmetics Module
        gameModules.add(new GameModePackage(
                "Cosmetics & Skins",
                "200 MB",
                200,
                false,
                "Weapon skins, character cosmetics, and emotes"
        ));
        
        // Maps Module
        gameModules.add(new GameModePackage(
                "Additional Maps",
                "500 MB",
                500,
                false,
                "Extra game maps for variety"
        ));
        
        // Voice Chat Module
        gameModules.add(new GameModePackage(
                "Voice Chat Pack",
                "150 MB",
                150,
                true,
                "Real-time voice communication"
        ));
        
        // Display modules
        GameModeAdapter adapter = new GameModeAdapter(this, gameModules);
        gameModesListView.setAdapter(adapter);
    }
    
    private void updateStorageInfo() {
        long usedSpace = 0;
        long totalSpace = 0;
        
        for (GameModePackage module : gameModules) {
            if (module.isInstalled) {
                usedSpace += module.sizeInMB;
            }
        }
        
        totalSpace = 4096; // 4 GB
        
        storageUsedText.setText(String.format("📱 Used: %.2f GB / %.2f GB", 
                usedSpace / 1024.0, totalSpace / 1024.0));
        
        Log.i(TAG, "Storage: " + usedSpace + " MB used");
    }
    
    private void cleanCache() {
        File cacheDir = getCacheDir();
        deleteDir(cacheDir);
        
        Log.i(TAG, "Cache cleaned");
        android.widget.Toast.makeText(this, "Cache cleaned!", android.widget.Toast.LENGTH_SHORT).show();
        updateStorageInfo();
    }
    
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    // Game Mode Package Class
    public static class GameModePackage {
        public String name;
        public String sizeStr;
        public long sizeInMB;
        public boolean isInstalled;
        public String description;
        public int downloadProgress;
        
        public GameModePackage(String name, String sizeStr, long sizeInMB, 
                             boolean isInstalled, String description) {
            this.name = name;
            this.sizeStr = sizeStr;
            this.sizeInMB = sizeInMB;
            this.isInstalled = isInstalled;
            this.description = description;
            this.downloadProgress = 0;
        }
    }
}
