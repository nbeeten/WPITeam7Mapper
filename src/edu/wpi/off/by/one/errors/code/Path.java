package edu.wpi.off.by.one.errors.code;

import java.util.ArrayList;

public class Path {
	private int startNode;
	private int endNode;
	private int[] route;
	private Node[] nodes;
	private Edge[] edges;
	
	public Path(int startNodeIn, int endNodeIn){
		startNode = startNodeIn;
		endNode = endNodeIn;
	}
	public void runAStar(Node[] nodesIn, Edge[] edgesIn){
		ArrayList<Integer> visited = new ArrayList<Integer>();
		ArrayList<Integer> toVisit = new ArrayList<Integer>();
		nodes = nodesIn;
		edges = edgesIn;
		
		
	}
	public void setStartNode(int startNodeIn){
		startNode = startNodeIn;
	}
	public void setEndNode(int endNodeIn){
		endNode = endNodeIn;
	}
	public int getStartNode(){
		return startNode;
	}
	public int getEndNode(){
		return endNode;
	}
	public int[] getRoute(){
		return route;
	}
}
