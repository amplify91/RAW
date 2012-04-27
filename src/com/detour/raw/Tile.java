package com.detour.raw;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Tile extends Sprite{
	
	private static final float HEIGHT = 0.5f;
	private static final float WIDTH = 0.5f;
	
	public Tile(){
		super(new PhysicsStatic(), new AnimationComponent());
		
	}
	
	public void create(World world, float x, float y, int frame){
		
		int newFrame = convertTileFrame(frame);
		
		mDrawWidth = WIDTH * SCALE_FACTOR;
		mDrawHeight = HEIGHT * SCALE_FACTOR;
		mDrawOffsetX = 0;
		mDrawOffsetY = 0;
		mPhysics.create(world, x, y, getTileVertices(newFrame), false);
		
		
		mAnimation.setFrame(newFrame);
		pauseAnimation();
		
	}
	
	private int convertTileFrame(int frame){
		//TODO this is kind of an ugly way to do this.
		int f = 0;
		if(frame>0&&frame<65){
			f = Animation.FRAME_TEST_TILES[frame-1];
		}else{
			f = frame;
		}
		return f;
	}
	
	private Vec2[] getTileVertices(int frame){
		//TODO finish this. Plug in vertices for all different types of frames.
		Vec2[] verts;
		verts = new Vec2[]{new Vec2(0f,0f),new Vec2(0.5f,0f),new Vec2(0.5f,0.5f),new Vec2(0f,0.5f)};
		return verts;
	}
	
}