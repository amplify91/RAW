package com.detour.raw;

public class PhysicsStaticTile implements PhysicsComponent{
	
	private float x = 0;
	private float y = 0;
	
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

}
