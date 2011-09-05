package com.detour.raw;

import android.content.Context;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	
	private Context mContext;
	
	LevelLoader levelLoader;
	SpriteBatch spriteBatch;
	Texture mTileTexture;
	Texture mHeroTexture;
	Input input;
	
	Tile[][] tileMap;
	Sprite hero;
	
	boolean levelLoaded = false;
	
	int speed = 1;
	
	private GameManager(){
		input = new Input();
	}
	
	public static synchronized GameManager getGameManager(){ //TODO synchronized?
		return gameManager;
	}
	
	public void update(){
		if(levelLoaded){
			if(tileMap!=null){
				for(int y=0;y<tileMap.length;y++){
					for(int x=0;x<tileMap[y].length;x++){
						tileMap[y][x].update(speed);
					}
				}
				hero.update(speed);
			}
		}
	}
	
	public void draw(){
		
		if(levelLoaded){
			spriteBatch.begin();
			for(int y=0;y<tileMap.length;y++){
				for(int x=0;x<tileMap[y].length;x++){
					tileMap[y][x].draw(spriteBatch, mTileTexture);
					//spriteBatch.draw(mTileTexture, tileMap[y][x].frame, x, y, tileMap[y][x].width, tileMap[y][x].height);
				}
			}
			spriteBatch.end();
			
			spriteBatch.begin();
			hero.draw(spriteBatch, mHeroTexture);
			spriteBatch.end();
		}
		
	}
	
	public void setSpeed(int s){
		speed = s;
	}
	
	public void loadLevel(Context context, int program, int level){
		this.mContext = context;
		levelLoader = new LevelLoader(context, program, level);
		tileMap = levelLoader.getTileMap();
		mTileTexture = new Texture(mContext, R.drawable.spritesheet1, 4, 2);
		
		hero = new Sprite(mContext);
		mHeroTexture = new Texture(mContext, R.drawable.raw1a, 1, 1);
		hero.setFrame(1);
		hero.translate(2, 2);
		hero.scale(2, 2);
		
		spriteBatch = new SpriteBatch(tileMap.length * tileMap[0].length, program);
		
		levelLoaded = true;
	}
	
	public Input getInput(){
		return input;
	}
	
	public Sprite getHero(){
		return hero;
	}
	
}
