package com.detour.raw;

import android.view.MotionEvent;

public class Input {
	
	public Input(){
		
	}
	
	public void onTouchInput(MotionEvent event){
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			jump();
		}
		
	}
	
	private void jump(){
		GameManager.getGameManager().getHero().mPhysics.jump();
	}
	
	private void shoot(float x, float y){
		
	}
	
}
