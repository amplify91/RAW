package com.detour.raw;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Hero extends Sprite{
	
	Vec2 running;
	private static final float MAX_SPEED = 10;
	private static final float HEIGHT = 1;
	private static final float WIDTH = 1;
	
	public Hero(){
		super(new PhysicsHero(), new AnimationComponent());
		running = new Vec2(25, 0);
		mAnimation.setAnimation(Animation.RUNNING);
		
	}
	
	public void create(World world, float x, float y){
		mDrawWidth = WIDTH * SCALE_FACTOR; //TODO change SCALE_FACTOR without breaking Camera class.
		mDrawHeight = HEIGHT * SCALE_FACTOR;
		mDrawOffsetX = -WIDTH / 2f;
		mDrawOffsetY = -HEIGHT / 2f;
		mPhysics.create(world, x, y, WIDTH, HEIGHT, true);
		//mAnimation.setAnimation(Animation.RUNNING);
	}
	
	public void jump(){
		((PhysicsHero) mPhysics).jump();
	}
	
	public void dash(){
		((PhysicsHero) mPhysics).dash();
	}
	
}
