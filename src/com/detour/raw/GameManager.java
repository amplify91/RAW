package com.detour.raw;

import android.content.Context;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	
	private Context mContext;
	
	public static Camera camera;
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
		camera = new Camera();
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
				camera.update(hero.getX(), hero.getY());
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
			
			//TODO Improve the time it takes to switch textures with spritebatch
			//or atlas all graphics on one spritesheet or both.
			//This is where the bottleneck in framerate is coming from!
			
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
		mHeroTexture = new Texture(mContext, Animation.HERO_TEXTURE, 8, 1);
		//hero.setFrame(1);
		hero.translate(2, 2);
		hero.scale(2, 2);
		
		spriteBatch = new SpriteBatch(tileMap.length * tileMap[0].length, program);
		
		levelLoaded = true;
	}
	
	public Input getInput(){
		return input;
	}
	
	public static Camera getCamera(){
		return camera;
	}
	
	public Sprite getHero(){
		return hero;
	}
	
}
