package com.detour.raw;

import java.util.*;

public class GridCell {
	
	CollisionGrid mCollisionGrid;
	ArrayList<BaseEntity> mEntities = new ArrayList<BaseEntity>();
	ArrayList<BaseEntity> mUpdatables = new ArrayList<BaseEntity>();
	int x;
	int y;
	int it;
	int mSize;
	int mAllSize;
	private int id = 0;
	
	public GridCell(CollisionGrid cg, int x, int y){
		mCollisionGrid = cg;
		this.x = x;
		this.y = y;
	}
	
	public void add(BaseEntity b){
		mEntities.add(b);
		mUpdatables.add(b);
		b.cellx = x;
		b.celly = y;
		b.cellIndex = id++; //TODOS straight up wrong. only right if no objects are ever removed.
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
	
	public BaseEntity getEntity(int index){
		return mEntities.get(index);
	}
	
	public BaseEntity getUpdateable(int index){
		return mUpdatables.get(index);
	}
	
	public int getNumberOfEntities(){
		return mEntities.size();
	}
	
	public int getNumberOfUpdateables(){
		return mUpdatables.size();
	}
	
	public void updateContents(){
		for(it=0;it<mSize;it++){
			mUpdatables.get(it).update(mCollisionGrid);
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
