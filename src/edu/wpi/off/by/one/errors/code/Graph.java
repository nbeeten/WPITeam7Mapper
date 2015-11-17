package edu.wpi.off.by.one.errors.code;

import java.util.*;

//graph class manages edges and nodes
public class Graph {
	private Vector<Node> listOfNodes = new Vector<Node>(); //array list of nodes
	private Vector<Edge> listOfEdges = new Vector<Edge>(); //array list of edges
	
	public Node addNode(Coordinate coordIn){//adds a node to the list
		Node n = new Node(coordIn);
		n.setId(listOfNodes.size());
		listOfNodes.add(n);
		return n;
	}
	public int addNodeRint(Coordinate coordIn){//adds a node to the list, returns ID instead of node
		Node n = new Node(coordIn);
		int id = listOfNodes.size();
		n.setId(id);
		listOfNodes.add(n);
		return id;
	}
	public Node returnNodeById(int id){
		Node n = listOfNodes.elementAt(id);
		if(n != null && n.getId() == id) return n;
		return null;
	}
	
	

	public Edge addEdge(int nodeAIn, int nodeBIn){//adds an edge to the list
		Edge e = new Edge(nodeAIn, nodeBIn); 
		e.setId(listOfEdges.size());
		listOfEdges.add(e);
		return e;
	}
	public int addEdgeRint(int nodeAIn, int nodeBIn){//adds an edge to the list, returns ID instead of edge
		Edge e = new Edge(nodeAIn, nodeBIn); 
		int id = listOfEdges.size();
		e.setId(id);
		listOfEdges.add(e);
		return id;
	}
	public Edge returnEdgeById(int id){
		Edge e = listOfEdges.elementAt(id);
		if(e != null && e.getId() == id) return e;
		return null;
	}
	
	public Vector<Node> getNodes(){//gets the list of nodes
		return listOfNodes;
	}
	
	public Vector<Edge> getEdges(){//gets the list of edges
		return listOfEdges;
	}
	
	public void deleteNode(int target){//deletes a node from the list (replaces it with null)
		Node n = listOfNodes.elementAt(target);
		if(n != null && n.getId() == target) listOfNodes.set(target, null);
		//will change this when we upgrade ids
	}
	
	public void deleteEdge(int target){//deletes an edge from the list (replaces it with null)
		Edge e = listOfEdges.elementAt(target);
		if(e != null && e.getId() == target) listOfEdges.set(target, null);
		//will change this when we upgrade ids
	}
	
	public void editNode(int target, Coordinate newCoord){//edits a node on the list
		Node n = listOfNodes.elementAt(target);
		if(n != null && n.getId() == target){
			n.setCoordinate(newCoord);
			listOfNodes.set(target, n);
		}
		//will change this when we upgrade ids
	}
	
	public void editEdge(){//edits an edge on the list
		//TODO: add functionality after we decide exactly what this should do
	}
}
