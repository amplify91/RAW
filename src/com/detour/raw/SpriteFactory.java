package com.detour.raw;

public class SpriteFactory {
	
	Level mLevel;
	
	public SpriteFactory(Level level){
		mLevel = level;
	}
	
	//TODO make the create functions return the object they create if you need a reference to it.
	
	public Hero createHero(float x, float y){
		Hero hero = new Hero();
		hero.create(mLevel.getWorld(), x, y);
		mLevel.addDrawableSprite(hero);
		mLevel.addUpdateableSprite(hero);
		mLevel.assignHero(hero);
		
		return hero;
	}
	
	public Tile createTile(float x, float y, int frame){
		
		Tile tile = new Tile();
		tile.create(mLevel.getWorld(), x, y, frame);
		mLevel.addDrawableSprite(tile);
		
		return tile;
	}
	
	public Projectile createProjectile(){
		Projectile projectile = new Projectile();
		projectile.create(mLevel.getWorld(), 0, 0);
		mLevel.addDrawableSprite(projectile);
		mLevel.addUpdateableSprite(projectile);
		
		return projectile;
	}
	
}
