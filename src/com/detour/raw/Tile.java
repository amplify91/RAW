package com.detour.raw;

import android.content.Context;

public class Tile extends Sprite{
	
	public Tile(int frame, float x, float y){
		super(new PhysicsStaticTile());
		
		setFrame(frame);
		pauseAnimation();
		
		translate(x,y);
	}
	
}