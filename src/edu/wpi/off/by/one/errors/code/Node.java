package edu.wpi.off.by.one.errors.code;

public class Node {
	private Coordinate coord;
	private int[] edges;//list of indexes of edges
	private int id;

	/**
	 * 
	 * @param coordIn: The given coordinate
	 * @param idIn: the integer id number for the Node
	 */
	public Node(Coordinate coordIn, int idIn) {
		coord = coordIn;
		id = idIn;//default, set when added
	}
	
	/**
	 * get the coordinate for the node
	 * @return the Node's coordinate
	 */
	public Coordinate getCoordinate() {
		return coord;
	}

	/**
	 * set the coordinate for the Node
	 * @param newCoord: The new Coordinate
	 */
	public void setCoordinate(Coordinate newCoord) {
		coord = newCoord;
	}
	
	/**
	 * get the list of connected edges for the node
	 * @return edges: the list of connected edges 
	 */
	public int[] getEdgelist() {
		return edges;
	}

	/**
	 * set the edges to the passed in list 
	 * @param newEdgeList: The new list of edges
	 */
	public void setEdgeList(int[] newEdgeList) {
		edges = newEdgeList;
	}
	/**
	 *  get the Id for the node
	 * @return id: the Node's ID
	 */
	public int getId() {
		System.out.println(this.id);
		return id;
	}

	/**
	 * setter for the Node's id
	 * @param idIn: the new id
	 */
	public void setId(int idIn) {
		id = idIn;
	}
}
