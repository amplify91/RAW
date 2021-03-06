package com.detour.raw;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.content.Context;

public class Level {
	//This class will be used to manage multiple world objects within one level.
	
	World mWorld;
	ArrayList<Sprite> mDrawableSprites;
	ArrayList<Sprite> mUpdateableSprites;
	
	//ProjectileList mProjectiles = new ProjectileList(8);
	
	B2DDebugDraw debug;
	
	public Level(Context context){
		
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		mWorld = new World(gravity, true);
		mWorld.setContinuousPhysics(true);
		
		mDrawableSprites = new ArrayList<Sprite>();
		mUpdateableSprites = new ArrayList<Sprite>();
		
		debug = new B2DDebugDraw(null, context);//TODO if debug drawing doesn't work, this null is probably why.
		
		mWorld.setDebugDraw(debug);
		debug.setFlags(B2DDebugDraw.e_shapeBit);
		//debug.setFlags(B2DDebugDraw.e_aabbBit);
	}
	
	public void drawDebug(Camera camera){
		debug.beginSpriteBatch();
		mWorld.drawDebugData();
		debug.endSpriteBatch();
		//Log.i("debugDraw", "success!!!!");
	}
	
	public void update(float deltaTime, int velocityIterations, int positionIterations){
		for(int i=0;i<mUpdateableSprites.size();i++){
			mUpdateableSprites.get(i).update(deltaTime);
		}
		mWorld.step(deltaTime, velocityIterations, positionIterations);
	}
	
	public void draw(SpriteBatch sb){
		for(int i=0;i<mDrawableSprites.size();i++){
			mDrawableSprites.get(i).draw(sb);
		}
	}
	
	Hero mHero;
	public void assignHero(Hero hero){
		//TODO change this to assign a different Hero to each client (for multiplayer).
		mHero = hero;
	}
	public Hero getHero(){
		return mHero;
	}
	
	public void addDrawableSprite(Sprite sprite){
		mDrawableSprites.add(sprite);
	}
	
	public void addUpdateableSprite(Sprite sprite){
		mUpdateableSprites.add(sprite);
	}
	
	void step(float deltaTime){
		mWorld.step(deltaTime, 8, 3);
	}
	
	World getWorld(){
		return mWorld;
	}
	
}
