package com.detour.raw;


public interface Renderable {
	
	void loadGLTexture(int id, int program);
	
	void draw(float[] view, float[] proj);
	
	void scale(float sx, float sy);
	
	void translate(float tx, float ty);
	
}
