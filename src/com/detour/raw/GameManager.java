package com.detour.raw;

import android.content.Context;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	
	private Context mContext;
	
	Camera camera;
	Input input;
	HUD mHUD;
	CollisionGrid cGrid;
	EntityManager mEntityManager;
	
	LevelLoader levelLoader;
	SpriteBatch spriteBatch;
	Texture mTileTexture;
	Texture mHeroTexture;
	
	Tile[][] tileMap;
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
			camera.update(hero.getX(), hero.getY());
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
			
			spriteBatch.begin();
			
			mHUD.draw(spriteBatch, mHeroTexture);
			hero.draw(spriteBatch, mHeroTexture);
			spriteBatch.end(camera);
		}
		
	}
	
	public void loadLevel(Context context, int program, int level){
		this.mContext = context;
		levelLoader = new LevelLoader(context, level);
		tileMap = levelLoader.getTileMap();
		mTileTexture = new Texture(mContext, R.drawable.tilesheet, 16, 4);
		
		hero = new Hero();
		mHeroTexture = new Texture(mContext, Animation.HERO_TEXTURE, 8, 2);
		//hero.setFrame(1);
		hero.translate(2, 2);
		hero.scale(2, 2);
		
		spriteBatch = new SpriteBatch(levelLoader.getNumberOfSprites()+1, program, camera.getScreenRatio());
		
		int extraCellsH = 2;
		int extraCellsW = 2;
		if(tileMap[0].length%CollisionGrid.CELL_WIDTH != 0){
			extraCellsH += 1;
		}
		if(tileMap.length%CollisionGrid.CELL_HEIGHT != 0){
			extraCellsW += 1;
		}
		cGrid = new CollisionGrid(tileMap[0].length/CollisionGrid.CELL_WIDTH + extraCellsH, /*tileMap.length/CollisionGrid.CELL_HEIGHT*/20/*TODO*/ + extraCellsW);
		mEntityManager = new EntityManager(cGrid);
		
		mEntityManager.add(hero);
		
		if(tileMap!=null){
			for(int y=0;y<tileMap.length;y++){
				for(int x=0;x<tileMap[y].length;x++){
					mEntityManager.add(tileMap[y][x]);
				}
			}
		}
		
		levelLoaded = true;
		
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
