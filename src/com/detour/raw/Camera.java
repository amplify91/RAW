package com.detour.raw;

import android.opengl.Matrix;
import android.util.Log;

public class Camera {
	
	private static float mRatio = 0;
	
	private float[] mViewMatrix = new float[16];
	private float[] mProjMatrix = new float[16];
	
	private float[] mModelMatrix = new float[16];
	private float[] mMVMatrix = new float[16];
	private static float[] mMVPMatrix = new float[16];
	
	public static final float HERO_X = 2f;
	public static final float HERO_Y = 2f;
	
	public static final String TAG = "Camera";
	
	public Camera(){
		
	}
	
	public void initialize(){
		if(mRatio==0){
			mRatio = 1;
			createMatrices();
		}else{
			Log.d(TAG, "Already initialized.");
		}
	}
	
	public void initialize(float ratio){
		if(mRatio==0){
			mRatio = ratio;
			createMatrices();
		}else{
			Log.d(TAG, "Already initialized.");
		}
	}
	
	public void initialize(float width, float height){
		if(mRatio==0){
			mRatio = width / height;
			createMatrices();
		}else{
			Log.d(TAG, "Already initialized.");
		}
	}
	
	public void createMatrices(){
		if(mRatio!=0){
			Matrix.orthoM(mProjMatrix, 0, -mRatio, mRatio, -1, 1, 0.5f, 10);
			Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 1.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0.0f);
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.multiplyMM(mMVMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
	        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVMatrix, 0);
		}else{
			Log.d(TAG, "Screen ratio not set.");
		}
	}
	
	public void update(float x, float y){
		//Move camera according to where Hero is.
		
		x = Sprite.SCALE_FACTOR * (x - HERO_X);
		y = Sprite.SCALE_FACTOR * (y - HERO_Y);
		/*if(y1<1){
			y1 = 0;
		}*/
		
		Matrix.setIdentityM(mViewMatrix, 0);
		Matrix.setIdentityM(mMVMatrix, 0);
		Matrix.setIdentityM(mMVPMatrix, 0);
		
		Matrix.setLookAtM(mViewMatrix, 0, x, y, 1.0f, x, y, 0f, 0f, 1.0f, 0.0f);
		
		Matrix.multiplyMM(mMVMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVMatrix, 0);
	}
	
	public static float getScreenRatio(){
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
	
	public static float[] getMVPMatrix(){
		return mMVPMatrix;
	}
	
}
