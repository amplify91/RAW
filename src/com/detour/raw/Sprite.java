package com.detour.raw;

import android.content.Context;
import android.util.Log;

public class Sprite{
	
	RenderComponent mRenderable;
	PhysicsComponent mPhysics;
	AnimationComponent mAnimation;
	
	public static final int VERTEX_SIZE = 2 + 2;
	public static final int SPRITE_SIZE = 4 * VERTEX_SIZE;
	//TODO put these 2 fields into render components
	
	public float width;
	public float height;
	
	public static final float SCALE_FACTOR = 2f/15f;
	public static final float SCALE_FACTOR_INV = 15f/2f;
	
	public Sprite(Context context){
		
		//renderable = new RenderVisible(context);
		mPhysics = new PhysicsHero();
		mAnimation = new AnimationComponent();
		
		width = (SCALE_FACTOR);
		height = (SCALE_FACTOR);
		
		mAnimation.setAnimation(Animation.RUNNING);
		
	}
	
	public void draw(SpriteBatch sb, Texture t){
		sb.draw(t, mAnimation.getFrame(), mPhysics.getX(), mPhysics.getY(), width, height);
	}
	
	public void update(int speed){
		mPhysics.update(speed);
		mAnimation.update(speed);
	}
	
	public float getX(){
		return mPhysics.getX();
	}
	
	public float getY(){
		return mPhysics.getY();
	}
	
	public void setFrame(int frame){
		mAnimation.setFrame(frame);
	}
	
	public void scale(float sx, float sy){
		width *= sx;
		height *= sy;
	}
	
	/*public void scaleGraphics(float sx, float sy){
		width *= sx;
		height *= sy;
	}
	
	public void scalePhysics(float sx, float sy){
		width *= sx;
		height *= sy;
	}*/
	
	public void translate(float tx, float ty){
		mPhysics.translate(tx, ty);
	}
	
}
