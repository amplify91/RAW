package com.detour.raw;

import java.util.ArrayList;

import android.util.Log;

public class EntityManager {
	
	ArrayList<BaseEntity> mEntityList = new ArrayList<BaseEntity>();
	CollisionGrid mGrid;
	int mHeroIndex;
	
	public EntityManager(CollisionGrid c){
		mGrid = c;
	}
	
	public void update(){
		//TODO change this to update entities depending on their grid placement.
		//Relative (nearby) entities will be the first and possibly only ones updated.
		mHeroIndex = GameManager.getGameManager().getHero().getIndex();
		mEntityList.get(mHeroIndex).update();
		
		for(int i = (mEntityList.size()-1); i>=0;i--){
			mEntityList.get(i).update();
			mGrid.updateCurrentCell(mEntityList.get(i));
		}
		//Log.i("EntityManager", ""+GameManager.getGameManager().getHero().cellx);
	}
	
	public void add(BaseEntity b){
		//make sure entity is completely initialized first
		mEntityList.add(b);
		mGrid.placeInGrid(b);
	}
	
	public void remove(BaseEntity b){
		mEntityList.remove(b.getIndex());
		mGrid.getCell(b.cellx, b.celly).remove(b);
	}
	
}
