package com.detour.raw;

public class SpriteFactory {
	
	Level mLevel;
	
	public SpriteFactory(Level level){
		mLevel = level;
	}
	
	public void createHero(float x, float y){
		Hero hero = new Hero();
		hero.create(mLevel.getWorld(), x, y);
		mLevel.addDynamicSprite(hero);
		mLevel.assignHero(hero);
	}
	
	public void createTile(int frame, float x, float y){
		Tile tile = new Tile(frame);
		//TODO
	}
	
}
