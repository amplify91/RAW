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
	public void update(int speed) {
		translate(-0.1f*(float)speed, 0f);
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
	public void jump() {
		//do nothing
	}

}
