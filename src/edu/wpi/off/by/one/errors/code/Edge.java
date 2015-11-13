package edu.wpi.off.by.one.errors.code;

public class Edge {
	private int nodeA;
	private int nodeB;
	private int id;
	private float length;
	
	public Edge(int nodeAIn, int nodeBIn, int idIn){
		int nodeA = nodeAIn;
		int nodeB = nodeBIn;
		int id = idIn;
		//can't set length until we can access the nodes through the ID system
		float length;
		
	}
	public int getNodeA(){
		return nodeA;
	}
	public int getNodeB(){
		return nodeB;
	}
	public int getId(){
		return id;
	}
	public float getLength(){
		return length;
	}
	public void setNodeA(int nodeAIn){
		nodeA = nodeAIn;
	}
	public void setNodeB(int nodeBIn){
		nodeB = nodeBIn;
	}
	public void setId(int idIn){
		id = idIn;
	}
	public void updateLength(){
		//We need the ID system implemented before we can get nodes properly for edge length updating
	}

}
