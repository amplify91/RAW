package com.detour.raw;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity{
	
	private static final String LOG_TAG = GameActivity.class.getSimpleName();
    private GameView gameView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
    }

}
