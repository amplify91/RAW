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
	
	private float[] vertices;
	private short[] indices;
	private float[] uvCoords;
	private float[] colors;
	
	private int vertexHandle;
	private int textureHandle;
	private int texCoordHandle;
	private int colorHandle;
	private int MVPMatrixHandle;
	
	private boolean drawing = false;
	private int prevFrame = 0;
	
	private int vi = 0;
	private int ii = 0;
	private int ti = 0;
	private int ci = 0;
	private int ix = 0;
	
	private float[] uvArr;
	private float u;
	private float v;
	private float u2;
	private float v2;
	
	private float mRatio;
	private float[] MVPMatrix = new float[16];
	
	private FloatBuffer vertexBuffer;
	private ShortBuffer indexBuffer;
	private FloatBuffer textureBuffer;
	private FloatBuffer colorBuffer;

	private int mProgram;
	private int mShaderType;
	private int mDrawType = 0;
	
	public static final int SPRITE_SHADER = 0;
	public static final int DEBUG_SHADER = 1;
	private static final int DRAW_SPRITE = 1;
	private static final int DRAW_POLYGON = 2;
	private static final int DRAW_LINE = 3;
	
	public SpriteBatch(int size, int shaderType, float ratio, Context context){
		
		mRatio = ratio;
		mShaderType = shaderType;
		
		if(mShaderType == SPRITE_SHADER){
			Shader shader = new Shader(R.raw.sprite_vs, R.raw.sprite_fs, context);
			mProgram = shader.getProgram();
			
			uvCoords = new float[size * 2 * 4];
			
			texCoordHandle = GLES20.glGetAttribLocation(mProgram, "a_texcoord");
			textureHandle = GLES20.glGetUniformLocation(mProgram, "u_texture");
			
			ByteBuffer tbb = ByteBuffer.allocateDirect(uvCoords.length * (Float.SIZE / Byte.SIZE));
			tbb.order(ByteOrder.nativeOrder());
			textureBuffer = tbb.asFloatBuffer();
			textureBuffer.position(0);
		}else if(shaderType == DEBUG_SHADER){
			Shader shader = new Shader(R.raw.debug_vs, R.raw.debug_fs, context);
			mProgram = shader.getProgram();
			
			colors = new float[size * 3];
			
			colorHandle = GLES20.glGetAttribLocation(mProgram, "a_color");
			
			ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * (Float.SIZE / Byte.SIZE));
			cbb.order(ByteOrder.nativeOrder());
			colorBuffer = cbb.asFloatBuffer();
			colorBuffer.position(0);
		}else{
			throw new IllegalArgumentException("Invalid shader type.");
		}
		
		vertices = new float[size * 2 * 4];
		indices = new short[size * 6];
		
		vertexHandle = GLES20.glGetAttribLocation(mProgram, "a_position");
		MVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * (Float.SIZE / Byte.SIZE));
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.position(0);
		
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * (Short.SIZE / Byte.SIZE));
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
		if(texture != null && texture != mTexture){
			mTexture = texture;
			texture.bind();
			prevFrame = 0;
		}
		
		GLES20.glUseProgram(mProgram);
		
		vi = 0;
		ii = 0;
		ti = 0;
		ci = 0;
		ix = 0;
	}
	
	public void drawSprite(int frame, float x, float y, float width, float height){
		if(!drawing){
			throw new IllegalStateException("Haven't started drawing. Call begin() first.");
		}
		if(mShaderType != SPRITE_SHADER){
			throw new IllegalStateException("Can't draw sprite without sprite shader.");
		}
		if(mDrawType==0){
			mDrawType = DRAW_SPRITE;
		}else if(mDrawType!=DRAW_SPRITE){
			//TODO end() then begin() and continue
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
		
		indices[ii++] = (short)(0 + ix);
		indices[ii++] = (short)(1 + ix);
		indices[ii++] = (short)(2 + ix);
		indices[ii++] = (short)(0 + ix);
		indices[ii++] = (short)(2 + ix);
		indices[ii++] = (short)(3 + ix);
		ix += 4;
		
		prevFrame = frame;
	}
	
	public void drawPolygon(Vec2[] vertices, int vertexCount, Color3f color){
		
		if(mShaderType != DEBUG_SHADER){
			throw new IllegalStateException("Can't draw debug without debug shader.");
		}
		if(mDrawType==0){
			mDrawType = DRAW_POLYGON;
		}else if(mDrawType!=DRAW_POLYGON){
			//TODO end() then begin() and continue
		}
		
	    //fill in vertex positions as directed by Box2D
	    for (int i = 0; i < vertexCount; i++) {
	      this.vertices[vi++]   = (vertices[i].x * 2f/15f) - (1f * mRatio);
	      this.vertices[vi++] = (vertices[i].y * 2f/15f) - 1f;
	    }
	    
	    for(int i=0;i<vertexCount-2;i++){
	    	indices[ii++] = (short)(0 + ix);
			indices[ii++] = (short)((i+1) + ix);
			indices[ii++] = (short)((i+2) + ix);
	    }
	    ix += vertexCount;
	    
	    colors[ci++] = color.x;
	    colors[ci++] = color.y;
	    colors[ci++] = color.z;
	}
	
	public void drawLine(Vec2 p1, Vec2 p2, Color3f color){
		if(mShaderType != DEBUG_SHADER){
			throw new IllegalStateException("Can't draw debug without debug shader.");
		}
		if(mDrawType==0){
			mDrawType = DRAW_LINE;
		}else if(mDrawType!=DRAW_LINE){
			//TODO end() then begin() and continue
		}
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
		
		mDrawType = 0;
		
		//Log.i("SpriteBatch", "ix = "+ix);
	}
	
	private void fillBuffers(){
		
		vertexBuffer.position(0);
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		if(mShaderType==SPRITE_SHADER){
			textureBuffer.position(0);
			textureBuffer.put(uvCoords);
			textureBuffer.position(0);
		}else if(mShaderType==DEBUG_SHADER){
			colorBuffer.position(0);
			colorBuffer.put(colors);
			colorBuffer.position(0);
		}
		indexBuffer.position(0);
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
	}
	
	private void render(){
		
		if(mDrawType==DRAW_SPRITE || mDrawType==DRAW_POLYGON){
			if(mShaderType==SPRITE_SHADER){
				GLES20.glEnableVertexAttribArray(texCoordHandle);
				GLES20.glUniform1i(textureHandle, 0);
				GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
			}else if(mShaderType==DEBUG_SHADER){
				GLES20.glEnableVertexAttribArray(colorHandle);
				GLES20.glVertexAttribPointer(colorHandle, 3/*2*/, GLES20.GL_FLOAT, false, 0, colorBuffer);
			}
			GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
			GLES20.glEnableVertexAttribArray(vertexHandle);
			GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
			
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, ii, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		}else if(mDrawType==DRAW_LINE){
			GLES20.glEnableVertexAttribArray(colorHandle);
			GLES20.glVertexAttribPointer(colorHandle, 3/*2*/, GLES20.GL_FLOAT, false, 0, colorBuffer);
			
			GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
			GLES20.glEnableVertexAttribArray(vertexHandle);
			GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
			
			GLES20.glDrawArrays(GLES20.GL_LINES, 0, ii);
		}
		
	}
	
}
