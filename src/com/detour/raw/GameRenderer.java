package com.detour.raw;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class GameRenderer implements GLSurfaceView.Renderer{
	
	//Singleton instance
	private static GameRenderer renderer = new GameRenderer();
	
	GameManager gameManager = GameManager.getGameManager();
	
	private static final String TAG = "GameRenderer";
	static Context mContext;
	Bitmap bitmap;
	
	private float red = 0.5f;
	private float green = 0.5f;
	private float blue = 0.5f;
	
	static float ratio;
	
	FPSCounter fps = new FPSCounter();
	
	private static float[] mViewMatrix = new float[16];
	private static float[] mProjMatrix = new float[16];
	
	private GameRenderer(){
		
	}
	
	/*private GameRenderer(Context context){
		mContext = context;
		
		//create objects/sprites
		//sprite = new Sprite(mContext);
		//sprite2 = new Sprite(mContext);
		//fps = new FPSCounter();
	}*/
	
	public static GameRenderer getGameRenderer(Context c){
		mContext = c;
		if(renderer == null){
			renderer = new GameRenderer();
		}
		return renderer;
	}
	
	public static GameRenderer getGameRenderer(){
		if(mContext==null){
			Log.d("GameRenderer", "null context. Use other singleton getter method.");
		}
		if(renderer == null){
			renderer = new GameRenderer();
		}
		return renderer;
	}
	
	public void onDrawFrame(GL10 gl) {
		
		GLES20.glClearColor(red, green, blue, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		//Log.i(TAG, "" + GLES20.glGetError());
		gameManager.draw();
		//Log.i(TAG, "" + GLES20.glGetError());
		fps.calculate();
		
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		GLES20.glViewport(0, 0, width, height);
		gameManager.getCamera().initialize(width, height);
		gameManager.loadLevel(mContext, 1);//TODO change this to set game_is_running in game loop to true, then load level in game manager or somewhere else.
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		Log.i(TAG, "" + GLES20.glGetError());
		
		//GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glClearDepthf(1.0f);
		GLES20.glDepthFunc(GLES20.GL_LEQUAL);
		GLES20.glDepthMask(true);
		GLES20.glDisable(GLES20.GL_DITHER);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glCullFace(GLES20.GL_BACK);
		GLES20.glClearColor(red, green, blue, 1.0f);
		
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		
		Log.i(TAG, "" + GLES20.glGetError());
		System.gc();
	}
	
	/*public static float getScreenRatio(){
		return ratio;
	}
	
	public static float[] getViewMatrix(){
		return mViewMatrix;
	}
	
	public static float[] getProjMatrix(){
		return mProjMatrix;
	}*/
	
	public void setViewMatrix(float[] view){
		mViewMatrix = view;
	}
	
	public void setProjMatrix(float[] proj){
		mProjMatrix = proj;
	}
	
}
