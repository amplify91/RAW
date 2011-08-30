package com.detour.raw;

import android.opengl.GLES20;

public class SpriteBatch {
	
	private float[] vertices;
	
	private boolean drawing = false;
	private int i = 0;
	
	public SpriteBatch(int size){
		
		vertices = new float[size * Sprite.SPRITE_SIZE];
		
	}
	
	public void begin(){
		if(drawing){
			throw new IllegalStateException("Already drawing. Call end() first.");
		}
		drawing = true;
		
		i = 0;
	}
	
	public void draw(float x, float y){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		
		vertices[i++] = x;
		vertices[i++] = y;
	}
	
	public void end(){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		drawing = false;
		
		//TODO add shader and other ogles20 stuff. not necessarily right here.
		//render - 
		//GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, vertices);
		//GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length);
		
	}
	
}
