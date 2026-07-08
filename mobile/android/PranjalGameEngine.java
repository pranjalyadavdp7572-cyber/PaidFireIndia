package com.pranjalfreefireindia;

import android.content.Context;
import android.opengl.GLSurfaceView;
import java.util.concurrent.atomic.AtomicBoolean;

public class PranjalGameEngine {

    private Context context;
    private AtomicBoolean isRunning;
    private GLSurfaceView renderSurface;
    private GameThread gameThread;
    private long lastFrameTime;

    // Native engine reference
    static {
        System.loadLibrary("pranjal_engine");
    }

    public PranjalGameEngine(Context ctx) {
        this.context = ctx;
        this.isRunning = new AtomicBoolean(false);
        this.lastFrameTime = System.currentTimeMillis();
    }

    public boolean initialize() {
        try {
            // Initialize native engine
            boolean success = nativeInitialize(context);
            if (success) {
                android.util.Log.i("PranjalFreeFire", "Engine initialized successfully");
                return true;
            }
        } catch (Exception e) {
            android.util.Log.e("PranjalFreeFire", "Engine initialization failed: " + e.getMessage());
        }
        return false;
    }

    public void start() {
        if (!isRunning.getAndSet(true)) {
            gameThread = new GameThread(this);
            gameThread.start();
        }
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastFrameTime) / 1000.0f;
        lastFrameTime = currentTime;

        if (isRunning.get()) {
            nativeUpdate(deltaTime);
        }
    }

    public void render() {
        if (isRunning.get()) {
            nativeRender();
        }
    }

    public void shutdown() {
        isRunning.set(false);
        nativeShutdown();
        if (gameThread != null) {
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                android.util.Log.e("PranjalFreeFire", "Error joining game thread: " + e.getMessage());
            }
        }
    }

    // Native methods
    private native boolean nativeInitialize(Context context);
    private native void nativeUpdate(float deltaTime);
    private native void nativeRender();
    private native void nativeShutdown();

    public boolean isRunning() {
        return isRunning.get();
    }
}
