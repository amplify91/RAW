package com.detour.raw;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity{
	
	//private static final String LOG_TAG = "GameActivity";
    private GameView gameView;
    private GameLoopThread thread;
    private Input input;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        gameView = new GameView(this.getApplicationContext());
        thread = new GameLoopThread(gameView, gameView.getContext());
        input = GameManager.getGameManager().getInput();
        
        setContentView(gameView);
    }
	
	@Override
	protected void onResume(){
		super.onResume();
		gameView.onResume();
		thread.setGameRunning(true);
		thread.start();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		gameView.onPause();
		thread.setGameRunning(false);
		boolean retry = true;
		while (retry){
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		input.onTouchInput(event);
		
		return super.onTouchEvent(event);
	}
	
}
