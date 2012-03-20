package com.detour.raw;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.content.Context;

public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	
	Camera camera;
	Input input;
	HUD mHUD;
	
	World mWorld;
	
	LevelLoader levelLoader;
	SpriteBatch spriteBatch;
	Texture mHeroTexture;
	
	Tile[] tileMap;
	Sprite hero;
	Sprite ground;
	
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
			camera.update((float)hero.getX(), (float)hero.getY());
			mHUD.update();
			hero.update();
			mWorld.step(deltaTime, 8, 3);
			/*Vec2 position = body.getPosition();
            float angle = body.getAngle();
            Log.i("Test", position.x +" "+ position.y +" "+ angle);*/
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
			hero.draw(spriteBatch);
			spriteBatch.end(camera);
		}
		
	}
	
	public void loadLevel(Context context, int program, int level){
		
		levelLoaded = false;
		
		//<Practice> TODO
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		mWorld = new World(gravity, true);
		mWorld.setContinuousPhysics(true);
		
		mHeroTexture = new Texture(context, Animation.HERO_TEXTURE, 3, new int[]{3,1,5}, new int[]{8,8,4,0,7,16,16,16,16}, new int[]{128,1024,64}, new int[]{128,320,64});
		if(!levelLoaded){
			levelLoader = new LevelLoader(context, level);
			tileMap = levelLoader.getTileMap();
			
			hero = new Sprite();
			hero.create(mWorld, 2, 3, 2, 2, true);
			ground = new Sprite();
			ground.create(mWorld, 0, 0, 10, 2, false);
			
			spriteBatch = new SpriteBatch(1600, program, camera.getScreenRatio());
			
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
