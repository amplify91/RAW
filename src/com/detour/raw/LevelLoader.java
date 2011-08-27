package com.detour.raw;

import java.util.Random;

import android.content.Context;

public class LevelLoader {
	
	Context mContext;
	int program;
	
	public LevelLoader(Context c, int program, int level){
		mContext = c;
		this.program = program;
	}
	
	public Tile[][] getTileMap(){
		Tile[][] tiles = new Tile[15][100];
		
		Random rand = new Random();
		
		for(int y=0;y<tiles.length;y++){
			for(int x=0;x<tiles[y].length;x++){
				tiles[y][x] = new Tile(mContext, GameRenderer.getScreenRatio());
				tiles[y][x].setProgram(program);
				tiles[y][x].loadGLTexture((getRandomPlaceholder(rand.nextInt(4))));
				tiles[y][x].translate(x, y);
			}
		}
		
		return tiles;
	}
	
	int getRandomPlaceholder(int x){
		int id = 0;
		
		if(x==0){
			id = R.drawable.t9;
		}else if(x==1){
			id = R.drawable.t91;
		}else if(x==2){
			id = R.drawable.t92;
		}else{
			id = R.drawable.t93;
		}
		
		return id;
	}
	
}
