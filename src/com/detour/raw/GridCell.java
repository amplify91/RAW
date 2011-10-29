package com.detour.raw;

import java.util.*;

public class GridCell {
	
	ArrayList<BaseEntity> mEntities = new ArrayList<BaseEntity>();
	ArrayList<BaseEntity> mNotTiles = new ArrayList<BaseEntity>();
	int x;
	int y;
	int it;
	int mSize;
	
	public GridCell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void add(BaseEntity b){
		mEntities.add(b);
		mNotTiles.add(b);
		b.cellx = x;
		b.celly = y;
	}
	
	public void addTile(Tile t){
		mEntities.add(t);
		t.cellx = x;
		t.celly = y;
	}
	
	public void remove(int index){
		mNotTiles.remove(index);
	}
	
	public void remove(BaseEntity b){
		mNotTiles.remove(b);
	}
	
	public BaseEntity getElement(int index){
		return mNotTiles.get(index);
	}
	
	public int getNumberOfElements(){
		return mNotTiles.size();
	}
	
	public void updateContents(){
		mSize = mNotTiles.size();
		for(it=0;it<mSize;it++){
			mNotTiles.get(it).update();
		}
	}
	
	public void drawContents(SpriteBatch sb, Texture t){
		mSize = mEntities.size();
		for(it=0;it<mSize;it++){
			mEntities.get(it).draw(sb, t);
		}
	}
	
	/*public BaseEntity[] getAllElements(){
		return (BaseEntity[]) list.toArray();
	}*/
	
}
