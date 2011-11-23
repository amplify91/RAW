package com.detour.raw;

import static java.lang.Math.*;

public class Vector {
    
    //origin
    //public double mOriginX;
    //public double mOriginY;
    //rectangular form values
    public double Ax;
    public double Ay;
    //polar form values
    public double mLength;
    public double mTheta; //Radians. use Math.toDegrees() to convert.
    
    public Vector(){
    	Ax = 0;
    	Ay = 0;
    	mLength = 0;
    	mTheta = 0;
    }
    
    public Vector(double xComponentMag, double yComponentTheta, boolean polar){
    	setVector(xComponentMag, yComponentTheta, polar);
    }
    
    public Vector(double xOrigin, double yOrigin, double xEnd, double yEnd){
    	//setOrigin(xOrigin, yOrigin);
    	this(xEnd - xOrigin, yEnd - yOrigin, false);
    }
    
    private void updatePolarForm(){
    	mLength = sqrt( (Ax * Ax) + (Ay * Ay) );
    	mTheta = atan2(Ay, Ax);
    }
    
    private void updateRectangularForm(){
    	Ax = mLength * cos(mTheta);
    	Ay = mLength * sin(mTheta);
    }
    
   /* public void setOrigin(double xOrigin, double yOrigin){
    	mOriginX = xOrigin;
      	mOriginY = yOrigin;
    }*/
    
    public void setVector(double xMag, double yTheta, boolean polar){
    	if(polar){
    		mLength = xMag;
    		mTheta = yTheta;
    		updateRectangularForm();
    	}else{
    		Ax = xMag;
    		Ay = yTheta;
    		updatePolarForm();
    	}
    }
    
    void normalize(){
    	mLength = 1;
    	updateRectangularForm();
    }
    
    Vector add(Vector v){
    	return new Vector((Ax + v.Ax), (Ay + v.Ay), false);
    }
    
    Vector subtract(Vector v){
    	return new Vector((Ax - v.Ax), (Ay - v.Ay), false);
    }
    
    Vector getNormal(){
    	return new Vector(-Ay, Ax, false);
    }
    
    Vector getNormalizedVector(){
    	return new Vector(1, mTheta, true);
    }
    
    double dotProduct(Vector v){
    	return (Ax * v.Ax) + (Ay * v.Ay);
    	// OR return (mLength * v.mLength * cos(mTheta - v.mTheta));
    }
    
    boolean isParallelTo(Vector v){
    	if(abs(mTheta) == abs(v.mTheta)){ //not sure if this is mathematically true.
    		return true;
    	}else{
    		return false;
    	}
    }
    
    double projectPoint(double x, double y){
    	//The dot product of this vector's normal and the given point equals the projected value of the point (value relative to this vector, not world space). 
		return ((-Ay * x) + (Ax * y)) / mLength;
	}
    
}
