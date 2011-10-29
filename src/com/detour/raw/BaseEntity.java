package com.detour.raw;

public abstract class BaseEntity {
	
	int index;
	boolean isActive = true;
	int cellx;
	int celly;
	
	public abstract void draw(SpriteBatch sb, Texture t);
	
	public abstract void update();
	
	public abstract float getX();
	
	public abstract float getY();
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int i){
		index = i;
	}
	
}
