package edu.wpi.off.by.one.errors.code;

public class Node {
	private Coordinate coord;
	private int[] edges;
	private int id;

	public Node(Coordinate coordIn, int idIn) {
		this.coord = coordIn;
		this.id = idIn;
	}

	public Coordinate getCoordinate() {
		return this.coord;
	}

	public void setCoordinate(Coordinate newCoord) {
		this.coord = newCoord;
	}

	public int[] getEdgelist() {
		return edges;
	}

	public void setEdgeList(int[] newEdgeList) {
		this.edges = newEdgeList;
	}

	public int getId() {
		return id;
	}

	public void setId(int idIn) {
		id = idIn;
	}
}
