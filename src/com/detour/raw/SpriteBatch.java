package com.detour.raw;

import android.opengl.GLES20;

public class SpriteBatch {
	
	private float[] vertices;
	
	private boolean drawing = false;
	private int i = 0;
	
	public SpriteBatch(int size){
		
		vertices = new float[size * Sprite.SPRITE_SIZE];
		
	}
	
	public void begin(){
		if(drawing){
			throw new IllegalStateException("Already drawing. Call end() first.");
		}
		drawing = true;
		
		i = 0;
	}
	
	public void draw(Texture texture, int frame, float x, float y, int width, int height){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		
		float[] uvArr = texture.getFrameUV(frame);
		float u = uvArr[0];
		float v = uvArr[1];
		float u2 = uvArr[2];
		float v2 = uvArr[3];
		
		vertices[i++] = x;
		vertices[i++] = y + height;
		vertices[i++] = u;
		vertices[i++] = v2;
		
		vertices[i++] = x;
		vertices[i++] = y;
		vertices[i++] = u;
		vertices[i++] = v;
		
		vertices[i++] = x + width;
		vertices[i++] = y;
		vertices[i++] = u2;
		vertices[i++] = v;
		
		vertices[i++] = x + width;
		vertices[i++] = y + height;
		vertices[i++] = u2;
		vertices[i++] = v2;
	}
	
	public void end(){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		drawing = false;
		
		//TODO add shader and other OpenGLES20 stuff. not necessarily right here.
		//render - 
		
		/*GLES20.glEnableVertexAttribArray(vertexHandle);
		GLES20.glEnableVertexAttribArray(texCoordHandle);
		GLES20.glUniform1i(textureHandle, 0);
		GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mMVPMatrix, 0);
		GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
		
		GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, vertices);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length);*/
		
	}
	
}
