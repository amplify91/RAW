package com.detour.raw;

import android.content.Context;
import android.util.Log;

public class Sprite {
	
	RenderComponent renderable;
	PhysicsComponent physics;
	
	float x;
	float y;
	float xVel;
	float yVel;
	
	public Sprite(Context context){
		
		renderable = new RenderVisible(context);
		physics = new PhysicsStaticTile();
		scale(0.5f, 0.5f);
		
	}
	
	public void draw(){
		renderable.draw(physics.getX(), physics.getY());
	}
	
	public void loadGLTexture(int id) {
		Log.d("Sprite", "TODO!!!"); //TODO
		renderable.loadGLTexture(id);
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
		physics.translate(tx, ty);
	}
	
	public void setProgram(int program){
		renderable.setProgram(program);
	}
	
}
