package com.detour.raw;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

public class RenderVisible implements RenderComponent{/*
	
	Context mContext;
	Bitmap bitmap;
	
	ArrayList<Bitmap> aFrames = new ArrayList<Bitmap>();
	
	private int program;
	private int vertexHandle;
	private int texCoordHandle;
	private int textureHandle;
	private int MVPMatrixHandle;
	
	private int[] textures = new int[1];
	
	private float textureCoordinates[] = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
            };
	
	private float vertices[] = {
			-1.0f,  1.0f,// 0.0f, 1.0f,
			-1.0f, -1.0f,// 0.0f, 1.0f,
			 1.0f, -1.0f,// 0.0f, 1.0f,
			 1.0f,  1.0f//, 0.0f, 1.0f
		     };
	
	private short[] indices = {
			0, 1, 2,
			0, 2, 3
			};
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ShortBuffer indexBuffer;
	
	private float mScaleX = 1.0f;
	private float mScaleY = 1.0f;
	//private float mRotate = 0;
	
	public RenderVisible(Context context){
		
		mContext = context;
		
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(textureCoordinates.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(textureCoordinates);
		textureBuffer.position(0);

		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
	}
	
	@Override
	public void draw(float x, float y) { //TODO draw at x,y
		
		scale(mScaleX, mScaleY);
		translate(x, y);
		
		GLES20.glEnableVertexAttribArray(vertexHandle);
		GLES20.glEnableVertexAttribArray(texCoordHandle);
		GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
		GLES20.glUniform1i(textureHandle, 0);
		GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, Camera.getMVPMatrix(), 0);
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		
		//GLES20.glDisableVertexAttribArray(vertexHandle);
		//GLES20.glDisableVertexAttribArray(texCoordHandle);
		
	}

	@Override
	public void loadGLTexture(int id){
		
		vertexHandle = GLES20.glGetAttribLocation(program, "a_position");
		texCoordHandle = GLES20.glGetAttribLocation(program, "a_texcoord");
		textureHandle = GLES20.glGetUniformLocation(program, "u_texture");
		MVPMatrixHandle = GLES20.glGetUniformLocation(program, "u_MVPMatrix");
		
		
		
		GLES20.glGenTextures(1, textures, 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
		
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		
		if(id!=0){
			Options opts = new Options();
			opts.inScaled = false; //Trying to not allow Android to do its stupid screen density pixel fucking.
			bitmap = BitmapFactory.decodeResource(mContext.getResources(), id, opts);
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
			bitmap.recycle();
		}
		
	}
	
	public void createAnitmationFrames(int id, int frameWidth, int frameHeight, int program){
		
		loadGLTexture(0);
		
		Options opts = new Options();
		opts.inScaled = false; //Trying to not allow Android to do its stupid screen density pixel fucking.
		bitmap = BitmapFactory.decodeResource(mContext.getResources(), id, opts );
		
		int framesWide = bitmap.getWidth()/frameWidth;
		int framesHigh = bitmap.getHeight()/frameHeight;
		int numberOfFrames = framesWide*framesHigh;
		int x = 0;
		int y = 0;
		
		for(int i=0; i<numberOfFrames;i++){
			if(i>=framesWide){
				x = (i%framesWide)*frameWidth;
				y = (i/framesWide)*frameHeight;
			}else{
				x = i*frameWidth;
				y = 0;
			}
			Bitmap b = Bitmap.createBitmap(bitmap, x, y, frameWidth, frameHeight);
			aFrames.add(b);
		}
		
		bitmap.recycle();
		
	}
	
	public void selectFrame(int frameIndex){
		
		GLES20.glGenTextures(1, textures, 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
		
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		
		//GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, FRAME_WIDTH, FRAME_HEIGHT, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, byteBuf);//(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, aFrames.get(frameIndex), 0);
		
	}
	
	public void setProgram(int program){
		this.program = program;
	}

	@Override
	public void scale(float sx, float sy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void translate(float tx, float ty) {
		// TODO Auto-generated method stub
		
	}
	
*/}
