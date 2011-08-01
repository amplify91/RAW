package com.detour.raw;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class GameRenderer implements GLSurfaceView.Renderer{
	
	//private static final String TAG = "GameRenderer";
	Context mContext;
	Bitmap bitmap;
	
	private float red = 0.5f;
	private float green = 0.5f;
	private float blue = 0.5f;
	
	Shader shader;
	int program;
	FPSCounter fps;
	Sprite sprite;
	Sprite sprite2;
	int x = 0;
	
	private float[] mViewMatrix = new float[16];
	private float[] mProjMatrix = new float[16];
	
	public GameRenderer(Context context){
		mContext = context;
		
		//create objects/sprites
		sprite = new Sprite(mContext);
		sprite2 = new Sprite(mContext);
		fps = new FPSCounter();
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		
		GLES20.glClearColor(red, green, blue, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		GLES20.glUseProgram(program);
		
		sprite.scale(0.5f, 0.5f);
		sprite.translate(-1, 0);
		sprite.draw(mViewMatrix, mProjMatrix);
		
		sprite2.scale(0.5f, 0.5f);
		sprite2.translate(1.0f, 0.0f);
		if(x==8){
			x = 0;
		}
		sprite2.selectFrame(x);
		x++;
        sprite2.draw(mViewMatrix, mProjMatrix);
		
		//fps.calculate();
		//fps.draw(gl);
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		GLES20.glViewport(0, 0, width, height);
		float ratio = ((float)(width))/((float)(height));
		Matrix.orthoM(mProjMatrix, 0, -ratio, ratio, -1, 1, 0.5f, 10);
		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 1.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0.0f);
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
		GLES20.glClearDepthf(1.0f);
		GLES20.glDepthFunc(GLES20.GL_LEQUAL);
		GLES20.glDepthMask(true);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glCullFace(GLES20.GL_BACK);
		GLES20.glClearColor(red, green, blue, 1.0f);
		
		//load sprite/object textures (preferably loop through an array of all sprites).
		sprite.loadGLTexture(R.drawable.raw1, program);
		sprite2.createAnitmationFrames(R.drawable.spritesheet1, 64, 64, program);
		sprite2.selectFrame(0);
		
		System.gc();
	}
	
}
