package edu.wpi.off.by.one.errors.code;

public class Node {
	private Coordinate coord;
	private int[] edges;//list of indexes of edges
	private int id;
	
	/**
	 * 
	 * @param coordIn: The given coordinate
	 */
	public Node(Coordinate coordIn) {
		coord = coordIn;
		id = -1;//default, set when added
	}

	public Coordinate getCoordinate() {
		return this.coord;
	}

	/**
	 * 
	 * @param newCoord: The new Coordinate
	 */
	public void setCoordinate(Coordinate newCoord) {
		this.coord = newCoord;
	}

	public int[] getEdgelist() {
		return edges;
	}

	/**
	 * 
	 * @param newEdgeList: The new list of edges
	 */
	public void setEdgeList(int[] newEdgeList) {
		this.edges = newEdgeList;
	}
	
	public int getId() {
		System.out.println(this.id);
		return id;
	}

	/**
	 * 
	 * @param idIn: the new id
	 */
	public void setId(int idIn) {
		id = idIn;
	}
}
