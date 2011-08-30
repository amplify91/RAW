package com.detour.raw;

import android.content.Context;

public class Tile {
	
	RenderComponent render;
	PhysicsComponent physics;
	
	private static final float SCALE_FACTOR = 1f/15f;
	
	public Tile(Context context, float ratio){
		
		render = new RenderVisible(context);
		physics = new PhysicsStaticTile();
		
		scale(SCALE_FACTOR, SCALE_FACTOR);
		translate((((-0.5f/SCALE_FACTOR)*ratio)+0.5f), ((-0.5f/SCALE_FACTOR)+0.5f)); //translate to bottom left corner of screen
		
	}
	
	public void draw(){
		render.draw(physics.getX(), physics.getY());
	}
	
	public float getX(){
		return physics.getX();
	}
	
	public float getY(){
		return physics.getY();
	}
	
	public void loadGLTexture(int id) {
		render.loadGLTexture(id);
	}
	
	public void setProgram(int program){
		render.setProgram(program);
	}
	
	public void scale(float sx, float sy){
		render.scale(sx, sy);
	}
	
	public void translate(float tx, float ty){
		physics.translate(tx, ty);
	}
	
}
