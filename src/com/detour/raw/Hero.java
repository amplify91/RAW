package com.detour.raw;

import org.jbox2d.common.Vec2;

public class Hero extends Sprite{
	
	Vec2 running;
	private static final float MAX_SPEED = 10;
	
	public Hero(){
		
		running = new Vec2(25, 0);
		mAnimation.setAnimation(Animation.RUNNING);
		
	}
	
	public void update(){
		super.update();
		/*if(mPhysics.mBody.getLinearVelocity().x<MAX_SPEED){
			mPhysics.mBody.applyForce(running, mPhysics.mBody.getWorldCenter());
		}*/
	}
	
	public void jump(){
		mPhysics.jump();
	}
	
	public void dash(){
		mPhysics.dash();
	}
	
}
