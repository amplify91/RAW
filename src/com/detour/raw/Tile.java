package com.detour.raw;

public class Tile extends Sprite{
	
	public Tile(int frame){
		super();
		
		mAnimation.setFrame(frame);
		pauseAnimation();
	}
	
}