package edu.wpi.off.by.one.errors.code;

public class Edge {
	private int nodeA;//ID of Node A
	private int nodeB;//ID of Node B
	private int id;
	private float length;

	/**
	 * 
	 * @param nodeAIn: The given id of Node A
	 * @param nodeBIn: The given id of Node B
	 */
	public Edge(int nodeAIn, int nodeBIn) {
		nodeA = nodeAIn;
		nodeB = nodeBIn;
		id = -1;//default, set when added
		// can't set length until we can access the nodes through the ID system
		float length;

	}

	public int getNodeA() {
		return this.nodeA;
	}

	public int getNodeB() {
		return this.nodeB;
	}

	public int getId() {
		return this.id;
	}

	public float getLength() {
		return this.length;
	}

	/**
	 * 
	 * @param nodeAIn: The new id of NodeA
	 */
	public void setNodeA(int nodeAIn) {
		nodeA = nodeAIn;
	}

	/**
	 * 
	 * @param nodeBIn: The new id of NodeB
	 */
	public void setNodeB(int nodeBIn) {
		nodeB = nodeBIn;
	}

	/**
	 * 
	 * @param idIn: The new id of edge
	 */
	public void setId(int idIn) {
		id = idIn;
	}

	/**
	 * update the length of this edge
	 */
	public void updateLength() {
		// We need the ID system implemented before we can get nodes properly
		// for edge length updating
	}

}
