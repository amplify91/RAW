package com.detour.raw;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class GameRenderer implements GLSurfaceView.Renderer{
	
	//Singleton instance
	private static GameRenderer renderer = new GameRenderer();
	
	GameManager gameManager = GameManager.getGameManager();
	
	//private static final String TAG = "GameRenderer";
	static Context mContext;
	Bitmap bitmap;
	
	private float red = 0.5f;
	private float green = 0.5f;
	private float blue = 0.5f;
	
	static float ratio;
	
	Shader shader;
	int program;
	FPSCounter fps;
	//Sprite sprite;
	//Sprite sprite2;
	int x = 0;
	int y = 0;
	int i = 0;
	
	float xUnit;
	float yUnit = (2f/15f);
	//float xOrig = -1;
	//float yOrig = -1;
	
	private float[] mViewMatrix = new float[16];
	private float[] mProjMatrix = new float[16];
	
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
	
	@Override
	public void onDrawFrame(GL10 gl) {
		
		GLES20.glClearColor(red, green, blue, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		GLES20.glUseProgram(program);
		
		gameManager.draw(mViewMatrix, mProjMatrix);
		
		/*sprite.draw(mViewMatrix, mProjMatrix);
		
		sprite2.selectFrame(i);
		if(i==7){
			i=0;
		}else{
			i++;
		}
        sprite2.draw(mViewMatrix, mProjMatrix);*/
		
		//fps.calculate();
		//fps.draw(gl);
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		GLES20.glViewport(0, 0, width, height);
		ratio = ((float)(width))/((float)(height));
		Matrix.orthoM(mProjMatrix, 0, -ratio, ratio, -1, 1, 0.5f, 10);
		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 1.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0.0f);
		//xUnit = ((2f/15f)*ratio);
		
		gameManager.loadLevel(mContext, program, 0);
		
		//temporary place for loading drawables. Change later!
		/*sprite.loadGLTexture(R.drawable.raw1, program);
		sprite.scale(0.5f, 0.5f);
		sprite.translate(-1, 0);
		sprite2.scale(0.5f, 0.5f);
		sprite2.translate(1.0f, 0.0f);
		sprite2.createAnitmationFrames(R.drawable.spritesheet1, 64, 64, program);
		sprite2.selectFrame(0);*/
		
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		/*int error = GLES20.glGetError();
		Log.d(LOG_TAG, ""+error);*/
		
		shader = new Shader(R.raw.sprite_vs, R.raw.sprite_fs, mContext);
		program = shader.getProgram();
		
		GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glClearDepthf(1.0f);
		GLES20.glDepthFunc(GLES20.GL_LEQUAL);
		GLES20.glDepthMask(true);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glCullFace(GLES20.GL_BACK);
		GLES20.glClearColor(red, green, blue, 1.0f);
		
		System.gc();
	}
	
	public static float getScreenRatio(){
		return ratio;
	}
	
}
