package com.detour.raw;

public interface RenderComponent {
	
	void loadGLTexture(int id);
	
	void draw(float x, float y);
	
	void createAnitmationFrames(int id, int frameWidth, int frameHeight, int program);
	
	void selectFrame(int frameIndex);
	
	void scale(float sx, float sy);
	
	void translate(float tx, float ty);
	
	void setProgram(int program);
	
}
