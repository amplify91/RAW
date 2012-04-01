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
		originX = mCamera.getOriginX();
		originY = 0f;
	}
	
	public void update(){
		originX = mCamera.getOriginX();
		//originY = mCamera.getOriginY();
	}
	
	public void draw(SpriteBatch sb){
		sb.drawSprite(Animation.FRAME_JUMP_BUTTON, originX, originY, 2f * Sprite.SCALE_FACTOR, 2f * Sprite.SCALE_FACTOR);
	}
	
}
