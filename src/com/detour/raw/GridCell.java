package com.detour.raw;

import java.util.*;

public class GridCell {
	
	ArrayList<BaseEntity> mEntities = new ArrayList<BaseEntity>();
	ArrayList<BaseEntity> mUpdatables = new ArrayList<BaseEntity>();
	int x;
	int y;
	int it;
	int mSize;
	int mAllSize;
	
	public GridCell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void add(BaseEntity b){
		mEntities.add(b);
		mUpdatables.add(b);
		b.cellx = x;
		b.celly = y;
		mSize = mUpdatables.size();
		mAllSize = mEntities.size();
	}
	
	public void addTile(Tile t){
		mEntities.add(t);
		t.cellx = x;
		t.celly = y;
		mAllSize = mEntities.size();
	}
	
	public void remove(int index){
		mUpdatables.remove(index);
		mSize = mUpdatables.size();
	}
	
	public void remove(BaseEntity b){
		mUpdatables.remove(b);
		mSize = mUpdatables.size();
	}
	
	public BaseEntity getElement(int index){
		return mUpdatables.get(index);
	}
	
	public int getNumberOfElements(){
		return mUpdatables.size();
	}
	
	public void updateContents(){
		for(it=0;it<mSize;it++){
			mUpdatables.get(it).update();
		}
	}
	
	public void drawContents(SpriteBatch sb){
		for(it=0;it<mAllSize;it++){
			mEntities.get(it).draw(sb);
		}
	}
	
	/*public BaseEntity[] getAllElements(){
		return (BaseEntity[]) list.toArray();
	}*/
	
}
