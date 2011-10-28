package com.detour.raw;

import android.util.Log;
import android.view.MotionEvent;

public class Input {
	
	GameManager mGameManager;
	
	float eventX;
	float eventY;
	float eventX2;
	float eventY2;
	
	int mPixelWidth = 0;
	int mPixelHeight = 0;
	float mScaleX;
	float mScaleY;
	
	public Input(GameManager gm){
		mGameManager = gm;
        
	}
	
	public boolean onTouchInput(MotionEvent event){
		
		eventX = event.getX(0);
		eventY = event.getY(0);
		
		eventX2 = event.getX(1);
		eventY2 = event.getY(1);
		
		//Log.i("Touch", "x = "+eventX+" y = "+eventY+" x2 = "+eventX2+" y2 = "+eventY2);
		//Log.i("Touch", "yMin = "+(mPixelHeight-(HUD.JUMP_BUTTON_MIN_Y*(mPixelHeight/15)))+" yMax = "+(mPixelHeight-(HUD.JUMP_BUTTON_MAX_Y*(mPixelHeight/15))));
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			if(isOnJumpButton(eventX, eventY) || isOnJumpButton(eventX2, eventY2)){
				jump();
			}
		}else{
			return false;
		}
		
		return true;
		
	}
	
	private boolean isOnJumpButton(float eventX, float eventY){
		if(eventX>=HUD.JUMP_BUTTON_MIN_X*mScaleX && eventX<=HUD.JUMP_BUTTON_MAX_X*mScaleX && 
				eventY<=mPixelHeight-(HUD.JUMP_BUTTON_MIN_Y*mScaleY) && eventY>=mPixelHeight-(HUD.JUMP_BUTTON_MAX_Y*mScaleY)){
			return true;
		}else{
			return false;
		}
	}
	
	private void jump(){
		mGameManager.getHero().mPhysics.jump();
	}
	
	private void shoot(float x, float y){
		
	}
	
	public void setPixelDimensions(int pixelWidth, int pixelHeight){
		mPixelWidth = pixelWidth;
		mPixelHeight = pixelHeight;
		mScaleY = mPixelHeight / 15;
		mScaleX = mPixelWidth / (15 * ((float)mPixelWidth / (float)mPixelHeight));
	}
	
}
