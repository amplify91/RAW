package com.detour.raw;

public class Tile extends Sprite{
	
	public Tile(int frame, float x, float y){
		super(new PhysicsStaticTile());
		
		setFrame(frame);
		pauseAnimation();
		
		translate(x,y);
	}
	
}