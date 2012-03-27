package com.detour.raw;

import org.jbox2d.common.Vec2;

public class PhysicsHero extends PhysicsComponent{
	
	private Vec2 mVel = new Vec2(); // Current velocity.
	private Vec2 mRunVel = new Vec2(); // Impulse velocity that needs to be applied to run at correct speed
	private Vec2 mJumpVel = new Vec2(0, 10);
	private Vec2 mDashVel = new Vec2(20, 0);
	private Vec2 mTotalVel = new Vec2();
	
	private float velChange;
	private float runImpulse;
	
	private static final float MAX_SPEED = 5f;
	
	public PhysicsHero(){
		
	}

	@Override
	public void update() {
		mBody.setLinearVelocity(mBody.getLinearVelocity().mul(0.9f)); // Slow down, in case we're above max speed.
		run();
		//getInput();
		mBody.applyLinearImpulse(mTotalVel, mBody.getWorldCenter());
		mTotalVel.setZero();
	}
	
	private void run(){
		mRunVel.setZero();
		mVel = mBody.getLinearVelocity();
		velChange = MAX_SPEED - mVel.x;
	    runImpulse = mBody.getMass() * velChange;
	    if(runImpulse>0){
	    	mRunVel.set(runImpulse, 0);
	    }
	    mTotalVel = mTotalVel.add(mRunVel);
	}
	
	public void jump(){
		mTotalVel = mTotalVel.add(mJumpVel);
	}
	
	public void dash(){
		mTotalVel = mTotalVel.add(mDashVel);
	}
	
}
