package com.detour.raw;

public class CollisionGrid {
	
	private int width; //TODO unnecessary?
	private int height;
	
	private GridCell[][] cell;
	
	public static final int CELL_WIDTH = 4;
	public static final int CELL_HEIGHT = 4;
	
	public CollisionGrid(int cellsWide, int cellsHigh){
		
		this.width = cellsWide;
		this.height = cellsHigh;
		
		cell = new GridCell[width][height];
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				cell[x][y] = new GridCell(x,y);
			}
		}
	}
	
	public GridCell getCell(int cellX, int cellY){
		return cell[cellX][cellY];
	}
	
	public void placeInGrid(BaseEntity b){
		if((int)b.getX() == 0 && (int)b.getY() == 0){
			getCell(0, 0).add(b);
		}else if((int)b.getX() == 0){
			getCell(0, (int)b.getY() / CELL_HEIGHT).add(b);
		}else if((int)b.getY() == 0){
			getCell((int)b.getX() / CELL_WIDTH, 0).add(b);
		}else{
			getCell((int)b.getX() / CELL_WIDTH, (int)b.getY() / CELL_HEIGHT).add(b);
		}
	}
	
	public void updateCurrentCell(BaseEntity b){
		//TODO make this better/more optimized because it's terrible right now.
		getCell(b.cellx, b.celly).remove(b);
		placeInGrid(b);
	}
	
}
