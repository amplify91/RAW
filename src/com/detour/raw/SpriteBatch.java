package com.detour.raw;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class SpriteBatch {
	
	private Texture mTexture;
	
	private float[] vertices;
	private float[] uvCoords;
	private short[] indices;
	
	private int vertexHandle;
	private int texCoordHandle;
	private int textureHandle;
	private int MVPMatrixHandle;
	
	private boolean drawing = false;
	private int prevFrame = 0;
	
	private int vi = 0;
	private int ti = 0;
	private int ii = 0;
	private int ix = 0;
	
	private float[] uvArr;
	private float u;
	private float v;
	private float u2;
	private float v2;
	
	private float mRatio;
	private float[] MVPMatrix = new float[16];
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ShortBuffer indexBuffer;
	
	public SpriteBatch(int size, int program, float ratio){
		
		mRatio = ratio;
		
		vertices = new float[size * 2 * 4];
		uvCoords = new float[size * 2 * 4];
		indices = new short[size * 6];
		
		vertexHandle = GLES20.glGetAttribLocation(program, "a_position");
		texCoordHandle = GLES20.glGetAttribLocation(program, "a_texcoord");
		textureHandle = GLES20.glGetUniformLocation(program, "u_texture");
		MVPMatrixHandle = GLES20.glGetUniformLocation(program, "u_MVPMatrix");
		
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(size * 8 * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.position(0);
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(size * 8 * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.position(0);
		
		ByteBuffer ibb = ByteBuffer.allocateDirect(size * 6 * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.position(0);
		
	}
	
	public void begin(Texture texture){
		if(drawing){
			throw new IllegalStateException("Already drawing. Call end() first.");
		}
		drawing = true;
		
		//Texture binding takes way too long for a draw call so have it take place here.
		if(texture != mTexture){
			mTexture = texture;
			texture.bind();
			prevFrame = 0;
		}
		
		vi = 0;
		ti = 0;
		ii = 0;
		ix = 0;
	}
	
	public void draw(int frame, float x, float y, float width, float height){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		
		if(frame==0){
			//invisible, don't draw
			return;
		}//TODO Make these coordinates based on hero's position, not world position.
		/*else if(x>25 || y>15 || x+(width*Sprite.SCALE_FACTOR_INV)<0 || y+(height*Sprite.SCALE_FACTOR_INV)<0){ //TODO make better
			//off screen, don't draw
			return;
		}*/
		
		x *= 2f/15f;
		y *= 2f/15f;
		x -= 1f * mRatio;
		y -= 1f;
		
		if(frame!=prevFrame){ //Small optimization. Don't load new UV data if using same frame.
			uvArr = mTexture.getFrameUV(frame);
			u = uvArr[0];
			v = uvArr[1];
			u2 = uvArr[2];
			v2 = uvArr[3];
		}
		
		vertices[vi++] = x;
		vertices[vi++] = y + height;
		uvCoords[ti++] = u;
		uvCoords[ti++] = v;
		
		vertices[vi++] = x;
		vertices[vi++] = y;
		uvCoords[ti++] = u;
		uvCoords[ti++] = v2;
		
		vertices[vi++] = x + width;
		vertices[vi++] = y;
		uvCoords[ti++] = u2;
		uvCoords[ti++] = v2;
		
		vertices[vi++] = x + width;
		vertices[vi++] = y + height;
		uvCoords[ti++] = u2;
		uvCoords[ti++] = v;
		
		indices[ii++] = (short)(0 + (ix*4));
		indices[ii++] = (short)(1 + (ix*4));
		indices[ii++] = (short)(2 + (ix*4));
		indices[ii++] = (short)(0 + (ix*4));
		indices[ii++] = (short)(2 + (ix*4));
		indices[ii++] = (short)(3 + (ix*4));
		ix++;
		
		prevFrame = frame;
	}
	
	public void end(Camera camera){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		drawing = false;
		
		MVPMatrix = camera.getMVPMatrix();
		
		fillBuffers();
		render();
		
	}
	
	private void fillBuffers(){
		
		vertexBuffer.position(0);
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		textureBuffer.position(0);
		textureBuffer.put(uvCoords);
		textureBuffer.position(0);
		indexBuffer.position(0);
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
	}
	
	private void render(){
		
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		
		GLES20.glEnableVertexAttribArray(vertexHandle);
		GLES20.glEnableVertexAttribArray(texCoordHandle);
		GLES20.glUniform1i(textureHandle, 0);
		GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
		GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
		GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, ii, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		
	}
	
}
