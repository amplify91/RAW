package com.detour.raw;

import android.content.Context;
import android.util.Log;

public class Sprite {
	
	Renderable renderable;
	
	float x;
	float y;
	float xVel;
	float yVel;
	
	public Sprite(Context context){
		
		renderable = new RenderVisible(context);
		
	}
	
	public void draw(float[] view, float[] proj){
		
		renderable.draw(view, proj);
	}
	
	public void loadGLTexture(int id, int program) {
		Log.d("Sprite", "TODO!!!"); //TODO
		//renderable.loadGLTexture(id, program);
	}
	
	public void createAnitmationFrames(int id, int frameWidth, int frameHeight, int program){
		renderable.createAnitmationFrames(id, frameWidth, frameHeight, program);
	}
	
	public void selectFrame(int frameIndex){
		renderable.selectFrame(frameIndex);
	}
	
	public void scale(float sx, float sy){
		renderable.scale(sx, sy);
	}
	
	public void translate(float tx, float ty){
		renderable.translate(tx, ty);
	}
	
}
