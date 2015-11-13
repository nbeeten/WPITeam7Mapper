
public class Node {
	private Coordinate coord;
	private int[] edges;
	private int id;
	
	public Node(Coordinate coordIn, int idIn)
	{
		Coordinate coord = coordIn;
		int id = idIn;
	}
	public Coordinate getCoordinate(){
		return coord;
	}
	public void setCoordinate(Coordinate newCoord){
		coord = newCoord;
	}
	public int[] getEdgelist(){
		return edges;
	}
	public void setEdgeList(int[] newEdgeList){
		edges = newEdgeList;
	}
	public int getId(){
		return id;
	}
	public void setId(int idIn){
		id = idIn;
	}
}
