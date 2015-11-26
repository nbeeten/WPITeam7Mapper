package edu.wpi.off.by.one.errors.code.controller;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Vector;

import edu.wpi.off.by.one.errors.code.application.EdgeDisplay;
import edu.wpi.off.by.one.errors.code.application.NodeDisplay;
import edu.wpi.off.by.one.errors.code.application.event.EditorEvent;
import edu.wpi.off.by.one.errors.code.application.event.SelectEvent;
import edu.wpi.off.by.one.errors.code.model.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
/**
 * The controller that manages the logic for most of the application view:
 * 		- Map
 * 		- Menu
 *		- Directions
 * 
 */
public class MainController implements Initializable{
	
	@FXML BorderPane root;
	@FXML StackPane mapPane;
	@FXML ImageView mapView;
	@FXML ScrollPane mapScrollPane;
	@FXML Button zoomInButton;
	@FXML Button zoomOutButton;
	@FXML VBox editorPane;
	@FXML Button drawPathDisplayButton;
	StackPane pathPane = new StackPane();
	
	//Where all the images and txt files should be
	String resourceDir = "/edu/wpi/off/by/one/errors/code/resources/";
	
	Display display;												//Current display
    Queue<NodeDisplay> nodeQueue = new LinkedList<NodeDisplay>();	//Selected node queue
    Queue<EdgeDisplay> edgeQueue = new LinkedList<EdgeDisplay>();
    String editItem = "";
    boolean isMapEditor = false;
    boolean isNodeEditor = false;
    boolean isEdgeEditor = false;
    boolean isEditMode = false;
    boolean isAddMode = false;		//Is editor currently adding nodes?
    boolean isDeleteMode = false;	//Is editor currently deleting nodes?
    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Register this controller to the mediator
		ControllerMediator.getInstance().registerMainController(this);
		System.out.print("Main Controller Initialized.");
		
		//Set up new display
		//TODO Make it so that the map preloads a display
		display = new Display();
        mapPane.getChildren().add(0, pathPane);
		// center the mapScrollPane contents.
        mapScrollPane.setHvalue(mapScrollPane.getHmin() + (mapScrollPane.getHmax() - mapScrollPane.getHmin()) / 2);
        mapScrollPane.setVvalue(mapScrollPane.getVmin() + (mapScrollPane.getVmax() - mapScrollPane.getVmin()) / 2);
		
