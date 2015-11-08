import java.util.*;

//graph class manages edges and nodes
//written by Sam Wallach
public class graph {
	private ArrayList<node> listOfNodes = new ArrayList<node>(); //array list of nodes
	private ArrayList<edge> listOfEdges = new ArrayList<edge>(); //array list of edges
	
	public void addNode(node n){//adds a node to the list
		listOfNodes.add(n);
	}
	
	public void addEdge(edge e){//adds an edge to the list
		listOfEdges.add(e);
	}
	
	public ArrayList<node> getNodes(){//gets the list of nodes
		return listOfNodes;
	}
	
	public ArrayList<edge> getEdges(){//gets the list of edges
		return listOfEdges;
	}
	
	public void deleteNode(node n){//deletes a node from the list
		listOfNodes.remove(n);
	}
	
	public void deleteEdge(edge e){//deletes an edge from the list
		listOfNodes.remove(e);
	}
	
	public void editNode(){//edits a node on the list
		//TODO: add functionality after node class has been set up
	}
	
	public void editEdge(){//edits an edge on the list
		//TODO: add functionality after edge class has been set up
	}
}
