package com.detour.raw;

public abstract class BaseEntity {
	
	int index;
	boolean isActive = true;
	int cellx;
	int celly;
	int cellIndex; //not necessarily an actual index
	
	public abstract void draw(SpriteBatch sb);
	
	public abstract void update(CollisionGrid cg);
	
	public abstract double getX();
	
	public abstract double getY();
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int i){
		index = i;
	}
	
}
