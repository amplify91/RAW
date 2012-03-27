package com.detour.raw;

import android.opengl.Matrix;
import android.util.Log;

public class Camera {
	
	private float mRatio = 0;
	private float cameraX = 0;
	private float cameraY = 0;
	
	private float[] mViewMatrix = new float[16];
	private float[] mProjMatrix = new float[16];
	
	private float[] mModelMatrix = new float[16];
	private float[] mMVMatrix = new float[16];
	private static float[] mMVPMatrix = new float[16];
	
	public static final float HERO_X = 2f;//TODO make these two relative to the ground,
	public static final float HERO_Y = 2f;//instead of world coordinates.
	public static final float HERO_X_MAX = HERO_X;
	public static final float HERO_Y_MAX = 6f;
	public static final float CAMERA_OFFSET_X = -4f * Sprite.SCALE_FACTOR;
	public static final float CAMERA_OFFSET_Y = -2f * Sprite.SCALE_FACTOR;
	
	public static final String TAG = "Camera";
	
	public Camera(){
		
	}
	
	public void initialize(){
		mRatio = 1;
		createMatrices();
	}
	
	public void initialize(float ratio){
		mRatio = ratio;
		createMatrices();
	}
	
	public void initialize(float width, float height){
		mRatio = width / height;
		createMatrices();
	}
	
	public void createMatrices(){
		if(mRatio!=0){
			Matrix.orthoM(mProjMatrix, 0, -mRatio, /*mRatio*/0, -1, /*1*/0, 0.5f, 10);
			Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 1.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0.0f);
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.multiplyMM(mMVMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
	        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVMatrix, 0);
		}else{
			Log.d(TAG, "Screen ratio not set.");
		}
	}
	
	public void update(float heroX, float heroY){
		//Move camera according to where Hero is.
		
		if(heroX>HERO_X_MAX){
			heroX -= HERO_X_MAX - HERO_X;
		}else if(heroX>HERO_X && heroX<=HERO_X_MAX){
			heroX = HERO_X;
		}
		if(heroY>HERO_Y_MAX){
			heroY -= HERO_Y_MAX - HERO_Y;
		}else if(heroY>HERO_Y && heroY<=HERO_Y_MAX){
			heroY = HERO_Y;
		}
		
		cameraX = (heroX * Sprite.SCALE_FACTOR) + CAMERA_OFFSET_X;
		cameraY = (heroY * Sprite.SCALE_FACTOR) + CAMERA_OFFSET_Y;
		
		Matrix.setLookAtM(mViewMatrix, 0, cameraX, cameraY, 1.0f, cameraX, cameraY, 0f, 0f, 1.0f, 0.0f);
		
		Matrix.multiplyMM(mMVMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVMatrix, 0);
	}
	
	public float getCameraX(){
		return cameraX;
	}
	
	public float getCameraY(){
		return cameraY;
	}
	
	public float getOriginX(){
		return (cameraX)/Sprite.SCALE_FACTOR;
	}
	
	public float getOriginY(){
		return cameraY;
	}
	
	public float getScreenRatio(){
		return mRatio;
	}
	
	public void setScreenRatio(float ratio){
		mRatio = ratio;
	}
	
	public void setScreenRatio(float width, float height){
		mRatio = width / height;
	}
	
	public void setMatrices(float[] view, float[] proj){
		mViewMatrix = view;
		mProjMatrix = proj;
	}
	
	public float[] getViewMatrix(){
		return mViewMatrix;
	}
	
	public float[] getProjMatrix(){
		return mProjMatrix;
	}
	
	public void setViewMatrix(float[] view){
		mViewMatrix = view;
	}
	
	public void setProjMatrix(float[] proj){
		mProjMatrix = proj;
	}
	
	public float[] getMVPMatrix(){
		return mMVPMatrix;
	}
	
}
