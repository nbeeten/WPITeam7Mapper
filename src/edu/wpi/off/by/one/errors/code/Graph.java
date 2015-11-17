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
	

	public Edge addEdge(int nodeAIn, int nodeBIn){//adds an edge to the list
		Edge e = new Edge(nodeAIn, nodeBIn); 
		e.setId(listOfEdges.size());

		listOfEdges.add(e);
		return e;
	}
	
	public Vector<Node> getNodes(){//gets the list of nodes
		return listOfNodes;
	}
	
	public Vector<Edge> getEdges(){//gets the list of edges
		return listOfEdges;
	}
	
	public void deleteNode(int target){//deletes a node from the list (replaces it with null)
		Iterator it = listOfNodes.iterator();
		while(it.hasNext()){
			Node n = (Node) it.next();
			if(n.getId()==target){
				listOfNodes.set(listOfNodes.indexOf(n), null);
			}
		}
	}
	
	public void deleteEdge(int target){//deletes an edge from the list (replaces it with null)
		Iterator it = listOfEdges.iterator();
		while(it.hasNext()){
			Edge e = (Edge) it.next();
			if(e.getId()==target){
				listOfEdges.set(listOfEdges.indexOf(e), null);
			}
		}
	}
	
	public void editNode(int target, Coordinate newCoord){//edits a node on the list
		Iterator it = listOfNodes.iterator();
		while(it.hasNext()){
			Node n = (Node) it.next();
			if(n.getId()==target){
				listOfNodes.get(listOfNodes.indexOf(n)).setCoordinate(newCoord);
			}
		}
	}
	
	public void editEdge(){//edits an edge on the list
		//TODO: add functionality after we decide exactly what this should do
	}
}
