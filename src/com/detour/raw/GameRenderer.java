package com.detour.raw;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class GameRenderer implements Renderer{
	
	private static final String LOG_TAG = GameRenderer.class.getSimpleName();
	Context mContext;
	Bitmap bitmap;
	
	private float red = 1.0f;
	private float green = 1.0f;
	private float blue = 1.0f;
	
	Sprite sprite;
	Sprite sprite2;
	int x = 0;
	
	public GameRenderer(Context context){
		mContext = context;
		sprite = new Sprite();
		sprite2 = new Sprite();
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		
		//gl.glClearColor(red, green, blue, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -4);
		gl.glPushMatrix();
		gl.glScalef(0.5f, 0.5f, 1.0f);
		gl.glTranslatef(-4, -1.5f, 0);
		if(x%2==0){
			sprite.draw(gl);
		}else{
			sprite2.draw(gl);
		}
		x++;
		gl.glPopMatrix();
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		// Set the background color to black.
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		sprite.loadGLTexture(gl, mContext, R.drawable.raw1);
		sprite2.loadGLTexture(gl, mContext, R.drawable.raw2);
		
	}
	
}
