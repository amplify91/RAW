package com.detour.raw;

public class AnimationComponent {
	
	private int mFrame = 0;
	private float nextFrame = 0;
	private float FRAME_DURATION = (float)GameLoopThread.TICKS_PER_SECOND/(float)Animation.FPS;
	private int tick = 0;
	
	public AnimationComponent(){
		mFrame = Animation.RUNNING_INDEX;
		nextFrame = FRAME_DURATION;
	}
	
	public void update(int speed){
		 
		if(tick>GameLoopThread.TICKS_PER_SECOND-1){
			tick = 0;
			nextFrame = FRAME_DURATION;
		}
		
		if(tick>nextFrame){
			nextFrame += FRAME_DURATION;
			/*if(mFrame < Animation.RUNNING_DURATION+Animation.RUNNING_INDEX-1){
				mFrame++;
			}else{
				mFrame = Animation.RUNNING_INDEX;
			}*/
			if(mFrame>1){
				mFrame--;
			}else{
				mFrame = 8;
			}
		}
		tick++;
	}
	
	public int getFrame(){
		return mFrame;
	}
	
	public void setFrame(int frame){
		mFrame = frame;
	}
	
}
