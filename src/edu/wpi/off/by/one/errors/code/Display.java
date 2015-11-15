package edu.wpi.off.by.one.errors.code;

import java.util.ArrayList;
import java.util.List;

public class Display{
	/*
	 * Holds the list of maps to be displayed
	 */
	private ArrayList<Map> listOfMaps;
	
	/*
	 * A graph for the maps
	 */
	private Graph graph;
	
	/*
	 * Default constructor
	 * Initiales the list of maps with an empty list and the graph with a new graph object
	 */
	public Display(){
		listOfMaps = new ArrayList<Map>();
		graph = new Graph();
	}
	
	/*
	 * Initializes the list of maps and the graph with the given list and graph;
	 * @param listOfMaps The given list of maps to initialize this list with.
	 * @param graph The given graph to initialize this graph with.
	 */
	public Display(ArrayList<Map> listOfMaps, Graph graph){
		this.listOfMaps = listOfMaps;
		this.graph = graph;
	}
	
	/*
	 * Sets the graph to the given graph
	 * @param graph The new graph for the display
	 */
	public void setGraph(Graph graph){
		this.graph = graph;
	}
	
	/*
	 * Adds a Map to the list of maps
	 * @param map The map to be added to the list.
	 */
	public void addMap(Map newMap){
		listOfMaps.add(newMap);
	}
	
	/*
	 * Draws the path in between two node
	 */
	public void drawPath(Node initNode, Node finalNode){
		//Draw a straight line.
	}
}
