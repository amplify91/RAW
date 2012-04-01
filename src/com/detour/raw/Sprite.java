package com.detour.raw;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


public class Sprite extends BaseEntity{
	
	PhysicsComponent mPhysics;
	AnimationComponent mAnimation;
	
	private boolean isActive = false;
	
	private float mDrawWidth;
	private float mDrawHeight;
	private float mDrawOffsetX = 0;
	private float mDrawOffsetY = 0;
	
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
		
		mDrawWidth = width * SCALE_FACTOR; //TODO change SCALE_FACTOR without breaking Camera class.
		mDrawHeight = height * SCALE_FACTOR;
		mDrawOffsetX = -width / 2f;
		mDrawOffsetY = -height / 2f;
		setActive(true);
		mPhysics.create(world, x/2f, y/2f, width, height, dynamic);
		
	}
	
	public void create(World world, float x, float y, Vec2 vertices[], boolean dynamic){
		//TODO height and width are equal to 1 and so this method currently only works for tiles. Fix.
		mDrawWidth = 0.5f * SCALE_FACTOR;
		mDrawHeight = 0.5f * SCALE_FACTOR;
		mDrawOffsetX = -mDrawWidth / 2f;
		mDrawOffsetY = -mDrawHeight / 2f;
		setActive(true);
		mPhysics.create(world, x/2f, y/2f, vertices, vertices.length, dynamic);
	}
	
	public void createProjectile(World world, Sprite parent, float xDest, float yDest, int type){
		setActive(true);
		mPhysics.setProjectileProperties(parent, xDest, yDest, type);
	}
	
	public void destroy(){
		//TODO
		setActive(false);
	}
	
	public void draw(SpriteBatch sb){
		sb.drawSprite(mAnimation.getFrame(), mPhysics.getX()+mDrawOffsetX, mPhysics.getY()+mDrawOffsetY, mDrawWidth, mDrawHeight);
	}
	
	public void update(float deltaTime){
		mAnimation.update(deltaTime);
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
	
	public boolean isActive(){
		return isActive;
	}
	
	public void setActive(boolean active){
		isActive = active;
	}
	
	/*public float getOriginX(){
		return mPhysics.getX() + mDrawWidth/2f;
	}
	
	public float getOriginY(){
		return mPhysics.getY() + mDrawHeight/2f;
	}*/
	
	public void pauseAnimation(){
		mAnimation.pause();
	}
	
	public void resumeAnimation(){
		mAnimation.resume();
	}
	
}
