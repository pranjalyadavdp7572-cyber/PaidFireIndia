package com.pranjalfreefireindia;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameSurfaceView extends GLSurfaceView {

    private PranjalGameEngine gameEngine;
    private GameRenderer gameRenderer;
    private InputHandler inputHandler;
    private ConcurrentLinkedQueue<MotionEvent> touchEvents;

    public GameSurfaceView(Context context, PranjalGameEngine engine) {
        super(context);
        this.gameEngine = engine;
        this.touchEvents = new ConcurrentLinkedQueue<>();

        // Set OpenGL ES version
        setEGLContextClientVersion(3);

        // Create renderer
        gameRenderer = new GameRenderer(gameEngine);
        setRenderer(gameRenderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);

        // Initialize input handler
        inputHandler = new InputHandler();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchEvents.offer(event);
        return inputHandler.handleTouchEvent(event);
    }

    public ConcurrentLinkedQueue<MotionEvent> getTouchEvents() {
        return touchEvents;
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
