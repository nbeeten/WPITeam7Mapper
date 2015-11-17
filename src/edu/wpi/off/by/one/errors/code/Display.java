package edu.wpi.off.by.one.errors.code;

import java.util.Vector;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Display extends Pane{
	
	Map currentMap;
	Graph currentGraph;

	/**
	 * Display Constructor
	 * @param currentMap
	 * @param currentGraph
	 */
	public Display(Map currentMap, Graph currentGraph){
		this.currentMap = currentMap == null ? new Map() : currentMap;
		this.currentGraph = currentGraph == null ? new Graph() : currentGraph;
	}
	public Display(){
		this.currentMap = new Map();
		this.currentGraph = new Graph();
	}
	
	public void setMap(Map m){ this.currentMap = m; }
	public void setGraph(Graph g) { this.currentGraph = g; }
	public Map getMap() { return currentMap; }
	public Graph getGraph() { return currentGraph; }
	
	/**
	 * Draws a graphical path between two nodes on the map
	 * @param a First node
	 * @param b Second node
	 */
	public void drawPath(int[] idList) {
		int idx = 0;
		Vector<Node> nodes = currentGraph.getNodes();
		Path p = new Path();
		while(idx < idList.length){
			Node a = nodes.get(idx);
			Node b = nodes.get(idx++);
			Coordinate aLoc = a.getCoordinate();
			Coordinate bLoc = b.getCoordinate();
			Line l = new Line(aLoc.getX(), aLoc.getY(), 
					bLoc.getX(), bLoc.getY());
		}
		
		//TODO: Add code to actually draw the line on the map
	}
	
	private ImageView GetMapView() {
		currentMap.getImgUrl();
		Image map = new Image("campusmap.png");
		ImageView mapIV = new ImageView();
		mapIV.setImage(map);
		mapIV.setPreserveRatio(true);
		mapIV.setSmooth(true);
		mapIV.setCache(true);
		return mapIV;
	}
	
}