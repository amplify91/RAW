package com.detour.raw;

import android.opengl.Matrix;
import android.util.Log;

public class Camera {
	
	private float mRatio = 0;
	private float[] mViewMatrix = new float[16];
	private float[] mProjMatrix = new float[16];
	
	public static final String TAG = "Camera";
	
	public Camera(){
		this(1f);
	}
	
	public Camera(float ratio){
		mRatio = ratio;
		createMatrices();
	}
	
	public Camera(float width, float height){
		setScreenRation(width, height);
		createMatrices();
	}
	
	private void createMatrices(){
		if(mRatio!=0){
			Matrix.orthoM(mProjMatrix, 0, -mRatio, mRatio, -1, 1, 0.5f, 10);
			Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 1.0f, 0.0f, 0f, 0f, 0f, 1.0f, 0.0f);
		}else{
			Log.d(TAG, "Screen ratio not set.");
		}
	}
	
	public void setMatrices(float[] view, float[] proj){
		mViewMatrix = view;
		mProjMatrix = proj;
	}
	
	public void setScreenRatio(float ratio){
		mRatio = ratio;
	}
	
	public void setScreenRation(float width, float height){
		mRatio = width / height;
	}
	
}
