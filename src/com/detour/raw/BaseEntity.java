package com.detour.raw;

public abstract class BaseEntity {
	
	int index;
	boolean isActive = true;
	int cellx;
	int celly;
	int cellIndex;
	
	public abstract void update();
	
	public abstract float getX();
	
	public abstract float getY();
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int i){
		index = i;
	}
	
	public int getCellIndex(){
		return cellIndex;
	}
	
	public void setCellIndex(int i){
		cellIndex = i;
	}
	
}
