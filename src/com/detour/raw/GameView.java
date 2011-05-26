package com.detour.raw;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GameView extends GLSurfaceView{
	
	private static final String LOG_TAG = GameView.class.getSimpleName();
    private GameRenderer renderer;

	public GameView(Context context) {
		super(context);
		renderer = new GameRenderer(context);
		setRenderer(renderer);
	}

}
