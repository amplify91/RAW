package com.detour.raw;

import java.util.*;

public class GridCell {
	
	ArrayList<BaseEntity> list = new ArrayList<BaseEntity>();
	int x;
	int y;
	
	public GridCell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void add(BaseEntity b){
		list.add(b);
		b.setCellIndex(list.size()-1);
		b.cellx = x;
		b.celly = y;
	}
	
	public void remove(int index){
		list.remove(index);
	}
	
	public void remove(BaseEntity b){
		list.remove(b);
	}
	
	public BaseEntity getElement(int index){
		return list.get(index);
	}
	
	public int getNumberOfElements(){
		return list.size();
	}
	
	/*public BaseEntity[] getAllElements(){
		return (BaseEntity[]) list.toArray();
	}*/
	
}
