package com.detour.raw;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class Sprite extends BaseEntity{
	
	PhysicsComponent mPhysics;
	AnimationComponent mAnimation;
	
	public float mWidth;
	public float mHeight;
	
	public static final int VERTEX_SIZE = 2 + 2;
	public static final int SPRITE_SIZE = 4 * VERTEX_SIZE;
	//TODO put these 2 fields into render components
	public static final float SCALE_FACTOR = 2f/15f;
	public static final float SCALE_FACTOR_INV = 15f/2f;
	
	public Sprite(PhysicsComponent p, AnimationComponent a){
		
		//renderable = new RenderVisible(context);
		mPhysics = p;
		mAnimation = a;
		
	}
	
	public void create(World world, float x, float y, float width, float height, boolean dynamic){
		
		mWidth = width * SCALE_FACTOR;
		mHeight = height * SCALE_FACTOR;
		mPhysics.create(world, x, y, width, height, dynamic);
		
	}
	
	public void destroy(){
		
	}
	
	public void draw(SpriteBatch sb){
		sb.draw(mAnimation.getFrame(), mPhysics.getX(), mPhysics.getY(), mWidth, mHeight);
	}
	
	public void update(){
		mAnimation.update();
		mPhysics.update();
	}
	
	public void getInput(){
		
	}
	
	public float getX(){
		return mPhysics.getX();
	}
	
	public float getY(){
		return mPhysics.getY();
	}
	
	public void pauseAnimation(){
		mAnimation.pause();
	}
	
	public void resumeAnimation(){
		mAnimation.resume();
	}
	
}
