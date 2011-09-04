package com.detour.raw;

import android.view.MotionEvent;

public class Input {
	
	public Input(){
		
	}
	
	public void onTouchInput(MotionEvent event){
		
		if(event.getAction()==MotionEvent.ACTION_UP){
			jump();
		}
		
	}
	
	private void jump(){
		GameManager.getGameManager().getHero().translate(0, 2);
	}
	
	private void shoot(float x, float y){
		
	}
	
}
