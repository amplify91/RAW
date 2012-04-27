package com.detour.raw;

public class SpriteFactory {
	
	Level mLevel;
	
	public SpriteFactory(Level level){
		mLevel = level;
	}
	
	public void createHero(float x, float y){
		Hero hero = new Hero();
		hero.create(mLevel.getWorld(), x, y);
		mLevel.addDrawableSprite(hero);
		mLevel.addUpdateableSprite(hero);
		mLevel.assignHero(hero);
	}
	
	public void createTile(float x, float y, int frame){
		
		Tile tile = new Tile();
		tile.create(mLevel.getWorld(), x, y, frame);
		mLevel.addDrawableSprite(tile);
	}
	
}
