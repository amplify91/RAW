package com.detour.raw;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameView extends GLSurfaceView{
	
	Input mInput;
	private static final String LOG_TAG = GameView.class.getSimpleName();

	public GameView(Context context, Input input) {
		super(context);
		mInput = input;
		setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
		setKeepScreenOn(true);
		setEGLContextClientVersion(2);
		setRenderer(GameRenderer.getGameRenderer(context));
		setRenderMode(RENDERMODE_WHEN_DIRTY);
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
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(holder);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		return mInput.onTouchInput(event);
	}

}
