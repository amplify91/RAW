package com.detour.raw;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView;

public class GameRenderer implements GLSurfaceView.Renderer{
	
	private static final String LOG_TAG = GameRenderer.class.getSimpleName();
	Context mContext;
	Bitmap bitmap;
	
	private float red = 0.0f;
	private float green = 0.0f;
	private float blue = 0.0f;
	
	Shader shader;
	FPSCounter fps;
	Sprite sprite;
	Sprite sprite2;
	int x = 0;
	private float[] mProjMatrix = new float[16];
	private float[] mVMatrix = new float[16];
	
	//int[] vertexShader;
	//int[] fragmentShader;
	int program;
	String vShaderSource = "";
	String fShaderSource = "";
	
	
	public GameRenderer(Context context){
		mContext = context;
		
		//vertexShader = new int[1]; //for using shaders written in resource files.
		//fragmentShader = new int[1];
		
		//create objects/sprites
		sprite = new Sprite();
		sprite2 = new Sprite();
		fps = new FPSCounter();
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		
		GLES20.glClearColor(red, green, blue, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		if(x>3){
			x=0;
		}
		if(x%2==0){
			sprite.draw(gl);
		}else{
			sprite2.draw(gl);
		}
		x++;
		
		fps.calculate();
		//fps.draw(gl);
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		GLES20.glViewport(0, 0, width, height);
		float ratio = (float)(width/height);
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 0.5f, 10);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
		GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glClearDepthf(1.0f);
		GLES20.glDepthFunc(GLES20.GL_LEQUAL);
		GLES20.glDepthMask(true);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glCullFace(GLES20.GL_BACK);
		GLES20.glClearColor(red, green, blue, 1.0f);
		
		shader = new Shader(vShaderSource, fShaderSource);
		program = shader.getProgram();
		GLES20.glUseProgram(program);
		
		//load sprite textures (preferably loop through an array of all sprites).
		sprite.loadGLTexture(gl, mContext, R.drawable.raw1);
		sprite2.loadGLTexture(gl, mContext, R.drawable.raw2);
		
		//Matrix.setLookAtM(mVMatrix, 0, 0, 0, -5.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0.0f);
		
		System.gc();
	}
	
}
