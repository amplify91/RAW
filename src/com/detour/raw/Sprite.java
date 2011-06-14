package com.detour.raw;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class Sprite {
	
	//public static final int FRAME_WIDTH = 64;
	//public static final int FRAME_HEIGHT = 64;
	private static final String TAG = "Sprite";
	Context mContext;
	Bitmap bitmap;
	
	private int textureLoc;
	private int vertexLoc;
	private int[] textures = new int[1];
	//private int[] pixels;
	
	/*private float textureCoordinates[] = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f};*/
	
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
	
	public Sprite(Context context){
		
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
	
	public void draw(GL10 gl) {
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		
	}
	
	/*public void loadGLTexture(GL10 gl, Bitmap bm){
		
		GLES20.glUseProgram(program);
		
		//declare attributes and uniforms
		//int attr = glGetAttribLocation(program, attr_name); //vertex position
		textureLoc = GLES20.glGetUniformLocation(program, "u_texture"); //texture
		GLES20.glUniform1i(textureLoc, textures[0]);
		
		bitmap = bm;
		
		GLES20.glDeleteTextures(1, textures, 0);
		GLES20.glGenTextures(1, textures, 0);
		GLES20.glActiveTexture(textures[0]);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
		
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, FRAME_WIDTH, FRAME_HEIGHT, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, textureBuffer);
		
	}*/
	
	public void loadGLTexture(GL10 gl, Context context, int id){
		
		shader = new Shader(R.raw.sprite_vs, R.raw.sprite_fs, mContext);
		program = shader.getProgram();
		
		GLES20.glUseProgram(program);
		
		vertexLoc = GLES20.glGetAttribLocation(program, "a_position");
		textureLoc = GLES20.glGetUniformLocation(program, "u_texture"); //texture
		
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
		
		//pixels = new int[(bitmap.getWidth()*bitmap.getHeight())];
		//bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
		
		/*ByteBuffer byteBuf = ByteBuffer.allocateDirect(pixels.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asIntBuffer();
		textureBuffer.put(pixels);
		textureBuffer.position(0);*/
		
		//GLES20.glDeleteTextures(1, textures, 0);
		GLES20.glGenTextures(1, textures, 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
		GLES20.glUniform1i(textureLoc, 0);
		
		GLES20.glEnableVertexAttribArray(vertexLoc);
		GLES20.glVertexAttribPointer(vertexLoc, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		
		//GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, FRAME_WIDTH, FRAME_HEIGHT, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, byteBuf);//(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		
		bitmap.recycle();
		
		String program_info = GLES20.glGetProgramInfoLog(program);
		int error = GLES20.glGetError();
		//Log.d(TAG, program_info+error);
	}
	
}
