package com.detour.raw;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;

import android.opengl.GLES20;

public class B2DDebugDraw extends DebugDraw{

	public B2DDebugDraw(IViewportTransform viewport) {
		super(viewport);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawPoint(Vec2 argPoint, float argRadiusOnScreen,
			Color3f argColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
		// TODO Auto-generated method stub
		/*float[] glverts = new float[16]; //allow for polygons up to 8 vertices
		GLES20.glVertexPointer(2, GLES20.GL_FLOAT, 0, glverts); //tell OpenGL where to find vertices
		GLES20.glEnableClientState(GLES20.GL_VERTEX_ATTRIB_ARRAY_ENABLED); //use vertices in subsequent calls to glDrawArrays
	    
	    //fill in vertex positions as directed by Box2D
	    for (int i = 0; i < vertexCount; i++) {
	      glverts[i*2]   = vertices[i].x;
	      glverts[i*2+1] = vertices[i].y;
	    }
	    
	    //draw solid area
	    GLES20.glBlendColor( color.x, color.y, color.z, 1);//GLES20.glColor4f( color.x, color.y, color.z, 1);
	    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
	  
	    //draw lines
	    GLES20.glLineWidth(3); //fat lines
	    GLES20.glBlendColor( 1, 0, 1, 1 ); //purple
	    GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vertexCount);*/
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
		// TODO Auto-generated method stub
		
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
