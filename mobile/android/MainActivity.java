package com.pranjalfreefireindia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.SurfaceView;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private PranjalGameEngine gameEngine;
    private GameSurfaceView gameSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize game engine
        gameEngine = new PranjalGameEngine(this);
        
        // Create game surface view
        gameSurfaceView = new GameSurfaceView(this, gameEngine);
        
        // Add to layout
        FrameLayout gameContainer = findViewById(R.id.game_container);
        gameContainer.addView(gameSurfaceView);
        
        // Start game engine
        if (gameEngine.initialize()) {
            gameEngine.start();
        } else {
            android.util.Log.e("PranjalFreeFire", "Failed to initialize game engine");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameSurfaceView != null) {
            gameSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameSurfaceView != null) {
            gameSurfaceView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameEngine != null) {
            gameEngine.shutdown();
        }
    }
}
