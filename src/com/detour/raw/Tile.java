package com.detour.raw;

import android.content.Context;

public class Tile {
	
	Renderable renderable;
	
	float x;
	float y;
	private static final float SCALE_FACTOR = 1f/15f;
	
	public Tile(Context context, float ratio){
		
		renderable = new RenderVisible(context);
		
		scale(SCALE_FACTOR, SCALE_FACTOR);
		translate((((-0.5f/SCALE_FACTOR)*ratio)+0.5f), ((-0.5f/SCALE_FACTOR)+0.5f)); //translate to bottom left corner of screen
		
	}
	
	public void draw(float[] view, float[] proj){
		
		renderable.draw(view, proj);
	}
	
	public void loadGLTexture(int id) {
		renderable.loadGLTexture(id);
	}
	
	public void setProgram(int program){
		renderable.setProgram(program);
	}
	
	public void scale(float sx, float sy){
		renderable.scale(sx, sy);
	}
	
	public void translate(float tx, float ty){
		renderable.translate(tx, ty);
	}
	
}
