package com.detour.raw;

import android.content.Context;

public class Tile {
	
	RenderComponent render;
	PhysicsComponent physics;
	
	public float width;
	public float height;
	public int frame = 0;
	
	private static final float SCALE_FACTOR = 2f/15f;
	
	public Tile(Context context, float ratio){
		
		//render = new RenderVisible(context);
		physics = new PhysicsStaticTile();
		
		width = (SCALE_FACTOR);
		height = (SCALE_FACTOR);
		
		//scale(SCALE_FACTOR, SCALE_FACTOR);
		//translate((((-0.5f/SCALE_FACTOR)*ratio)+0.5f), ((-0.5f/SCALE_FACTOR)+0.5f)); //translate to bottom left corner of screen
		
	}
	
	public void setFrame(int frame){
		this.frame = frame;
	}
	
	/*public void draw(){
		render.draw(physics.getX(), physics.getY());
	}*/
	
	public void draw(SpriteBatch sb, Texture t){
		sb.draw(t, frame, getX(), getY(), width, height);
	}
	
	public void update(int speed){
		physics.update(speed);
	}
	
	public float getX(){
		return physics.getX();
	}
	
	public float getY(){
		return physics.getY();
	}
	
	/*public void loadGLTexture(int id) {
		render.loadGLTexture(id);
	}
	
	public void setProgram(int program){
		render.setProgram(program);
	}*/
	
	public void scale(float sx, float sy){
		width *= sx;
		height *= sy;
	}
	
	public void translate(float tx, float ty){
		physics.translate(tx, ty);
	}
	
}
