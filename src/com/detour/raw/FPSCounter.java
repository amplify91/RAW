package com.detour.raw;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class FPSCounter extends Sprite{
	
	private long mLastTime;
    private long frameSampleTime = 0;
    private int frameSamplesCollected = 0;
    private int avg_samples;
    private float fps = 0; //would be fps if not for second average.
    public static int avg_fps = 0;
    private float avg_fps_sample = 0;
	
    public static Bitmap bitmap;
    public static Canvas canvas;
	Paint paint;
	
	public FPSCounter(){
		bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
		canvas = new Canvas(bitmap);
		paint = new Paint();
		
		//bitmap.eraseColor(Color.BLUE);
		paint.setTextSize(32);
		paint.setARGB(0xff, 0xff, 0x00, 0x00);
	}
	
	public void calculate(){
		
		long now = System.nanoTime();
		if (mLastTime != 0){
            int time = (int) (now - mLastTime);
            frameSampleTime += time;
            frameSamplesCollected++;
            if (frameSamplesCollected == 10) {
                fps = (10000000000f / frameSampleTime);
                //Log.i("FPS", ""+(int)fps);
                avg_fps_sample += fps;
                avg_samples++;
                frameSampleTime = 0;
                frameSamplesCollected = 0;
            }
        }
        mLastTime = now;
        
        if(avg_samples == 10){
        	avg_fps = (int)(avg_fps_sample/10);
        	setCountBitmap(avg_fps);
        	avg_fps_sample = 0;
        	avg_samples = 0;
        }
		
	}
	
	private void setCountBitmap(int f){
		//TODO why don't these canvas calls work?
		//canvas.drawColor(0); //transparent
		//canvas.drawText("FPS: "+f, 0, 0, paint);
		Log.i("FPS", ""+f);
	}
	
	public void draw(GL10 gl){
		
		super.loadGLTexture(gl, bitmap);
		super.draw(gl);
	}
	
}
