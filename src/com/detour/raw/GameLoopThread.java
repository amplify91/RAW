package com.detour.raw;

public class GameLoopThread extends Thread{
	
	public static final int TICKS_PER_SECOND = 25; //UPS
	public static final long SKIP_TICKS = 1000000000 / TICKS_PER_SECOND;
	public static final int MAX_FRAMESKIP = 5;
	
	long next_game_tick = System.nanoTime();
    int loops;
    float interpolation;
    boolean game_is_running;

    GameView gameView;
    FPSCounter fps;
	
	public GameLoopThread(GameView gv){
		super();
		gameView = gv;
		game_is_running = false;
		
		fps = new FPSCounter();
	}
	
	@Override
	public void run(){
		
	    while(game_is_running){
	        loops = 0;
	        while(System.nanoTime() > next_game_tick && loops < MAX_FRAMESKIP) {
	            updateGame();
	            next_game_tick += SKIP_TICKS;
	            loops++;
	        }
	        interpolation = ((float)(System.nanoTime() + SKIP_TICKS - next_game_tick))/((float)SKIP_TICKS);
	        displayGame(interpolation);
	    }
	}
	
	private void updateGame(){
		//update game logic (AI, Animation, Physics, Sound, etc.)
	}
	
	private void displayGame(float interpol){
		//render to the screen.
		gameView.requestRender();
		//when running at FPS faster than UPS, 
		//use the interpolation value to update the view
		//without updating the game logic between scheduled ticks.
		
		//view_position = position + (speed * interpol);
		
		//fps.calculate();
	}
	
	public void setGameRunning(boolean b){
		game_is_running = b;
	}

}
