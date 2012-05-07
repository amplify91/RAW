package com.detour.raw;

import org.jbox2d.dynamics.World;

public abstract class ReusableSprite extends Sprite implements IReusable{
	
	public boolean isActive = false;
	public boolean isReadyForSpawn = false;
	
	public ReusableSprite(PhysicsComponent p, AnimationComponent a) {
		super(p, a);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void spawn(World world);
	
	public abstract void recycle();
	
}
