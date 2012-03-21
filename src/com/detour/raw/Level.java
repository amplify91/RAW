package com.detour.raw;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.util.Log;

public class Level {
	//This class will be used to manage multiple world objects within one level.
	
	World mWorld;
	ArrayList<Sprite> mDynamicSprites;//maybe arraylist instead?
	
	B2DDebugDraw debug = new B2DDebugDraw(null);//TODO if debug drawing doesn't work, this null is probably why.
	
	public Level(){
		
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		mWorld = new World(gravity, true);
		mWorld.setContinuousPhysics(true);
		
		mDynamicSprites = new ArrayList<Sprite>();
		
		mWorld.setDebugDraw(debug);
		debug.setFlags(B2DDebugDraw.e_shapeBit);
	}
	
	public void drawDebug(){
		mWorld.drawDebugData();
		Log.i("debugDraw", "success!!!!");
	}
	
	public void update(float deltaTime, int velocityIterations, int positionIterations){
		for(int i=0;i<mDynamicSprites.size();i++){
			mDynamicSprites.get(i).update();
		}
		mWorld.step(deltaTime, velocityIterations, positionIterations);
	}
	
	void create(Sprite sprite, float x, float y, float width, float height, boolean dynamic){
		sprite.create(mWorld, x, y, width, height, dynamic);
		if(dynamic){
			mDynamicSprites.add(sprite);
		}
	}
	
	void step(float deltaTime){
		mWorld.step(deltaTime, 8, 3);
	}
	
	World getWorld(){
		return mWorld;
	}
	
}
