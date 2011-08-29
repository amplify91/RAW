package com.detour.raw;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity{
	
	private static final String LOG_TAG = GameActivity.class.getSimpleName();
    private GameView gameView;
    private GameLoopThread thread;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        gameView = new GameView(this.getApplicationContext());
        thread = new GameLoopThread(gameView, gameView.getContext());
        
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

}
