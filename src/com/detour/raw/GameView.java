package com.detour.raw;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;

public class GameView extends GLSurfaceView{
	
	private static final String LOG_TAG = GameView.class.getSimpleName();
    private GameRenderer renderer;
    private GameLoopThread thread;

	public GameView(Context context) {
		super(context);
		setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
		setKeepScreenOn(true);
		setEGLContextClientVersion(2);
		renderer = GameRenderer.getGameRenderer(context);
		setRenderer(renderer);
		setRenderMode(RENDERMODE_WHEN_DIRTY);
		thread = new GameLoopThread(this);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// TODO Auto-generated method stub
		super.surfaceChanged(holder, format, w, h);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceCreated(holder);
		thread.setGameRunning(true);
		thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(holder);
		thread.setGameRunning(false);
		boolean retry = true;
		while (retry){
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
	}

}
