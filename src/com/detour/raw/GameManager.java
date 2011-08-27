package com.detour.raw;

import android.content.Context;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	
	LevelLoader levelLoader;
	
	private Context mContext;
	
	Tile[][] tileMap;
	
	int speed = 1;
	
	private GameManager(){
		
	}
	
	public static GameManager getGameManager(){
		return gameManager;
	}
	
	public void update(){
		if(tileMap!=null){
			for(int y=0;y<tileMap.length;y++){
				for(int x=0;x<tileMap[y].length;x++){
					tileMap[y][x].translate(-0.1f*(float)speed, 0f);
				}
			}
		}
	}
	
	public void draw(float[] mViewMatrix, float[] mProjMatrix){
		
		for(int y=0;y<tileMap.length;y++){
			for(int x=0;x<tileMap[y].length;x++){
				tileMap[y][x].draw(mViewMatrix, mProjMatrix);
			}
		}
		
	}
	
	public void setSpeed(int s){
		speed = s;
	}
	
	public void loadLevel(Context context, int program, int level){
		this.mContext = context;
		levelLoader = new LevelLoader(context, program, level);
		tileMap = levelLoader.getTileMap();
		
	}
	
}
