package com.detour.raw;

import android.content.Context;
import android.util.Log;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	
	Camera camera;
	Input input;
	HUD mHUD;
	CollisionGrid cGrid;
	EntityManager mEntityManager;
	
	LevelLoader levelLoader;
	SpriteBatch spriteBatch;
	Texture mHeroTexture;
	
	Tile[] tileMap;
	Sprite hero;
	
	boolean levelLoaded = false;
	
	private GameManager(){
		input = new Input(this);
		camera = new Camera();
		mHUD = new HUD(camera);
		
	}
	
	public static synchronized GameManager getGameManager(){ //TODO synchronized?
		return gameManager;
	}
	
	public void update(){
		if(levelLoaded){
			mEntityManager.update();
			camera.update((float)hero.getX(), (float)hero.getY());
			mHUD.update();
		}
	}
	
	public void draw(){
		
		if(levelLoaded){
			/*spriteBatch.begin();
			for(int y=0;y<tileMap.length;y++){
				for(int x=0;x<tileMap[y].length;x++){
					tileMap[y][x].draw(spriteBatch, mTileTexture);
					//spriteBatch.draw(mTileTexture, tileMap[y][x].frame, x, y, tileMap[y][x].width, tileMap[y][x].height);
				}
			}
			spriteBatch.end(camera);*/
			
			//TODO Improve the time it takes to switch textures with spritebatch
			//or atlas all graphics on one spritesheet or both.
			//This is where the bottleneck in framerate is coming from!
			
			spriteBatch.begin(mHeroTexture);
			
			mHUD.draw(spriteBatch);
			mEntityManager.draw(spriteBatch);
			spriteBatch.end(camera);
		}
		
	}
	
	public void loadLevel(Context context, int program, int level){
		mHeroTexture = new Texture(context, Animation.HERO_TEXTURE, 3, new int[]{3,1,5}, new int[]{8,8,4,0,7,16,16,16,16}, new int[]{128,1024,64}, new int[]{128,320,64});
		if(!levelLoaded){
			levelLoader = new LevelLoader(context, level);
			tileMap = levelLoader.getTileMap();
			
			hero = new Hero();
			
			//hero.setFrame(1);
			hero.translate(2, 2);
			hero.scale(2, 2);
			
			spriteBatch = new SpriteBatch(1600, program, camera.getScreenRatio());
			
			int extraCellsH = 2;
			int extraCellsW = 2;
			if(levelLoader.levelWidth%CollisionGrid.CELL_WIDTH != 0){
				extraCellsH += 1;
			}
			if(levelLoader.levelHeight%CollisionGrid.CELL_HEIGHT != 0){
				extraCellsW += 1;
			}
			cGrid = new CollisionGrid(levelLoader.levelWidth/CollisionGrid.CELL_WIDTH + extraCellsH, /*tileMap.length/CollisionGrid.CELL_HEIGHT*/20/*TODO*/ + extraCellsW);
			mEntityManager = new EntityManager(cGrid);
			
			mEntityManager.add(hero);
			
			if(tileMap!=null){
				for(int i=0;i<tileMap.length;i++){
					mEntityManager.addTile(tileMap[i]);
				}
			}
			
			levelLoaded = true;
		}
		
		System.gc();
	}
	
	public HUD getHUD(){
		return mHUD;
	}
	
	public Input getInput(){
		return input;
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public Sprite getHero(){
		return hero;
	}
	
}
