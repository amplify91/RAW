package com.detour.raw;

import java.util.ArrayList;

import android.util.Log;

public class EntityManager {
	
	ArrayList<BaseEntity> mEntityList = new ArrayList<BaseEntity>();
	CollisionGrid mGrid;
	int mGridW;
	int mGridH;
	int mHeroIndex = -1;
	int mHeroCellX;
	int mHeroCellY;
	
	public EntityManager(CollisionGrid c){
		mGrid = c;
		mGridW = mGrid.width;
		mGridH = mGrid.height;
	}
	
	public void update(){
		//TODO change this to update entities depending on their grid placement.
		//Relative (nearby) entities will be the first and possibly only ones updated.
		if(mHeroIndex==-1){
			mHeroIndex = GameManager.getGameManager().getHero().getIndex();
		}
		mHeroCellX = mEntityList.get(mHeroIndex).cellx;
		mHeroCellY = mEntityList.get(mHeroIndex).celly;
		
		//Only update the closest few GridCells to the Hero.
		//TODO improve this to update only the objects that need updated in the closest cells.
		for(int x = mHeroCellX-10;x<mHeroCellX+30 && x!=mGridW;x++){
			if(x<0){
				x=0;
			}else if(x+40>=mGridW){
				break;
			}
			for(int y = 0;y<mGridH;y++){ //TODO pad closer to hero's y if game design allows
				mGrid.getCell(x, y).updateContents();
			}
		}
		
		//Update the current cells for entities that have moved.
		/*for(int i = (mEntityList.size()-1); i>=0;i--){
			mEntityList.get(i).update();
			//TODO change following line to use Observer/Receiver pattern so entities can
			// notify when they have moved.
			mGrid.updateCurrentCell(mEntityList.get(i));
		}*/mGrid.updateCurrentCell(mEntityList.get(mHeroIndex));
		//Log.i("EntityManager", ""+GameManager.getGameManager().getHero().cellx);
	}
	
	public void draw(SpriteBatch sb, Texture t){
		for(int x = mHeroCellX-8/CollisionGrid.CELL_WIDTH;x<mHeroCellX+32/CollisionGrid.CELL_WIDTH && x!=mGridW;x++){
			if(x<0){
				x=0;
			}else if(x+10>=mGridW){
				break;
			}
			for(int y = mHeroCellY-16/CollisionGrid.CELL_HEIGHT;y<mHeroCellY+16/CollisionGrid.CELL_HEIGHT;y++){ //TODO pad closer to hero's y if game design allows
				if(y<0){
					y=0;
				}else if(y+4>=mGridH){
					break;
				}
				//Log.i("EntityManager", "cellX = "+x+", cellY = "+y+" herox = "+GameManager.getGameManager().getHero().cellx);
				mGrid.getCell(x, y).drawContents(sb, t);
			}
		}
	}
	
	public void add(BaseEntity b){
		//make sure entity is completely initialized first
		mEntityList.add(b);
		mGrid.placeInGrid(b);
	}
	
	public void addTile(Tile t){
		//Don't add regular tiles to entity list because they don't ever get updated.
		//However, they are still added to the collision grid.
		//I realize this extra method is pretty bad coding. Whatevs.
		mGrid.placeTileInGrid(t);
	}
	
	public void remove(BaseEntity b){
		mEntityList.remove(b.getIndex());
		mGrid.getCell(b.cellx, b.celly).remove(b);
	}
	
}
