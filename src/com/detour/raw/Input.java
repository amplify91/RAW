package com.detour.raw;

import android.util.Log;
import android.view.MotionEvent;

public class Input {
	
	GameManager mGameManager;
	
	float eventX;
	float eventY;
	float eventX2;
	float eventY2;
	
	public Input(GameManager gm){
		mGameManager = gm;
	}
	
	public void onTouchInput(MotionEvent event){
		
		eventX = event.getX(0);
		eventY = event.getY(0);
		
		eventX2 = event.getX(1);
		eventY2 = event.getY(1);
		
		if((isOnJumpButton(eventX, eventY) || isOnJumpButton(eventX2, eventY2)) && event.getAction()==MotionEvent.ACTION_DOWN){
			jump();
		}
		
	}
	
	private boolean isOnJumpButton(float eventX, float eventY){
		if(eventX>=HUD.JUMP_BUTTON_MIN_X && eventX<=HUD.JUMP_BUTTON_MAX_X && eventY>=HUD.JUMP_BUTTON_MIN_Y && eventY<=HUD.JUMP_BUTTON_MAX_Y){
			return true;
		}else{
			return false;
		}
	}
	
	private void jump(){
		mGameManager.getHero().mPhysics.jump();
		Log.i("Input", "jump!");
	}
	
	private void shoot(float x, float y){
		
	}
	
}
