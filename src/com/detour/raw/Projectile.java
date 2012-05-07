package com.detour.raw;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.util.Log;

public class Projectile extends ReusableSprite{
	
	public boolean isActive = false;
	
	public static final int TYPE_RAW = 1;
	
	public static final float WIDTH_RAW = 1;
	public static final float HEIGHT_RAW = 1;
	
	public static final float VELOCITY_RAW = 1.0f;
	
	public static final Vec2[] VERTS_RAW = new Vec2[]{new Vec2(0.0f,0.0f), new Vec2(0.2f,0.0f), new Vec2(0.2f,0.2f), new Vec2(0.2f,0.0f)};
	
	public Projectile(){
		
		super(new PhysicsProjectile(), new AnimationComponent());
		
	}
	
	public void create(World world, float x, float y){
		//TODO
		mDrawWidth = WIDTH_RAW * SCALE_FACTOR; //TODO change SCALE_FACTOR without breaking Camera class.
		mDrawHeight = HEIGHT_RAW * SCALE_FACTOR; //TODO update draw properties based off of physics body.
		mDrawOffsetX = -WIDTH_RAW / 2f;
		mDrawOffsetY = -HEIGHT_RAW / 2f;
		mPhysics.create(world, x, y, VERTS_RAW, true);//TODO change to true for dynamic projectiles.
		mPhysics.mBody.setBullet(true);
	}
	
	public void prepareForSpawn(int type, Sprite parent, Vec2 destination){
		((PhysicsProjectile)mPhysics).setProjectileProperties(type, parent, destination);
		//TODO finish
		mAnimation.setFrame(Animation.FRAME_PROJECTILE_RAW_BULLET); //TODO change depending on type
		mAnimation.pause();
		
		isReadyForSpawn = true;
	}

	public void spawn(World world) {
		if(isReadyForSpawn){
			isActive = true;
			isReadyForSpawn = false;
			create(world, getX(), getY());
		}else{
			Log.e("Projectile", "prepareForSpawn() before spawn()");
		}
	}

	public void recycle() {
		isActive = false;
		isReadyForSpawn = false;
		//TODO set to a neutral state that won't interfere with the rest of the game.
	}
	
}
