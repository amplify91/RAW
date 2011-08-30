package com.detour.raw;

import android.content.Context;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	
	LevelLoader levelLoader;
	SpriteBatch spriteBatch;
	
	private Context mContext;
	
	Tile[][] tileMap;
	Sprite[] sprites;
	
	int speed = 1;
	
	private GameManager(){
		
	}
	
	public static synchronized GameManager getGameManager(){ //TODO synchronized
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
		
		spriteBatch.begin();
		
		for(int y=0;y<tileMap.length;y++){
			for(int x=0;x<tileMap[y].length;x++){
				tileMap[y][x].draw();
				//spriteBatch.draw(tileMap[y][x].getX(), tileMap[y][x].getY());
			}
		}
		
		spriteBatch.end();
		
		for(int x=0;x<sprites.length;x++){
			sprites[x].draw();
			//sprites[x].translate(0.1f, 0.0f);
		}
		
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
		
		spriteBatch = new SpriteBatch(tileMap.length);
	}
	
}
