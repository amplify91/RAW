package com.detour.raw;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Sprite {
	
	private static final String LOG_TAG = GameRenderer.class.getSimpleName();
	Context mContext;
	Bitmap bitmap;
	
	int[] textures = new int[1];
	
	private float textureCoordinates[] = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f};
	
	private float vertices[] = {
		      -1.0f,  1.0f, 0.0f,
		      -1.0f, -1.0f, 0.0f,
		       1.0f, -1.0f, 0.0f,
		       1.0f,  1.0f, 0.0f,};
	
	private byte[] indices = {
			0, 1, 2,
			0, 2, 3};
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ByteBuffer indexBuffer;
	
	public Sprite(){
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(textureCoordinates.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(textureCoordinates);
		textureBuffer.position(0);

		indexBuffer = ByteBuffer.allocateDirect(indices.length);
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}
	
	public void draw(GL10 gl) {
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glFrontFace(GL10.GL_CCW);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	public void loadGLTexture(GL10 gl, Bitmap bm){
		bitmap = bm;
		
		gl.glDeleteTextures(1, textures, 0);
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
	}
	
	public void loadGLTexture(GL10 gl, Context context, int id){
		
		InputStream is = context.getResources().openRawResource(id);
		try {
			bitmap = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
			}
		}
		
		gl.glDeleteTextures(1, textures, 0);
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
		bitmap.recycle();
	}
	
}
