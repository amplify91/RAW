package com.detour.raw;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class GameRenderer implements GLSurfaceView.Renderer{
	
	private static final String LOG_TAG = GameRenderer.class.getSimpleName();
	
	private float red = 1.0f;
	private float green = 1.0f;
	private float blue = 1.0f;

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClearColor(red, green, blue, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, width, height);
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}
	
}
