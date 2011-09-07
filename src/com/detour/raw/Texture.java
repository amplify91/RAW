package com.detour.raw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class Texture{
	
	private Bitmap mBitmap;
	private int numberOfFrames;
	private int framesWide;
	private int framesHigh;
	
	private float[][] frames;
	private int[] tex = new int[1];
	
	public Texture(Context context, int id){
		this(context, id, 1, 1);
	}
	
	public Texture(Context context, int id, int framesHorizontal, int framesVertical){
		if(id!=0){
			Options opts = new Options();
			opts.inScaled = false; //Trying to not allow Android to do its stupid screen density pixel fucking.
			mBitmap = BitmapFactory.decodeResource(context.getResources(), id, opts);
		}
		numberOfFrames = framesHorizontal * framesVertical;
		framesWide = framesHorizontal;
		framesHigh = framesVertical;
		frames = createFrames();
	}
	
	public int getWidth(){
		return mBitmap.getWidth();
	}
	
	public int getHeight(){
		return mBitmap.getHeight();
	}
	
	public Bitmap getBitmap(){
		return mBitmap;
	}
	
	public int getNumberOfFrames(){
		return numberOfFrames;
	}
	
	public float[] getFrameUV(int frame){
		//Frame numbers start at 1.
		return frames[frame-1];
	}
	
	private float[][] createFrames(){
		float[][] f = new float[numberOfFrames][4];
		
		for(int i=0;i<numberOfFrames;i++){
			f[i] = createFrameUV(i);
		}
		
		return f;
	}
	
	private float[] createFrameUV(int frame){
		//TODO Check this method. Make sure values are correct for multiple frames.
		
		float[] uv = new float[4];
		
		if(frame>=numberOfFrames || frame<0){
			Log.d("", "Invalid frame number.");
		}
		while(frame>=numberOfFrames){
			frame -= numberOfFrames;
		}
		
		if(numberOfFrames>1){
			float width = ((float)mBitmap.getWidth() / (float)framesWide) / (float)mBitmap.getWidth();
			float height = ((float)mBitmap.getHeight() / (float)framesHigh) / (float)mBitmap.getHeight();
			float u = 0;
			float v = 0;
			if(frame<framesWide){
				u = (float)frame * width;
				v = 1f;
			}else{
				u = (float)(frame%framesWide) * width;
				v = 1f - (float)(frame/framesWide) * height;
			}
			
			uv[0] = u;
			uv[1] = v;
			uv[2] = u + width;
			uv[3] = v + height;
		}else{
			uv[0] = 0f;
			uv[1] = 0f;
			uv[2] = 1f;
			uv[3] = 1f;
		}
		
		return uv;
	}
	
	public void bind(){
		
		GLES20.glGenTextures(1, tex, 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex[0]);
		GLES20.glDeleteTextures(1, tex, 0);
		
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
	}
	
}
