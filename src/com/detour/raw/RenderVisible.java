package com.detour.raw;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class RenderVisible implements Renderable{
	
	Context mContext;
	Bitmap bitmap;
	
	private int textureHandle;
	private int vertexHandle;
	
	private int[] textures = new int[1];
	
	private float vertices[] = {
			-1.0f,  1.0f,// 0.0f,
			-1.0f, -1.0f,// 0.0f,
			 1.0f, -1.0f,// 0.0f,
			 1.0f,  1.0f// 0.0f,
		     };
	
	private short[] indices = {
			0, 1, 2,
			0, 2, 3};
	
	private FloatBuffer vertexBuffer;
	//private IntBuffer textureBuffer;
	private ShortBuffer indexBuffer;
	
	Shader shader;
	int program;
	String vShaderSource = "";
	String fShaderSource = "";
	
	public RenderVisible(Context context){
		
		mContext = context;
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		

		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
	}
	
	@Override
	public void draw() {
		
		GLES20.glEnableVertexAttribArray(vertexHandle);
		GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		
	}

	@Override
	public void loadGLTexture(int id, int program) {
		
		vertexHandle = GLES20.glGetAttribLocation(program, "a_position");
		textureHandle = GLES20.glGetUniformLocation(program, "u_texture"); //texture
		
		bitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
		/*InputStream is = mContext.getResources().openRawResource(id);
		try {
			bitmap = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
			}
		}*/
		
		GLES20.glGenTextures(1, textures, 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
		GLES20.glUniform1i(textureHandle, 0);
		//GLES20.glUniformMatrix4fv(location, count, transpose, value);
		
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		
		//GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, FRAME_WIDTH, FRAME_HEIGHT, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, byteBuf);//(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		
		bitmap.recycle();
	}

}
