package com.detour.raw;

import org.jbox2d.common.Vec2;

public class Hero extends Sprite{
	
	Vec2 running;
	private static final float MAX_SPEED = 10;
	
	public Hero(){
		super(new PhysicsHero(), new AnimationComponent());
		running = new Vec2(25, 0);
		mAnimation.setAnimation(Animation.RUNNING);
		
	}
	
	public void jump(){
		((PhysicsHero) mPhysics).jump();
	}
	
	public void dash(){
		((PhysicsHero) mPhysics).dash();
	}
	
}
