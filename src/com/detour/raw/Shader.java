package com.detour.raw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class Shader {
	
	public static final String TAG = "Shader";	
	int program;
	int vertexShader;
	int fragmentShader;
	
	String vShaderSource;
	String fShaderSource;
	
	public Shader(){
		//blank constructor
		//createProgram();
	}
	
	public Shader(String vs_source, String fs_source){
		this.vShaderSource = vs_source;
		this.fShaderSource = fs_source;
		
		createProgram();
	}
	
	public Shader(int vs_source_id, int fs_source_id, Context context) {
		
		StringBuffer vs = new StringBuffer();
		StringBuffer fs = new StringBuffer();
		
		try{
			InputStream inputStream = context.getResources().openRawResource(vs_source_id);
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream), inputStream.toString().length());
			
			String read = in.readLine();
			while (read != null) {
				vs.append(read + "\n");
				read = in.readLine();
			}
			vs.deleteCharAt(vs.length() - 1);
			
			inputStream = context.getResources().openRawResource(fs_source_id);
			in = new BufferedReader(new InputStreamReader(inputStream), inputStream.toString().length());
			
			read = in.readLine();
			while (read != null) {
				fs.append(read + "\n");
				read = in.readLine();
			}
			fs.deleteCharAt(fs.length() - 1);
		}catch (Exception e){
			Log.d("ERROR-readingShader", "Could not read shader: " + e.getLocalizedMessage());
		}
		
		this.vShaderSource = vs.toString();
		//Log.i(TAG, vShaderSource);
		this.fShaderSource = fs.toString();
		//Log.i(TAG, fShaderSource);
		
		createProgram();
	}
	
	private void createProgram(){
		
		program = GLES20.glCreateProgram();
		if(program!=0){
			vertexShader = createShader(GLES20.GL_VERTEX_SHADER, vShaderSource);
			fragmentShader = createShader(GLES20.GL_FRAGMENT_SHADER, fShaderSource);
			
			if(vertexShader!=0){
				GLES20.glAttachShader(program, vertexShader);
				
			}
			String vs_info = GLES20.glGetShaderInfoLog(vertexShader);
			Log.d(TAG, vs_info);
			
			if(fragmentShader!=0){
				GLES20.glAttachShader(program, fragmentShader);
			}
			String fs_info = GLES20.glGetShaderInfoLog(fragmentShader);
			Log.d(TAG, fs_info);
			
			GLES20.glLinkProgram(program);
		}else{
			Log.e(TAG, "Couldn't create program.");
		}
		String p_info = GLES20.glGetProgramInfoLog(program);
		Log.d(TAG, p_info);
		
		
	}
	
	private int createShader(int type, String source){
		int shader = GLES20.glCreateShader(type);
		if(shader!=0){
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
		}
		
		return shader;
	}
	
	public int getProgram(){
		return program;
	}
	
	public void setProgram(int p){
		this.program = p;
	}
	
	public int getVertexShader(){
		return vertexShader;
	}
	
	public void setVertexShader(int vs){
		this.vertexShader = vs;
	}
	
	public int getFragmentShader(){
		return fragmentShader;
	}
	
	public void setFragmentShader(int fs){
		this.fragmentShader = fs;
	}
	
}
