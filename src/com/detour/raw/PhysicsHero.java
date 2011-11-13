package com.detour.raw;

import android.util.Log;

public class PhysicsHero extends PhysicsComponent{
	
	/*private float xPrev = x;
	private float yPrev = y;
	private float slope;*/
	
	float xVel;
	float yVel;
	
	private float gravity = -0.17f;
	private float acc = 0f;
	private float jumpVel = 1.6f;
	private float termVel = -0.5f;
	
	public PhysicsHero(){
		super();
		
	}
	
	@Override
	public void update(GridCell gc, int i) {
		isColliding = false;
		for(int i2 = 0;i2<gc.getNumberOfEntities();i2++){
			if(i==gc.getEntity(i2).cellIndex){continue;}
			if(isCollidingSAT((Sprite) gc.getEntity(i2))){
				handleSATCollision(resolvingVector);
				//Log.i("YEAH!", "Collision! Collides with: "+i2);
			}/*else{
				Log.i("NONE!", "No collision with: "+i2);
			}*/
		}
		
		translate(0.2f, 0f);
		
		acc += gravity;
		if(acc<termVel){
			acc = termVel;
		}
		
		fall();
		
		if(isOnGround()){
			translate(0f, (float)(2f - y));
		}
		
		/*xVel = x - xPrev;
		yVel = y - yPrev;
		slope = yVel / xVel;
		
		xPrev = x;
		yPrev = y;*/
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
		mModel.translate(x, y);
	}
	
	@Override
	public void scale(double sx, double sy) {
		mModel.scale(sx, sy);
	}
	
	@Override
	public void jump(){
		acc += jumpVel;
		if(!isOnGround()){
			acc = jumpVel;
		}
	}
	
	private void fall(){
		translate(0f, acc);
	}
	
	private boolean isOnGround(){
		if(y>2){
			return false;
		}else{
			return true;
		}
	}
	
}
