package com.detour.raw;

import android.content.Context;
import android.util.Log;

public class Sprite{
	
	RenderComponent renderable;
	PhysicsComponent physics;
	
	public static final int VERTEX_SIZE = 2 + 2;
	public static final int SPRITE_SIZE = 4 * VERTEX_SIZE;
	//TODO put these 2 fields into render components
	
	public float width;
	public float height;
	public int frame = 0;
	
	private static final float SCALE_FACTOR = 2f/15f;
	
	public Sprite(Context context){
		super();
		
		renderable = new RenderVisible(context);
		physics = new PhysicsStaticTile();
		scale(0.5f, 0.5f);
		
		width = (SCALE_FACTOR);
		height = (SCALE_FACTOR);
		
	}
	
	public void draw(){
		renderable.draw(physics.getX(), physics.getY());
	}
	
	public void draw(SpriteBatch sb, Texture t){
		sb.draw(t, frame, getX(), getY(), width, height);
	}
	
	public float getX(){
		return physics.getX();
	}
	
	public float getY(){
		return physics.getY();
	}
	
	public void loadGLTexture(int id) {
		Log.d("Sprite", "TODO!!!"); //TODO get rid of this and load tex coord from a texture atlas
		renderable.loadGLTexture(id);
	}
	
	public void createAnitmationFrames(int id, int frameWidth, int frameHeight, int program){
		renderable.createAnitmationFrames(id, frameWidth, frameHeight, program);
	}
	
	public void setFrame(int frame){
		this.frame = frame;
	}
	
	public void scale(float sx, float sy){
		width *= sx;
		height *= sy;
	}
	
	public void translate(float tx, float ty){
		physics.translate(tx, ty);
	}
	
	public void setProgram(int program){
		renderable.setProgram(program);
	}
	
}
