package com.detour.raw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.jbox2d.dynamics.World;

import android.content.Context;
import android.util.Log;

public class LevelLoader {
	
	/*
	 * A class for accessing level files, parsing them, loading them,
	 * and returning the contents of a level to the GameManager.
	 * 
	 * It should only need a few methods:
	 * createLevel(levelnumber), //should possibly be called in the constructor
	 * getLevel(), and
	 * getLevelItem() (like getTiles() or getEnemies()).
	 */
	
	Context mContext;
	int levelWidth = 0;
	int levelHeight = 0;
	int sprites = 0;
	
	Tile[] tiles;
	
	public LevelLoader(Context c, int lvln, Level level){
		mContext = c;
		if(lvln==0){
			//createRandomTileMap(); TODO
		}else{
			createLevelFromFile(mContext, lvln, level);
		}
		
		sprites += HUD.HUD_SPRITES;
	}
	
	public Tile[] getTileMap(){
		return tiles;
	}
	
	public int getNumberOfSprites(){
		return sprites;
	}
	
	/*private void createRandomTileMap(){ TODO
		sprites = 0;
		levelWidth = 2500;
		levelHeight = 20;
		Tile[] ph_tiles = new Tile[levelWidth*levelHeight];
		
		Random rand = new Random();
		
		int chance;
		
		for(int y=0;y<levelHeight;y++){
			for(int x=0;x<levelWidth;x++){
				chance = rand.nextInt(8);
				if(chance==0){
					ph_tiles[sprites] = new Tile(rand.nextInt(91)+1,x,y);
					sprites++;
				}
			}
		}
		
		tiles = new Tile[sprites];
		for(int i = 0;i<sprites;i++){
			tiles[i] = ph_tiles[i];
		}
		ph_tiles = null;
	}*/
	
	public void createLevelFromFile(Context context, int ln, Level level){
		int fileId = getFileName(ln);
		//String file;
		//int[][] pieceInfo = null;
		//int[][] grid = new int[1][1];
		levelWidth = 0;
		levelHeight = 0;
		//int[][][] levelInfo = null;
		String line1 = null;
		String line4 = null;
		String line5 = null;
		//String line6 = null;
		
		try{
			InputStream inputStream = context.getResources().openRawResource(fileId);
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream), inputStream.toString().length());
			
			String line = in.readLine();
			int x = 1;
			while (line != null) {
				if(x==1){
					line1 = line;
				}else if(x==4){
					line4 = line;
				}else if(x==5){
					line5 = line;
				}/*else if(x==6){
					line6 = read;
				}*/
				line = in.readLine();
				x++;
			}
			
		}catch (Exception e){
			Log.d("ERROR-readingLevelFile", "Could not read file: " + e.getLocalizedMessage());
		}
		
		if(line1!=null){
			String[] str = line1.split("[ ]");
			levelWidth = Integer.parseInt(str[0]);
			levelHeight = Integer.parseInt(str[1]);
			//grid = new int[levelHeight][levelWidth];
			/*for(int y=0;y<levelHeight;y++){
				for(int x=0;x<levelWidth;x++){
					grid[y][x] = 0;
				}
			}*/ //Not necessary
		}
		
		//tiles = new Tile[levelHeight][levelWidth];
		
		if(line4!=null){
			String[] str = line4.split("[ ]");
			int[] gridArr = new int[(levelWidth*levelHeight)];
			if(str.length!=gridArr.length){
				Log.d("Levels", "Not correctly parsing level file. Check split() method");
			}
			//int tile;
			for(int x=0;x<gridArr.length;x++){
				gridArr[x] = Integer.parseInt(str[x]);
			}
			
			sprites = 0;
			int i = 0;
			Tile[] ph_tiles = new Tile[gridArr.length];
			for(int y=levelHeight-1;y>-1;y--){
				for(int x=0;x<levelWidth;x++){
					if(gridArr[i]!=0){
						ph_tiles[sprites] = createTile(gridArr[i], x, y, level);
						sprites++;
					}
					i++;
				}
			}
			
			tiles = new Tile[sprites];
			for(int i2 = 0;i2<sprites;i2++){
				tiles[i2] = ph_tiles[i2];
			}
			ph_tiles = null;
			
		}
		
		/*int numberOfPieces = 0;
		if(line5!=null){
			String[] str = line5.split("[ ]");
			for(int x=0;x<str.length;x++){
				if(((int)(Double.parseDouble(str[x])))!=0){
					numberOfPieces++;
				}
			}
			int[][] pieces = new int[numberOfPieces+1][3];
			pieces[pieces.length-1] = new int[]{0};
			
			int currentPiece = 0;
			for(int x=0;x<str.length;x++){
				int pieceNum = (int)(Double.parseDouble(str[x]));
				if(pieceNum!=0){ //TOD O try to just fix the level editor instead of this hack-ery.
					if(pieceNum==1||pieceNum==3){
						if(x<levelWidth){
							pieces[currentPiece][0] = this.x[x];
							pieces[currentPiece][1] = this.y[0-1];
						}else{
							pieces[currentPiece][0] = this.x[x%levelWidth];
							pieces[currentPiece][1] = this.y[(x/levelWidth)-1];
						}
					}else if(pieceNum==2){
						if(x<levelWidth){
							pieces[currentPiece][0] = this.x[x];
							pieces[currentPiece][1] = this.y[0-2];
						}else{
							pieces[currentPiece][0] = this.x[x%levelWidth];
							pieces[currentPiece][1] = this.y[(x/levelWidth)-2];
						}
					}else if(pieceNum==4||pieceNum==6){
						if(x<levelWidth){
							pieces[currentPiece][0] = this.x[x-1];
							pieces[currentPiece][1] = this.y[0];
						}else{
							pieces[currentPiece][0] = this.x[(x%levelWidth)-1];
							pieces[currentPiece][1] = this.y[x/levelWidth];
						}
					}else if(pieceNum==5){
						if(x<levelWidth){
							pieces[currentPiece][0] = this.x[x-2];
							pieces[currentPiece][1] = this.y[0];
						}else{
							pieces[currentPiece][0] = this.x[(x%levelWidth)-2];
							pieces[currentPiece][1] = this.y[x/levelWidth];
						}
					}
					
					pieces[currentPiece][2] = pieceNum;
					currentPiece++;
				}
			}
			
			pieceInfo = pieces;
			setNeedsRandomized(true);
			randomizePieceBitmaps(pieceInfo);
		}*/
		
		/*if(pieceInfo!=null&&grid!=null){
			levelInfo = new int[][][]{pieceInfo,grid};
		}*/
		
		//return levelInfo;
		
	}
	
	private Tile createTile(int frame, float x, float y, Level level) {
		int frame2 = convertTileFrame(frame);
		Tile t = new Tile(frame2);
		level.create(t, x, y, 0.5f, 0.5f, false);
		return t;
	}
	
	private int convertTileFrame(int f){
		int frame = 0;
		if(f>0&&f<65){
			frame = Animation.FRAME_TEST_TILES[f-1];
		}else{
			frame = f;
		}
		return frame;
	}

	private int getFileName(int ln){
		int levelID = 0;
		
		if(ln==1){
			levelID = R.raw.rawtestlevel1;
		}
		
		return levelID;
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
