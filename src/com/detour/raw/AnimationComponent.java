package com.detour.raw;

public class AnimationComponent {
	
	private boolean animating = true;
	private int mFrame = 0;
	private float nextFrame = 0;
	private float FRAME_DURATION = (float)GameLoopThread.TICKS_PER_SECOND/(float)Animation.FPS;
	private int tick = 0;
	
	private int index = 0;
	private int duration = 0;
	
	public AnimationComponent(){
		//mFrame = Animation.RUNNING_INDEX;
		nextFrame = FRAME_DURATION;
	}
	
	public void update(float deltaTime){
		 
		if(animating){
			if(tick>GameLoopThread.TICKS_PER_SECOND-1){
				tick = 0;
				nextFrame = FRAME_DURATION;
			}
			
			if(tick>nextFrame){
				nextFrame += FRAME_DURATION;
				if(mFrame < index+duration-1){
					mFrame++;
				}else{
					mFrame = index;
				}
			}
			
			tick++;
		}
		
	}
	
	public int getFrame(){
		return mFrame;
	}
	
	public void setFrame(int frame){
		mFrame = frame;
	}
	
	public void pause(){
		animating = false;
	}
	
	public void resume(){
		animating = true;
	}
	
	public void setAnimation(int animation){
		if(animation==Animation.RUNNING){
			index = Animation.FRAME_RUNNING_INDEX;
			duration = Animation.RUNNING_DURATION;
		}
	}
	
}
