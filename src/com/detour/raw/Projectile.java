package com.detour.raw;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Projectile extends Sprite{
	
	private Sprite mParent;
	
	public boolean isActive = false;
	
	public static final int TYPE_RAW = 1;
	
	public static final float VELOCITY_RAW = 1.0f;
	
	public static final Vec2[] VERTS_RAW = new Vec2[]{new Vec2(0.0f,0.0f), new Vec2(0.2f,0.0f), new Vec2(0.2f,0.2f), new Vec2(0.2f,0.0f)};
	
	public Projectile(int type, Sprite parent, Vec2 destination){
		
		super(new PhysicsProjectile(), new AnimationComponent());
		
		recycleAs(type, parent, destination);
		
	}
	
	@Override
	public void create(World world, float x, float y, Vec2 vertices[], boolean dynamic){
		super.create(world, mParent.getProjectileSpawnPoint().x, mParent.getProjectileSpawnPoint().y, mPhysics.mVertices, /* why does this break my game?!?! */true);
		//mPhysics.mBody.setBullet(true);
	}
	
	public void recycleAs(int type, Sprite parent, Vec2 destination){
		destroy();
		mParent = parent;
		mPhysics.setProjectileProperties(type, destination);
		mAnimation.setFrame(Animation.FRAME_PROJECTILE_RAW_BULLET); //TODO change depending on type
		mAnimation.pause();
	}
	
}
