package com.detour.raw;

import org.jbox2d.common.Vec2;

public class PhysicsProjectile extends PhysicsComponent{
	
	//int mType;
	float mVelocity;
	Vec2 mVelocityVec = new Vec2(0, 1);
	//Vec2 mSpawnPoint;
	Vec2 mDestinationPoint;
	
	public PhysicsProjectile(){
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void setProjectileProperties(int type, Sprite parent, Vec2 destinationPoint){
		//TODO finish. also swap parent for just vec2 spawnpoint if no other info is needed from parent.
		mBody.setTransform(parent.getProjectileSpawnPoint(), 0);
		//mType = type;
		//mSpawnPoint = spawnPoint;
		mDestinationPoint = destinationPoint;
		setType(type);
		
	}
	
	private void setType(int type){
		
		if(type==Projectile.TYPE_RAW){
			mVelocity = Projectile.VELOCITY_RAW;
			mVertices = Projectile.VERTS_RAW;
		}
		
	}
	
}
