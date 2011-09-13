package com.detour.raw;

public class HUD {
	
	//HUDButton mJumpButton;
	//HUDGraphic mRawesomeness;
	
	Camera mCamera;
	
	float originX = 0;
	float originY = 0;
	
	public static final float JUMP_BUTTON_MIN_X = 0;
	public static final float JUMP_BUTTON_MAX_X = 3;
	public static final float JUMP_BUTTON_MIN_Y = 0;
	public static final float JUMP_BUTTON_MAX_Y = 3;
	
	public static final int HUD_SPRITES = 1;
	
	public HUD(Camera c){
		mCamera = c;
		
	}
	
	public void update(float origX, float origY){
		originX = origX;
		originY = origY;
	}
	
	public void draw(SpriteBatch sb, Texture t){
		//sb.draw(t, Animation.JUMP_BUTTON, originX, originY, 1f, 1f);
	}
	
}
