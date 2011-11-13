package com.detour.raw;

public class PhysicsModel {
	
	public double[][] vertices;
	public Vector[] edges;
	
	private int i = 0;
	
	public PhysicsModel(double[][] vertices){
		this.vertices = vertices;
		updateEdges();
	}
	
	private void updateEdges(){
		edges = new Vector[vertices.length];
		for(int i = 0;i<vertices.length;i++){
			edges[i] = new Vector(vertices[i][0], vertices[i][1], false);
		}
	}
	
	public void scale(double sx, double sy){
		for(i = 1;i<vertices.length;i++){
			vertices[i][0] *= sx;
			vertices[i][1] *= sy;
		}
		updateEdges();
	}
	
	public void translate(double tx, double ty){
		for(i = 0;i<vertices.length;i++){
			vertices[i][0] += tx;
			vertices[i][1] += ty;
		}
	}
	
}
