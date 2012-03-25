package com.detour.raw;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;

import android.content.Context;
import android.opengl.GLES20;

public class B2DDebugDraw extends DebugDraw{
	
	SpriteBatch mSpriteBatch;
	
	public B2DDebugDraw(IViewportTransform viewport, Context context) {
		super(viewport);
		mSpriteBatch = new SpriteBatch(1600, SpriteBatch.DEBUG_SHADER, context, GameManager.getGameManager().getCamera());
	}
	
	public void beginSpriteBatch(){
		mSpriteBatch.begin(null);
	}
	
	public void endSpriteBatch(){
		mSpriteBatch.end();
	}

	@Override
	public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f argColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
		
		mSpriteBatch.drawPolygon(vertices, vertexCount, color);
		
	}

	@Override
	public void drawCircle(Vec2 center, float radius, Color3f color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawSolidCircle(Vec2 center, float radius, Vec2 axis,
			Color3f color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
		
		mSpriteBatch.drawLine(p1, p2, color);
		
	}

	@Override
	public void drawTransform(Transform xf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawString(float x, float y, String s, Color3f color) {
		// TODO Auto-generated method stub
		
	}
	
}
