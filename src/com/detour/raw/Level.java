package com.detour.raw;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Level {
	//This class will be used to manage multiple world objects within one level.
	//For now, it is not being used at all.
	
	World mWorld;
	
	public Level(){
		
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		mWorld = new World(gravity, true);
		mWorld.setContinuousPhysics(true);
		
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0.0f, -10.0f);
		
		Body groundBody = mWorld.createBody(groundBodyDef);
		
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(50.0f, 10.0f);
		groundBody.createFixture(groundBox, 0.0f);
		
	}
	
	/*void add(Sprite sprite){
		sprite.add(mWorld);
	}*/
	
	void step(float deltaTime){
		mWorld.step(deltaTime, 8, 3);
	}
	
}
