package com.detour.raw;


public interface Renderable {
	
	void loadGLTexture(int id, int program);
	
	void draw();
	
}
