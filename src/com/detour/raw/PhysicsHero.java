package com.detour.raw;

public class PhysicsHero implements PhysicsComponent{
	
	private float x = 0;
	private float y = 0;
	private float xPrev = x;
	private float yPrev = y;
	private float slope;
	
	float xVel;
	float yVel;
	
	private float gravity = -0.17f;
	private float acc = 0f;
	private float jumpVel = 1.4f;
	private float termVel = -0.5f;
	
	public PhysicsHero(){
		
	}
	
	@Override
	public void update(int speed) {
		
		acc += gravity;
		if(acc<termVel){
			acc = termVel;
		}
		
		fall();
		
		if(isOnGround()){
			y = 2f;
		}
		
		xVel = x - xPrev;
		yVel = y - yPrev;
		slope = yVel / xVel;
		
		xPrev = x;
		yPrev = y;
	}
	
	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
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
	}
	
	@Override
	public void jump(){
		acc += jumpVel;
		if(acc>=jumpVel){
			acc = jumpVel;
		}
		translate(0f, acc);
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
