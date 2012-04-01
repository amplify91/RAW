package com.detour.raw;

import org.jbox2d.common.Vec2;

import android.content.Context;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	
	Camera camera;
	Input input;
	HUD mHUD;
	
	Level mLevel;
	
	LevelLoader levelLoader;
	SpriteBatch spriteBatch;
	Texture mHeroTexture;
	
	Tile[] tileMap;
	Hero hero;
	Tile test1;
	
	boolean levelLoaded = false;
	
	private GameManager(){
		input = new Input(this);
		camera = new Camera();
		mHUD = new HUD(camera);
		
	}
	
	public static synchronized GameManager getGameManager(){ //TODO synchronized?
		return gameManager;
	}
	
	public void update(float deltaTime){
		if(levelLoaded){
			camera.update((float)hero.getX()+1f, (float)hero.getY());
			mHUD.update();
			mLevel.update(deltaTime, 8, 3);
			//TODO update camera AFTER level?
		}
	}
	
	public void draw(){
		
		if(levelLoaded){
			
			spriteBatch.begin(mHeroTexture);
			for(int i=0;i<tileMap.length;i++){
				tileMap[i].draw(spriteBatch);
			}
			mHUD.draw(spriteBatch);
			spriteBatch.end();
			spriteBatch.begin(mHeroTexture);
			hero.draw(spriteBatch);
			test1.draw(spriteBatch);
			spriteBatch.end();
			//mLevel.drawDebug(camera);
		}
		
	}
	
	public void loadLevel(Context context, int level){
		
		levelLoaded = false;
		
		mLevel = new Level(context);
		
		mHeroTexture = new Texture(context, Animation.HERO_TEXTURE, 3, new int[]{3,1,5}, new int[]{8,8,4,0,7,16,16,16,16}, new int[]{128,1024,64}, new int[]{128,320,64});
		if(!levelLoaded){
			levelLoader = new LevelLoader(context, level, mLevel);
			tileMap = levelLoader.getTileMap();
			
			hero = new Hero();
			test1 = new Tile(64);
			Vec2[] verts = {new Vec2(0,0),new Vec2(0.5f,0),new Vec2(0.5f,0.5f)};
			mLevel.create(test1, 47.5f, 1.5f, verts, false);
			mLevel.create(hero, 2, 3, 1, 1, true);
			
			spriteBatch = new SpriteBatch(1600, SpriteBatch.SPRITE_SHADER, context, camera);
			
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
	
	public Hero getHero(){
		return hero;
	}
	
}
