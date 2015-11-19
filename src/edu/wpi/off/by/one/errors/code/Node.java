package edu.wpi.off.by.one.errors.code;

import java.util.Vector;

public class Node {
    private Coordinate coord;
    private Vector<Id> edges;//list of indexes of edges
    private Id id;
    
    /**
     *
     * @param coordIn: The given coordinate
     * @param idIn: the integer id number for the Node
     */
    public Node(Coordinate coordIn) {
        this.edges = new Vector<Id>(); // we can totally use arraylist here, dont have to use a vector
        this.coord = coordIn;
        this.id = new Id();//default, set when added
    }
    
    public Node(Coordinate coordIn, Id nid) {
        this.edges = new Vector<Id>(); // we can totally use arraylist here, dont have to use a vector
        this.coord = coordIn;
        this.id = nid;//default, set when added
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
    
    public boolean addEdgeId(Id id){
        return this.edges.add(id);
    }
    
    /**
     * get the list of connected edges for the node
     * @return edges: the list of connected edges
     */
    
    public Vector<Id> getEdgelist() {
        
        return edges;
    }
    
    /**
     * set the edges to the passed in list
     * @param newEdgeList: The new list of edges
     */
    public void setEdgeList(Vector<Id> newEdgeList) {
        this.edges = newEdgeList;
    }
    /**
     *  get the Id for the node
     * @return id: the Node's ID
     */
    public Id getId() {
        return id;
    }
    
    /**
     * setter for the Node's id
     * @param idIn: the new id
     */
    public void setId(int i, int u) {
        id.indice = i;
        id.unique = u;
    }
}