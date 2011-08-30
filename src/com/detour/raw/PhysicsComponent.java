package com.detour.raw;

public interface PhysicsComponent {
	
	public float getX();
	
	public float getY();
	
	public void setX(float x);
	
	public void setY(float y);
	
	public void translate(float x, float y);
	
}
