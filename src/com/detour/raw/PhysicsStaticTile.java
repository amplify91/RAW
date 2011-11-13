package com.detour.raw;

public class PhysicsStaticTile extends PhysicsComponent{
	
	public PhysicsStaticTile(){
		super();
	}
	
	public PhysicsStaticTile(float x, float y){
		this();
		setX(x);
		setY(y);
	}
	
	@Override
	public void update(GridCell gc, int i) {
		//translate(-0.1f*(float)speed, 0f);
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
	public void jump() {
		//do nothing
	}

}
