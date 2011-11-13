package com.detour.raw;


public class Sprite extends BaseEntity{
	
	PhysicsComponent mPhysics;
	AnimationComponent mAnimation;
	
	public static final int VERTEX_SIZE = 2 + 2;
	public static final int SPRITE_SIZE = 4 * VERTEX_SIZE;
	//TODO put these 2 fields into render components
	
	public float width;
	public float height;
	
	public static final float SCALE_FACTOR = 2f/15f;
	public static final float SCALE_FACTOR_INV = 15f/2f;
	
	public Sprite(PhysicsComponent p){
		
		//renderable = new RenderVisible(context);
		mPhysics = p;
		mAnimation = new AnimationComponent();
		
		width = (SCALE_FACTOR);
		height = (SCALE_FACTOR);
		
		mAnimation.setAnimation(Animation.RUNNING);
		
	}
	
	public void draw(SpriteBatch sb){
		sb.draw(mAnimation.getFrame(), (float)mPhysics.getX(), (float)mPhysics.getY(), width, height);
	}
	
	public void update(GridCell gc, int i){
		mPhysics.update(gc, i);
		mAnimation.update();
	}
	
	public boolean isColliding(Sprite sprite) {
		return mPhysics.isCollidingSAT(sprite);
	}
	
	public double getX(){
		return mPhysics.getX();
	}
	
	public double getY(){
		return mPhysics.getY();
	}
	
	public void setFrame(int frame){
		mAnimation.setFrame(frame);
	}
	
	public void scale(float sx, float sy){
		width *= sx;
		height *= sy;
		mPhysics.scale(sx, sy);
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
	
	public void pauseAnimation(){
		mAnimation.pause();
	}
	
	public void resumeAnimation(){
		mAnimation.resume();
	}
	
}
