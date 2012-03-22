package com.detour.raw;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;

import android.content.Context;
import android.opengl.GLES20;

public class SpriteBatch {
	
	private Texture mTexture;
	private int mProgram;
	
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
	
	public static final int SPRITE_SHADER = 0;
	public static final int DEBUG_SHADER = 1;
	
	public SpriteBatch(int size, int shader, float ratio, Context context){
		
		mRatio = ratio;
		
		if(shader == SPRITE_SHADER){
			Shader s = new Shader(R.raw.sprite_vs, R.raw.sprite_fs, context);
			mProgram = s.getProgram();
			
			vertices = new float[size * 2 * 4];
			uvCoords = new float[size * 2 * 4];
			indices = new short[size * 6];
			
			vertexHandle = GLES20.glGetAttribLocation(mProgram, "a_position");
			texCoordHandle = GLES20.glGetAttribLocation(mProgram, "a_texcoord");
			textureHandle = GLES20.glGetUniformLocation(mProgram, "u_texture");
			MVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
			
			ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * (Float.SIZE / Byte.SIZE));
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer = vbb.asFloatBuffer();
			vertexBuffer.position(0);
			
			ByteBuffer byteBuf = ByteBuffer.allocateDirect(uvCoords.length * (Float.SIZE / Byte.SIZE));
			byteBuf.order(ByteOrder.nativeOrder());
			textureBuffer = byteBuf.asFloatBuffer();
			textureBuffer.position(0);
			
			ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * (Short.SIZE / Byte.SIZE));
			ibb.order(ByteOrder.nativeOrder());
			indexBuffer = ibb.asShortBuffer();
			indexBuffer.position(0);
		}else if(shader == DEBUG_SHADER){
			//TODO debug shader code
		}else{
			throw new IllegalArgumentException("Invalid shader type.");
		}
		
	}
	
	public void begin(Texture texture){
		if(drawing){
			throw new IllegalStateException("Already drawing. Call end() first.");
		}
		drawing = true;
		
		//Texture binding takes way too long for a draw call so have it take place here.
		if(texture != null && texture != mTexture){
			mTexture = texture;
			texture.bind();
			prevFrame = 0;
		}
		
		GLES20.glUseProgram(mProgram);
		
		vi = 0;
		ti = 0;
		ii = 0;
		ix = 0;
	}
	
	public void drawSprite(int frame, float x, float y, float width, float height){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		
		if(frame==0){
			//invisible, don't draw
			return;
		}
		
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
	
	public void drawPolygon(Vec2[] vertices, int vertexCount, Color3f color){
		//TODO
	}
	
	public void end(Camera camera){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		drawing = false;
		
		MVPMatrix = camera.getMVPMatrix();
		
		fillBuffers();
		render();
		
		//Log.i("SpriteBatch", "ix = "+ix);
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
		GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
		GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
		GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, ii, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		
	}
	
}
