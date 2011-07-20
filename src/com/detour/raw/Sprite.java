package com.detour.raw;

import android.content.Context;

public class Sprite {
	
	Renderable renderable;
	
	public Sprite(Context context){
		
		renderable = new RenderVisible(context);
		
	}
	
	public void draw(){
		renderable.draw();
	}
	
	public void loadGLTexture(int id) {
		renderable.loadGLTexture(id);
	}
	
}
