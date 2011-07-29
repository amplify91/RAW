package com.detour.raw;

import android.content.Context;

public class Sprite {
	
	Renderable renderable;
	
	public Sprite(Context context){
		
		renderable = new RenderVisible(context);
		
	}
	
	public void draw(float[] view, float[] proj){
		
		renderable.draw(view, proj);
	}
	
	public void loadGLTexture(int id, int program) {
		renderable.loadGLTexture(id, program);
	}
	
	public void scale(float sx, float sy){
		renderable.scale(sx, sy);
	}
	
	public void translate(float tx, float ty){
		renderable.translate(tx, ty);
	}
	
}
