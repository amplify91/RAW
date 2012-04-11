package com.detour.raw;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class PhysicsComponent {
	
	Body mBody;
	Vec2[] mVertices;
	
	public PhysicsComponent(){
		
	}
	
	public void create(World world, float x, float y, float width, float height, boolean dynamic){
		
		BodyDef bodyDef = new BodyDef();
		if(dynamic){
			bodyDef.type = BodyType.DYNAMIC;
		}else{
			bodyDef.type = BodyType.STATIC;
		}
		bodyDef.position.set(x, y);//(x+(width/2f), y+(height/2f))
		mBody = world.createBody(bodyDef);
		
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(width/2, height/2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicBox;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.0f;
		mBody.createFixture(fixtureDef);
		mBody.setFixedRotation(true);
		
	}
	
	public void create(World world, float x, float y, Vec2 vertices[], boolean dynamic){
		
		BodyDef bodyDef = new BodyDef();
		if(dynamic){
			bodyDef.type = BodyType.DYNAMIC;
		}else{
			bodyDef.type = BodyType.STATIC;
		}
		bodyDef.position.set(x, y);
		mBody = world.createBody(bodyDef);
		
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.set(vertices, vertices.length);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicBox;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.0f;
		mBody.createFixture(fixtureDef);
		mBody.setFixedRotation(true);
		
	}
	
	public abstract void update();
	
	public void destroy(){
		//TODO
	}
	
	public float getX(){
		return mBody.getPosition().x;
	}
	
	public float getY(){
		return mBody.getPosition().y;
	}
	
	public void setProjectileProperties(int type, Vec2 destinationPoint){
		
	}
	
}