		Image map = new Image(resourceDir + "campusmap.png");
        mapView.setImage(map);
		mapView.preserveRatioProperty().set(true);
        setListeners();
        
	}
	
	/**
	 * Gets current display from MainController
	 * @return Current Display 
	 */
	public Display getDisplay(){
		return this.display;
	}
	
	/**
	 * Updates current display to show or append a new graph/map
	 * @param newdisplay New/Updated Display
	 * @param option Additional options to clear first or append onto current
	 */
	public void updateDisplay(Display newdisplay, String option){
		if(option.equals("NEW")){
			mapPane.getChildren().clear();
            mapPane.getChildren().addAll(mapView);
		}
		this.display = newdisplay;
		Graph g = newdisplay.getGraph();
		updateMap(newdisplay.getMap());
		addNodeDisplayFromList(g.getNodes());
		addEdgeDisplayFromList(g, g.getEdges());
	}
	
	/**
	 * Updates the map and the current map view
	 * TODO Figure out a cleaner way to manage maps
	 * @param newmap
	 */
	private void updateMap(Map newmap){
		mapView.setImage(new Image(resourceDir + newmap.getImgUrl()));
		if(newmap.getImgUrl() != display.getMap().getImgUrl()){
            mapView.setImage(new Image(resourceDir + newmap.getImgUrl()));
        }
	}
	
    /**
     * "Zoom" into map by pressing the "+" button
     * TODO scaling cuts off map at a certain point.
     * TODO Make it so that this works with scrolling on map
     * @param e event
     */
    @FXML
    private void zoomInAction(ActionEvent e){
    	//mapPane.setPrefSize(mapPane.getWidth() * 1.2, mapPane.getHeight() * 1.2);
    	mapScrollPane.setVmax(mapScrollPane.getHeight() * 1.1);
    	mapScrollPane.setHmax(mapScrollPane.getWidth() * 1.1);
    	mapPane.setScaleY(mapPane.getScaleY() * 1.1);
    	mapPane.setScaleX(mapPane.getScaleX() * 1.1);
    }
    /**
     * "Zoom" out of map by pressing the "-" button
     * TODO scaling cuts off map at a certain point.
     * TODO Make it so that this works with scrolling on map
     * @param e event
     */
    @FXML
    private void zoomOutAction(ActionEvent e){
    	//mapPane.setPrefSize(mapPane.getWidth() * 0.8, mapPane.getHeight() * 0.8);
    	mapScrollPane.setVmax(mapScrollPane.getHeight() * 0.9);
    	mapScrollPane.setHmax(mapScrollPane.getWidth() * 0.9);
    	mapPane.setScaleY(mapPane.getScaleY() * 0.9);
    	mapPane.setScaleX(mapPane.getScaleX() * 0.9);
    }
    
    private void setListeners(){
    	// Listen to when the user clicks on the map
    	mapPane.setOnMouseClicked(e -> {
    		//If user did not click-drag on map
    		if(e.isStillSincePress()){
    			//TODO Add marker on map
    			if (isAddMode && isNodeEditor) {
	                addNodeDisplay(e.getX(), e.getY());
	            } 
	    		else if (isEditMode && isNodeEditor){
	    			if(!nodeQueue.isEmpty()){
	    				System.out.println("Editing node");
	    				NodeDisplay n = nodeQueue.poll();
	    				move(n, e.getX(), e.getY());
	    				display.getGraph().editNode(n.getNode(),
	    						new Coordinate((float) e.getX(), (float)e.getY()));
	    				SelectEvent selectNodeEvent = new SelectEvent(SelectEvent.NODE_DESELECTED);
	    				n.fireEvent(selectNodeEvent);
	    			}
	    		}
    		}
    		//If user double-click
    		else{
    			
    		}
    	});
    	//Listen if editor pane sent out an Add/Edit/Delete event
    	root.addEventFilter(EditorEvent.EDIT_ELEMENT, e -> {
    		String eventName = e.getEventType().getName();
    		if(eventName == "ADD") isAddMode = true;
    		else isAddMode = false;
    		if(eventName == "EDIT") isEditMode = true;
    		else isEditMode = false;
    		if(eventName == "DELETE") isDeleteMode = true;
    		else isDeleteMode = false;
    		System.out.println(e.getEventType());
    	});
    	//Listen if editor pane sent out an Map/Node/Edge event
    	//TODO do something with it. right now it only gets the name
    	root.addEventFilter(EditorEvent.DISPLAY_ITEM, e -> {
    		String eventName = e.getEventType().getName();
    		if(eventName == "MAP") isMapEditor = true;
    		else isMapEditor = false;
    		if(eventName == "NODE") isNodeEditor = true;
    		else isNodeEditor = false;
    		if(eventName == "EDGE") isEdgeEditor = true;
    		else isEdgeEditor = false;
    		
    	});
    	
    	root.addEventFilter(EditorEvent.DRAW_EDGES, e -> {
    		if(isEdgeEditor) addEdgeDisplayFromQueue(); 
    	});
    }
    
    /**
     * Re-translates whatever object to it's intended place on the map
     * @param x
     * @param y
     */
    public void move(javafx.scene.Node obj, double x, double y){
        Bounds localBounds = mapView.getBoundsInLocal();
        System.out.println("MOVING");
        System.out.println(localBounds.getMinX() + " " + localBounds.getMinY());
        System.out.println(localBounds.getMaxX() + " " + localBounds.getMaxY());
        obj.setTranslateX(x - localBounds.getMaxX() / 2);
        obj.setTranslateY(y - localBounds.getMaxY() / 2);
        
    }
    
	/**
	 * Add a NodeDisplay using existing Node
	 * @param nodes
	 */
	void addNodeDisplayFromList(Collection<Node> nodes){
		Node[] nodeArr = new Node[nodes.size()];
		nodes.toArray(nodeArr); // To avoid ConcurrentModificationException
		for(Node n : nodeArr){
			NodeDisplay nd = new NodeDisplay(display, n.getId());
	        Coordinate c = n.getCoordinate();
	        move(nd, c.getX(), c.getY());
	        
	        nd.addEventFilter(SelectEvent.NODE_SELECTED, event -> {
	            System.out.println("Node Selected");
	            nd.selectNode();
	            nodeQueue.add(nd);
	        });
	        
	        nd.addEventFilter(SelectEvent.NODE_DESELECTED, event -> {
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
		System.out.println("Added Node");
		
		NodeDisplay newNode = new NodeDisplay(display, x, y, 0);
	    move(newNode, x, y);
	    
	    newNode.addEventFilter(SelectEvent.NODE_SELECTED, event -> {
	        
	        if(isDeleteMode && isNodeEditor){
	        	System.out.println("Node deleted");
	        	Id id = newNode.getNode();
	        	display.getGraph().deleteNode(id);
	        	mapPane.getChildren().remove(newNode);
	        	System.out.println(display.getGraph().getNodes().size());
	        } else {
	        	System.out.println("Node Selected");
	        	newNode.selectNode();
		        nodeQueue.add(newNode);
		        // Add selected node to selected node queue
	        }
	        
	        //TODO stuff regarding info about the node clicked
	        //if double-clicked
	        //if in edit mode
	        //if in add edge mode
	        //	if 2 nodes are already selected when add edge is pressed,
	        //	then create an edge between those two nodes
	        //	if >2 nodes are selected, then edges will be added in order
	        //	of selection
	    });
	    
	    newNode.addEventFilter(SelectEvent.NODE_DESELECTED, event -> {
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
		SelectEvent selectNodeEvent = new SelectEvent(SelectEvent.NODE_DESELECTED);
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
	            
	            
	            Id newEdge = g.addEdgeRint(a.getId(), b.getId());
	            EdgeDisplay e = new EdgeDisplay(display, newEdge,
	        			aLoc.getX(), aLoc.getY(),
	                    bLoc.getX(), bLoc.getY());
	            e.setStroke(Color.BLUE);
	            move(e, (aLoc.getX() + bLoc.getX())/2, (aLoc.getY() + bLoc.getY())/2);;
	            mapPane.getChildren().add(e);
	            n.fireEvent(selectNodeEvent);
	            
	            e.addEventFilter(SelectEvent.EDGE_SELECTED, ev -> {
	            	System.out.println("Edge selected");
	            	e.selectEdge();
	            	ControllerMediator cm = ControllerMediator.getInstance();
	            	cm.viewDisplayItem(e);
	            });
	            
	            e.addEventFilter(SelectEvent.EDGE_DESELECTED, ev -> {
	            	//do sth;
	            	
	            });
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
	void addEdgeDisplayFromList(Graph graph, Vector<Edge> edges){
		Edge[] edgeArr = new Edge[edges.size()];
		edges.toArray(edgeArr); // To avoid ConcurrentModificationException
	    for(Edge edge : edgeArr){
	    	Id aID = edge.getNodeA();
	    	Id bID = edge.getNodeB();
	    	Node a = graph.returnNodeById(aID);
	        Node b = graph.returnNodeById(bID);
	        Coordinate aLoc = a.getCoordinate();
	        Coordinate bLoc = b.getCoordinate();
	        //System.out.println("Edge size" + g.getEdges().size());
        	EdgeDisplay e = new EdgeDisplay(display, aID, bID,
        			aLoc.getX(), aLoc.getY(),
                    bLoc.getX(), bLoc.getY());
            e.setStroke(Color.BLUE);
            move(e, (aLoc.getX() + bLoc.getX())/2, (aLoc.getY() + bLoc.getY())/2);;
            mapPane.getChildren().add(e);
            
            e.addEventFilter(SelectEvent.EDGE_SELECTED, ev -> {
            	System.out.println("Edge selected");
            	e.selectEdge();
            	ControllerMediator cm = ControllerMediator.getInstance();
            	cm.viewDisplayItem(e);
            });
            
            e.addEventFilter(SelectEvent.EDGE_DESELECTED, ev -> {
            	//do sth;
            });
	    }
	}
    
	public void drawPath(){
		pathPane.getChildren().clear();
        NodeDisplay startNode = nodeQueue.poll();
        NodeDisplay endNode = nodeQueue.poll();
        if(startNode != null && endNode != null){
            //display.drawPath(startNode.node.getId(), endNode.node.getId());
            int idx = 0;
            Vector<Node> nodes = display.getGraph().getNodes();
            //System.out.println(nodes.size());
            
            Path p = new Path(startNode.getNode(), endNode.getNode());
            Graph g = display.getGraph();
            //System.out.println("Size graph " + g.getEdges().size());
            p.runAStar(g); //Change this later??
            ArrayList<Id> idList = p.getRoute();
            System.out.println("Route size " + idList.size());
            while(idx < idList.size() - 1){
            	Node a = g.returnNodeById(idList.get(idx));
                Node b = g.returnNodeById(idList.get(++idx));
                Coordinate aLoc = a.getCoordinate();
                Coordinate bLoc = b.getCoordinate();
                Line l = new Line(aLoc.getX(), aLoc.getY(), 
                                  bLoc.getX(), bLoc.getY());
                l.setStrokeWidth(3.0);
                move(l, (aLoc.getX() + bLoc.getX())/2, (aLoc.getY() + bLoc.getY())/2);
                
                pathPane.getChildren().add(l);
                pathPane.toFront();
                pathPane.setMouseTransparent(true);
            }
            SelectEvent selectNodeEvent = new SelectEvent(SelectEvent.NODE_DESELECTED);
            startNode.fireEvent(selectNodeEvent);
            endNode.fireEvent(selectNodeEvent);
        }
	}
	
}
