package com.detour.raw;

import org.jbox2d.dynamics.World;

public interface IReusable {
	
	public void spawn(World world);
	
	public void recycle();
	
}
