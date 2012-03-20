package com.detour.raw;

public class Tile extends Sprite{
	
	public Tile(int frame){
		super(new PhysicsStatic(), new AnimationComponent());
		
		mAnimation.setFrame(frame);
		pauseAnimation();
	}
	
}