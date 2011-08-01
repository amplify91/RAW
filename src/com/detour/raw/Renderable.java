package com.detour.raw;


public interface Renderable {
	
	void loadGLTexture(int id, int program);
	
	void draw(float[] view, float[] proj);
	
	void createAnitmationFrames(int id, int frameWidth, int frameHeight, int program);
	
	void selectFrame(int frameIndex);
	
	void scale(float sx, float sy);
	
	void translate(float tx, float ty);
	
}
