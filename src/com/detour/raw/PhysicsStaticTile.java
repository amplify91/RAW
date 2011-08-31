package com.detour.raw;

public class PhysicsStaticTile implements PhysicsComponent{
	
	private float x = 0;
	private float y = 0;
	
	float xVel; // TODO static tiles don't move and don't need velocity.
	float yVel; // Put these in different physics components
	
	public PhysicsStaticTile(){
		
	}
	
	public PhysicsStaticTile(float x, float y){
		this();
		setX(x);
		setY(y);
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

}
