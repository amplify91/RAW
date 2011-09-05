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
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ShortBuffer indexBuffer;
	
	private float[] mModelMatrix = new float[16];
	private float[] mMVMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	
	public SpriteBatch(int size, int program){
		
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
		
		Matrix.setIdentityM(mModelMatrix, 0);
		Matrix.multiplyMM(mMVMatrix, 0, GameRenderer.getViewMatrix(), 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, GameRenderer.getProjMatrix(), 0, mMVMatrix, 0);
		
	}
	
	public void begin(){
		if(drawing){
			throw new IllegalStateException("Already drawing. Call end() first.");
		}
		drawing = true;
		
		vi = 0;
		ti = 0;
		ii = 0;
		ix = 0;
	}
	
	public void draw(Texture texture, int frame, float x, float y, float width, float height){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		
		if(texture != mTexture){
			mTexture = texture;
			texture.bind();
			prevFrame = 0;
		}
		
		if(frame==0){
			//invisible, don't draw
			return;
		}else if(x+width+1<0 || x>25 || y+height+1<0 || y>15){ //TODO make better
			//off screen, don't draw
			return;
		}
		
		x *= 2f/15f;
		y *= 2f/15f;
		x -= 1f * GameRenderer.getScreenRatio();
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
	
	public void end(){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		drawing = false;
		
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
		
		GLES20.glEnableVertexAttribArray(vertexHandle);
		GLES20.glEnableVertexAttribArray(texCoordHandle);
		GLES20.glUniform1i(textureHandle, 0);
		GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mMVPMatrix, 0);
		GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
		GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, ii, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		
	}
	
}
