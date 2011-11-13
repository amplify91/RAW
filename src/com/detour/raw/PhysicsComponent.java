package com.detour.raw;

import android.util.Log;

public abstract class PhysicsComponent {
	
	double x = 0;
	double y = 0;
	
	PhysicsModel mModel;
	
	public PhysicsComponent(){
		mModel = new PhysicsModel(new double[][]{
				{0,0},
				{1,0},
				{1,1},
				{0,1}
		});
	}
	
	public abstract void update(GridCell gc, int i);
	
	public boolean isCollidingSAT(Sprite sprite){
		
		double[][] vertices = getVertices();
		Vector[] edges = getEdges();
		double[][] vertices2 = sprite.mPhysics.getVertices();
		Vector[] edges2 = sprite.mPhysics.getEdges();
		//int totalVertices = vertices.length + vertices2.length;
		//int totalEdges = edges.length + edges2.length;
		
		for(int i = 0;i<edges.length;i++){
			double min = Double.POSITIVE_INFINITY;
			double max = Double.NEGATIVE_INFINITY;
			double min2 = Double.POSITIVE_INFINITY;
			double max2 = Double.NEGATIVE_INFINITY;
			for(int i2 = 0;i2<vertices.length;i2++){
				double d = edges[i].projectPoint(vertices[i2][0], vertices[i2][1]);
				//Log.i("YAY!", "d: "+d+" max: "+max);
				//if(d==Double.POSITIVE_INFINITY || d==Double.NEGATIVE_INFINITY) Log.i("SHIT!"," d = "+d);
				if(d<min){min = d;}
				if(d>max){max = d;}
			}
			for(int i2 = 0;i2<vertices2.length;i2++){
				double d = edges[i].projectPoint(vertices2[i2][0], vertices2[i2][1]);
				if(d<min2){min2 = d;}
				if(d>max2){max2 = d;}
			}
			if(min<min2){
				if(max<min2){
					//Log.i("NOPE!", "min: "+min+" max: "+max+" min2: "+min2+" max2: "+max2);
					return false;
				}
			}
			if(min2<min){
				if(max2<min){
					//Log.i("NOPE2!", "min: "+min+" max: "+max+" min2: "+min2+" max2: "+max2);
					return false;
				}
			}
			//get minimum separating vector by subtracting projections
			//Log.i("YAY!", "colliding axis: min: "+min+" max: "+max+" min2: "+min2+" max2: "+max2);
		}
		for(int i = 0;i<edges2.length;i++){
			double min = Double.POSITIVE_INFINITY;
			double max = Double.NEGATIVE_INFINITY;
			double min2 = Double.POSITIVE_INFINITY;
			double max2 = Double.NEGATIVE_INFINITY;
			for(int i2 = 0;i2<vertices.length;i2++){
				double d = edges2[i].projectPoint(vertices[i2][0], vertices[i2][1]);
				if(d<min){min = d;}
				if(d>max){max = d;}
			}
			for(int i2 = 0;i2<vertices2.length;i2++){
				double d = edges2[i].projectPoint(vertices2[i2][0], vertices2[i2][1]);
				if(d<min2){min2 = d;}
				if(d>max2){max2 = d;}
			}
			if(min<min2){
				if(max<min2){
					//Log.i("NO!", "colliding axis: min: "+min+" max: "+max+" min2: "+min2+" max2: "+max2);
					return false;
				}
			}
			if(min2<min){
				if(max2<min){
					//Log.i("NO2!", "colliding axis: min: "+min+" max: "+max+" min2: "+min2+" max2: "+max2);
					return false;
				}
			}
			//get minimum separating vector by subtracting projections
			//Log.i("YAY!", "colliding axis: min: "+min+" max: "+max+" min2: "+min2+" max2: "+max2);
		}
		
		return true;
	}
	
	public abstract double getX();
	
	public abstract double getY();
	
	public abstract void setX(float x);
	
	public abstract void setY(float y);
	
	public abstract void translate(float x, float y);
	
	public abstract void scale(double sx, double sy);
	
	public abstract void jump();
	
	public double[][] getVertices(){
		return mModel.vertices;
	}
	
	public Vector[] getEdges(){
		return mModel.edges;
	}
	
}
