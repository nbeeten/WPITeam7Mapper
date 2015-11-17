package edu.wpi.off.by.one.errors.code;

import java.util.Vector;

public class Node {
	private Coordinate coord;
	private Vector<Integer> edges;//list of indexes of edges
	private int id;
	
	/**
	 * 
	 * @param coordIn: The given coordinate
	 * @param idIn: the integer id number for the Node
	 */
	public Node(Coordinate coordIn) {
		this.edges = new Vector<Integer>();
		this.coord = coordIn;
		this.id = -1;//default, set when added
	}
	
	/**
	 * get the coordinate for the node
	 * @return the Node's coordinate
	 */
	public Coordinate getCoordinate() {
		return this.coord;
	}

	/**
	 * set the coordinate for the Node
	 * @param newCoord: The new Coordinate
	 */
	public void setCoordinate(Coordinate newCoord) {
		this.coord = newCoord;
	}
	
	public boolean addEdgeId(int id){
		return this.edges.add(id);
	}
	
	/**
	 * get the list of connected edges for the node
	 * @return edges: the list of connected edges 
	 */
	public Vector<Integer> getEdgelist() {
		return edges;
	}

	/**
	 * set the edges to the passed in list 
	 * @param newEdgeList: The new list of edges
	 */
	public void setEdgeList(Vector<Integer> newEdgeList) {
		this.edges = newEdgeList;
	}
	/**
	 *  get the Id for the node
	 * @return id: the Node's ID
	 */
	public int getId() {
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
