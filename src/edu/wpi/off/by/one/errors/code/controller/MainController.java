package edu.wpi.off.by.one.errors.code.controller;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import edu.wpi.off.by.one.errors.code.application.NodeDisplay;
import edu.wpi.off.by.one.errors.code.application.event.Select;
import edu.wpi.off.by.one.errors.code.model.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Screen;

public class MainController implements Initializable{
	@FXML StackPane mapPane;
	@FXML ImageView mapView;
	@FXML ScrollPane mapScrollPane;
	@FXML Button zoomInButton;
	@FXML Button zoomOutButton;
	
	protected Display display = new Display();
    protected Queue<NodeDisplay> nodeQueue = new LinkedList<NodeDisplay>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.print("Main Controller Initialized.");
	
        // center the mapScrollPane contents.
        mapScrollPane.setHvalue(mapScrollPane.getHmin() + (mapScrollPane.getHmax() - mapScrollPane.getHmin()) / 2);
        mapScrollPane.setVvalue(mapScrollPane.getVmin() + (mapScrollPane.getVmax() - mapScrollPane.getVmin()) / 2);
		
		Image map = new Image("/edu/wpi/off/by/one/errors/code/resources/campusmap.png");
        //mapView = new ImageView();
		mapView.setImage(map);
		//mapView.setPreserveRatio(true);
        //mapView.setSmooth(true);
        //mapView.setCache(true);
        mapView.preserveRatioProperty().set(true);
	}
	
	// Gets the map image, sets properties, returns a usable node by JavaFX
    // Should be getting it from Display instead (?)
    // TODO delegate this to Map????? or even Display???????????
    private ImageView GetMapView() {
        Map m = display.getMap();
        
         m.setName("Campus Map");
         m.setCenter(new Coordinate(0));
         m.setImgUrl("campusmap.png");
         m.setRotation(0);
         m.setScale(0); //CHANGE THIS LATER
         Image map = new Image("resources/campusmap.png");
         
        ImageView mapIV = new ImageView();
        //mapIV.setImage(map);
        mapIV.setPreserveRatio(true);
        mapIV.setSmooth(true);
        mapIV.setCache(true);
        
        return mapIV;
    }

    @FXML
    private void zoomInAction(ActionEvent e){
    	//mapPane.setPrefSize(mapPane.getWidth() * 1.2, mapPane.getHeight() * 1.2);
    	mapScrollPane.setVmax(mapScrollPane.getHeight() * 1.1);
    	mapScrollPane.setHmax(mapScrollPane.getWidth() * 1.1);
    	mapPane.setScaleY(mapPane.getScaleY() * 1.1);
    	mapPane.setScaleX(mapPane.getScaleX() * 1.1);
    }
    
    @FXML
    private void zoomOutAction(ActionEvent e){
    	//mapPane.setPrefSize(mapPane.getWidth() * 0.8, mapPane.getHeight() * 0.8);
    	mapScrollPane.setVmax(mapScrollPane.getHeight() * 0.9);
    	mapScrollPane.setHmax(mapScrollPane.getWidth() * 0.9);
    	mapPane.setScaleY(mapPane.getScaleY() * 0.9);
    	mapPane.setScaleX(mapPane.getScaleX() * 0.9);
    }
    
    private void setListeners(){
    	mapView.setOnMouseClicked(e -> {
    		//If user did not click-drag on map
    		if(e.isStillSincePress()){
    			//Add marker on map
    		}
    		//If user double-click
    		if (e.getClickCount() == 2) {
                addNodeDisplay(e.getX(), e.getY());
            }
    	});
    }
    
    /**
     * Re-translates whatever object to it's intended place on the map
     * @param x
     * @param y
     */
    public void move(javafx.scene.Node obj, double x, double y){
        Bounds localBounds = obj.localToScene(mapView.getBoundsInLocal());
        obj.setTranslateX(x - localBounds.getMaxX() / 2);
        obj.setTranslateY(y - localBounds.getMaxY() / 2);
        
    }
    
	/**
	 * Add a NodeDisplay using existing Node
	 * @param nodes
	 */
	void addNodeDisplayFromList(Collection<Node> nodes){
		for(Node n : nodes){
			NodeDisplay nd = new NodeDisplay(display, n.getId());
	        Coordinate c = n.getCoordinate();
	        move(nd, c.getX(), c.getY());
	        
	        nd.addEventFilter(Select.NODE_SELECTED, event -> {
	            System.out.println("Node Selected");
	            nd.selectNode();
	            nodeQueue.add(nd);
	        });
	        
	        nd.addEventFilter(Select.NODE_DESELECTED, event -> {
	            nodeQueue.remove(nd);
	        });
	        mapPane.getChildren().add(nd);
	    }
	}
	
	/**
	 * Add a NodeDisplay using coordinates
	 * Use to add a non-existing NodeDisplay and Node to the display
	 * @param x
	 * @param y
	 */
	void addNodeDisplay(double x, double y){
		NodeDisplay newNode = new NodeDisplay(display, x, y, 0);
	    move(newNode, x, y);
	    
	    newNode.addEventFilter(Select.NODE_SELECTED, event -> {
	        System.out.println("Node Selected");
	        newNode.selectNode();
	        nodeQueue.add(newNode);
	        // Add selected node to selected node queue
	        
	        //TODO stuff regarding info about the node clicked
	        //if double-clicked
	        //if in edit mode
	        //if in add edge mode
	        //	if 2 nodes are already selected when add edge is pressed,
	        //	then create an edge between those two nodes
	        //	if >2 nodes are selected, then edges will be added in order
	        //	of selection
	    });
	    
	    newNode.addEventFilter(Select.NODE_DESELECTED, event -> {
	        nodeQueue.remove(newNode);
	    });
	    
	    //newNode.visibleProperty().bind(showNodes.selectedProperty());
	 
	    // Add to the scene
	    mapPane.getChildren().add(newNode);        
	}
	
	/**
	 * Add EdgeDisplays from selected NodeQueue
	 * Use to add a non-existing EdgeDisplay and Edge to the display
	 */
	void addEdgeDisplayFromQueue(){
		Select selectNodeEvent = new Select(Select.NODE_DESELECTED);
	    if(!nodeQueue.isEmpty()){
	        //System.out.println(nodeQueue.size());
	        while(nodeQueue.size() > 1){
	            NodeDisplay n = nodeQueue.poll();
	            Graph g = display.getGraph();
	            Node a = g.returnNodeById(n.getNode());
	            Node b = g.returnNodeById(nodeQueue.peek().getNode());
	            Coordinate aLoc;
	            Coordinate bLoc;
	            try{
	                aLoc = a.getCoordinate();
	                bLoc = b.getCoordinate();
	            }
	            catch(NullPointerException exception){
	                System.out.println("a thing broke");
	                break;
	            }
	            
	            
	            g.addEdge(a.getId(), b.getId());
	            System.out.println("Edge size" + g.getEdges().size());
	            Line l = new Line(aLoc.getX(), aLoc.getY(), 
	                              bLoc.getX(), bLoc.getY());
	            l.setStroke(Color.BLUE);
	            move(l, (aLoc.getX() + bLoc.getX())/2, (aLoc.getY() + bLoc.getY())/2);;
	            mapPane.getChildren().add(l);
	            n.fireEvent(selectNodeEvent);
	        }
	        nodeQueue.remove().fireEvent(selectNodeEvent);;
	    } else {
	    	//TODO: Display message in editor that says no nodes are being selected
	    }
	}
	
	/**
	 * Add an EdgeDisplay using an existing Edge list and Graph
	 * @param graph
	 * @param edges
	 */
	void addEdgeDisplayFromList(Graph graph, Collection<Edge> edges){
	    for(Edge edge : edges){
	    	Node a = graph.returnNodeById(edge.getNodeA());
	        Node b = graph.returnNodeById(edge.getNodeB());
	        Coordinate aLoc = a.getCoordinate();
	        Coordinate bLoc = b.getCoordinate();
	        //System.out.println("Edge size" + g.getEdges().size());
	            Line l = new Line(aLoc.getX(), aLoc.getY(),
	                              bLoc.getX(), bLoc.getY());
	            l.setStroke(Color.BLUE);
	            move(l, (aLoc.getX() + bLoc.getX())/2, (aLoc.getY() + bLoc.getY())/2);;
	            mapPane.getChildren().add(l);
	        }
	}
    
}
