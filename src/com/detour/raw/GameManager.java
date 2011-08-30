package com.detour.raw;

import android.content.Context;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	
	LevelLoader levelLoader;
	
	private Context mContext;
	
	Tile[][] tileMap;
	Sprite[] sprites;
	
	private static float[] mViewMatrix;
	private static float[] mProjMatrix;
	
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
	
	public void draw(){
		
		for(int y=0;y<tileMap.length;y++){
			for(int x=0;x<tileMap[y].length;x++){
				tileMap[y][x].draw();
			}
		}
		for(int x=0;x<sprites.length;x++){
			sprites[x].draw();
			//sprites[x].translate(0.1f, 0.0f);
		}
		
	}
	
	public static float[] getViewMatrix(){
		return mViewMatrix;
	}
	
	public static float[] getProjMatrix(){
		return mProjMatrix;
	}
	
	public void setViewMatrix(float[] view){
		mViewMatrix = view;
	}
	
	public void setProjMatrix(float[] proj){
		mProjMatrix = proj;
	}
	
	public void setSpeed(int s){
		speed = s;
	}
	
	public void loadLevel(Context context, int program, int level){
		this.mContext = context;
		levelLoader = new LevelLoader(context, program, level);
		tileMap = levelLoader.getTileMap();
		sprites = new Sprite[1];
		sprites[0] = new Sprite(mContext);
		sprites[0].setProgram(program);
		sprites[0].loadGLTexture(R.drawable.raw1a);
		sprites[0].translate(-1, -0.5f);
	}
	
}
