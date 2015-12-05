package edu.wpi.off.by.one.errors.code.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.Vector;
import java.util.stream.Collectors;

import edu.wpi.off.by.one.errors.code.application.EdgeDisplay;
import edu.wpi.off.by.one.errors.code.application.NodeDisplay;
import edu.wpi.off.by.one.errors.code.application.event.EditorEvent;
import edu.wpi.off.by.one.errors.code.application.event.SelectEvent;
import edu.wpi.off.by.one.errors.code.controller.menupanes.devtoolspanes.MapDevToolPane;
import edu.wpi.off.by.one.errors.code.model.Coordinate;
import edu.wpi.off.by.one.errors.code.model.Display;
import edu.wpi.off.by.one.errors.code.model.Edge;
import edu.wpi.off.by.one.errors.code.model.FileIO;
import edu.wpi.off.by.one.errors.code.model.Graph;
import edu.wpi.off.by.one.errors.code.model.Id;
import edu.wpi.off.by.one.errors.code.model.Map;
import edu.wpi.off.by.one.errors.code.model.Node;
import edu.wpi.off.by.one.errors.code.model.Path;
import edu.wpi.off.by.one.errors.code.model.Matrix;

/**
 * Created by jules on 11/28/2015.
 * Edited by Kelly on 11/30/2015.
 */
public class MapRootPane extends AnchorPane{

	@FXML StackPane mapPane;
	@FXML ImageView mapView;
	@FXML Button zoomInButton;
	@FXML Button zoomOutButton;
	@FXML Button drawPathDisplayButton;
	@FXML Button rotateLeftButton;
	@FXML Button rotateRightButton;
	StackPane nodeLayer = new StackPane();
	StackPane edgeLayer = new StackPane();
	StackPane pathPane = new StackPane();
	public Coordinate translate;
	Coordinate release = new Coordinate(0, 0, 0);
	public float rot = 0.0f;
	public float zoom = 2.0f;
	Matrix view;
	Matrix invview;
	Matrix lastview;
	//Change this as necessary
	public Canvas canvas = new Canvas(1000, 1600);
	public int currentLevel = 1;
	private Path p;
	
	//Where all the images and txt files should be
	String resourceDir = "/edu/wpi/off/by/one/errors/code/resources/";
	private String filePath = "src" + resourceDir + "maps/txtfiles/fullCampusMap.txt";
	Bounds localBounds;
	Display display;												//Current display
	HashMap<String, Display> displayList;
    Queue<NodeDisplay> nodeQueue = new LinkedList<NodeDisplay>();	//Selected node queue
    Queue<EdgeDisplay> edgeQueue = new LinkedList<EdgeDisplay>();
    ArrayList<Id> currentRoute = new ArrayList<Id>();
    boolean isMapEditor = false;
    public boolean isNodeEditor = false;
    public boolean isEdgeEditor = false;
    public boolean isEditMode = false;
    public boolean isAddMode = false;		//Is editor currently adding nodes?
    public boolean isDeleteMode = false;	//Is editor currently deleting nodes?
    
    boolean isctrl = false;

    
    public MapRootPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MapRootPane.fxml"));
        
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException excpt) {
            throw new RuntimeException(excpt);
        }
        ControllerSingleton.getInstance().registerMapRootPane(this);
        initialize();
    }
    
    public MapRootPane getMapRootPane() { return this; }
    public HashMap<String, Display> getAllDisplays() {return displayList; }
    public String getFilePath() {return this.filePath; }
    
    public void updateCanvasSize(double width, double height){
    	canvas.setHeight(height);
    	canvas.setWidth(width);
    	render();
    }
    
    private void initialize(){
    	//Load campus map from display list
		display = FileIO.load("src" + resourceDir + "maps/txtfiles/fullCampusMap.txt", display);
        display.getMaps().get(0).setRotation(0);
        pathPane.setMouseTransparent(true);
        nodeLayer.setMouseTransparent(false);
        edgeLayer.setPickOnBounds(false);
        nodeLayer.setPickOnBounds(false);
		mapPane.getChildren().addAll(canvas, edgeLayer, nodeLayer, pathPane);
		nodeLayer.setAlignment(Pos.TOP_LEFT);
		edgeLayer.setAlignment(Pos.TOP_LEFT);
		//Set map image
		mapView.preserveRatioProperty().set(true);
		Coordinate lastdragged = new Coordinate(0);
		Coordinate mydragged = new Coordinate(0);
		//Update local bounds of the map view
		localBounds = mapView.getBoundsInLocal();
		//Updates display with nodes/edges
		updateDisplay(display.getGraph());
		// center the mapScrollPane contents.
        
        //Setup event listeners for map
        setListeners();
		mapPane.setOnMousePressed(e -> {
			 lastview = invview;
			 Coordinate in = new Coordinate((float)e.getX(), (float)e.getY());
			 Coordinate sin = lastview.transform(in);
			 mydragged.setAll(in.getX(), in.getY(), 0);
			 lastdragged.setAll(sin.getX(), sin.getY(), 0);
	     });
		
		mapPane.setOnMouseDragged(e -> {
			Coordinate sin = new Coordinate((float)e.getX(), (float)e.getY());
			Coordinate in = lastview.transform(sin);
			if(e.isControlDown()){
				zoom *= (1.0f - 0.01*(sin.getY() - mydragged.getY()));
				render();
				lastview = invview;
			} else {
				Coordinate delta = new Coordinate(in.getX() - lastdragged.getX(), in.getY() - lastdragged.getY());
				translate.setAll((float) translate.getX() + delta.getX(), (float)translate.getY() + delta.getY(), translate.getZ());
				render();
			}
			lastdragged.setAll(in.getX(), in.getY(), 0);
			mydragged.setAll(sin.getX(), sin.getY(), 0);
			
		});
		
		mapPane.setOnMouseReleased(e -> {
	    	 release.setAll((float)e.getX(), (float)e.getY(), 0);
	     });
		
		mapPane.setOnScroll(v -> {
			if(v.getDeltaY() > 0) {
				zoom*=1.1; render();
			}
			else { zoom*=0.9; render(); }
		});

		
		translate = new Coordinate(0.0f, 0.0f);
		view = new Matrix();
		invview  =new Matrix();
        render();
    }
    
    /**
	 * Gets current display from MainPane
	 * @return Current Display
	 */
	public Display getDisplay(){
		return this.display;
	}

	/**
	 * External updater
	 * Updates current display to show or append a new graph/map
	 * @param newdisplay New/Updated Display
	 * @param option Additional options to clear first or append onto current
	 */
	public void updateDisplay(Display newdisplay, String option){
		updateDisplay(this.display.getGraph());
	}
	public void render(){
		if(zoom < 0.4f) zoom = Math.abs(zoom);
		if(zoom < 0.4f) zoom = 0.4f;
		else if(zoom > 11.4f) zoom = 11.4f;
		view = new Matrix().translate(new Coordinate((float)canvas.getWidth()/2.0f, (float)canvas.getHeight()/2.0f)).rotate(rot, 0.0f, 0.0f, 1.0f).scale(zoom).translate(new Coordinate(translate.getX(), translate.getY(), translate.getZ()));
		invview = new Matrix(new Coordinate(-1.0f * translate.getX(), -1.0f *translate.getY(), -1.0f * translate.getZ())).scale(1.0/zoom).rotate(-rot, 0.0, 0.0, 1.0).translate(new Coordinate((float)canvas.getWidth()/-2.0f, (float)canvas.getHeight()/-2.0f));
		//grab graphics context
		GraphicsContext mygc = canvas.getGraphicsContext2D();
		mygc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		Graph mygraph = display.getGraph();
		Vector<Node> nlist = mygraph.getNodes();
		Vector<Edge> elist = mygraph.getEdges();
		ArrayList<Map> mlist = display.getMaps();
		
		boolean isCurrentFloor = true;
		for(Map m : mlist){
			mygc.save();
			if(m == null) continue;

			if(m.getImage() == null) continue;
			if(translate.getZ() > m.getCenter().getZ() + 0.1 || translate.getZ() < m.getCenter().getZ() - 0.1){
				continue;
			}
			Coordinate c = view.transform(m.getCenter());
			//mygc.translate(c.getX(), c.getY());
			Rotate r = new Rotate(m.getRotation() + rot, 0, 0);
	        mygc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx() + c.getX(), r.getTy() + c.getY());
	        mygc.scale(zoom * m.getScale(), zoom * m.getScale());
			
			mygc.drawImage(m.getImage(), 0, 0);
			mygc.restore();
		}
		for(javafx.scene.Node np: nodeLayer.getChildren()){
			NodeDisplay nd = (NodeDisplay)np;
			if(nd == null) continue;

			Node n = display.getGraph().returnNodeById(nd.getNode());
			if(translate.getZ() > n.getCoordinate().getZ() + 0.1 || translate.getZ() < n.getCoordinate().getZ() - 0.1){
				np.setVisible(false);
				np.setMouseTransparent(true);
				continue;
			} else {
				np.setVisible(true);
				np.setMouseTransparent(false);
				if(n == null){ nodeLayer.getChildren().remove(np); continue; }
				Coordinate nc = view.transform(n.getCoordinate());
				nd.setCenterX(nc.getX()- 5.0f);
				nd.setCenterY(nc.getY()- 5.0f);
			}
		}
//		for(javafx.scene.Node np: edgeLayer.getChildren()){
//			EdgeDisplay nd = (EdgeDisplay)np;
//			if(nd == null) continue;
//			Edge n = display.getGraph().returnEdgeById(nd.getEdge());
//			if(n == null){ edgeLayer.getChildren().remove(np); continue; }
//			Node A = display.getGraph().returnNodeById(n.getNodeA());
//			Node B = display.getGraph().returnNodeById(n.getNodeB());
//			if(A == null || B == null){
//				display.getGraph().deleteEdge(n.getId());
//				edgeLayer.getChildren().remove(np); 
//				continue; 
//			}
//			if((translate.getZ() > A.getCoordinate().getZ() + 0.1 || translate.getZ() < A.getCoordinate().getZ() - 0.1) && (translate.getZ() > B.getCoordinate().getZ() + 0.1 || translate.getZ() < B.getCoordinate().getZ() - 0.1)){
//				np.setVisible(false);
//				np.setMouseTransparent(true);
//				continue;
//			}
//			np.setVisible(true);
//			np.setMouseTransparent(false);
//			Coordinate ac = view.transform(A.getCoordinate());
//			Coordinate bc = view.transform(B.getCoordinate());
//			nd.setStartX(ac.getX());
//			nd.setStartY(ac.getY());
//			nd.setEndX(bc.getX());
//			nd.setEndY(bc.getY());
//		}
		if(isEditMode){
			mygc.save();
			for(Edge e : elist){
				if(e == null) continue;
				Node A = display.getGraph().returnNodeById(e.getNodeA());
				Node B = display.getGraph().returnNodeById(e.getNodeB());
				if(A == null || B == null){
					display.getGraph().deleteEdge(e.getId());
					continue; 
				}
				if((translate.getZ() > A.getCoordinate().getZ() + 0.1 || translate.getZ() < A.getCoordinate().getZ() - 0.1) && (translate.getZ() > B.getCoordinate().getZ() + 0.1 || translate.getZ() < B.getCoordinate().getZ() - 0.1)){
					continue;
				}
				Coordinate ac = view.transform(A.getCoordinate());
				Coordinate bc = view.transform(B.getCoordinate());
				mygc.setLineWidth(5.0f);
				mygc.setFill(Color.AQUA);
				mygc.setStroke(Color.AQUA);
				mygc.strokeLine(ac.getX(), ac.getY(), bc.getX(), bc.getY());
			} 
			mygc.restore();
		}
		Node last = null;
		for(Id id : currentRoute){
			mygc.save();
			Node A = display.getGraph().returnNodeById(id);
			if(A == null) continue;
			if(last == null){
				last = A;
				continue;
			}
			if((translate.getZ() > A.getCoordinate().getZ() + 0.1 || translate.getZ() < A.getCoordinate().getZ() - 0.1) && (translate.getZ() > last.getCoordinate().getZ() + 0.1 || translate.getZ() < last.getCoordinate().getZ() - 0.1)){
				last = A;
				continue;
			}

			Coordinate ac = view.transform(A.getCoordinate());
			Coordinate bc = view.transform(last.getCoordinate());
			mygc.setLineWidth(5.0f);
			mygc.setFill(Color.RED);
			mygc.setStroke(Color.RED);
			mygc.strokeLine(ac.getX(), ac.getY(), bc.getX(), bc.getY());
			last = A;
			mygc.restore();
		}

	}
	/**
	 * Internal updater/Helper function
	 * Draw all nodes and edges on map of a graph
	 * @param g 
	 */
	private void updateDisplay(Graph g){
		addNodeDisplayFromList(g.getNodes());
	}

    /**
     * Sets up event listener functions for whenever user does something on the mapPane/mapView
     * 
     * TODO If user is in developer mode
     * 		* NODE: left click to add, right click to delete
     * 		* EDGE: right click to delete
     * 
     */
    private void setListeners(){
    	// Listen to when the user clicks on the map
    	
    	canvas.setOnMouseClicked(e -> {
    		//If user did not click-drag on map
    		if(e.isStillSincePress()){
    			//TODO Add marker on map
    			if (e.getButton() == MouseButton.PRIMARY) {
    				System.out.println("Adding node");
	                addNodeDisplay(e.getX(), e.getY());
	            }
	    		else if (isNodeEditor){
	    			if(!nodeQueue.isEmpty()){
	    				////System.out.println("Editing node");
	    				NodeDisplay n = nodeQueue.poll();
	    				Graph g = display.getGraph();
	    				//Coordinate currentCoord = g.returnNodeById(n.getNode()).getCoordinate();
	    				//Matrix transform = new Matrix(currentCoord);
	    				//Coordinate newCoord = transform.transform(new Coordinate((float) e.getX(), (float) e.getY()));
	    				//n.setCenterX(newCoord.getX());
	    				//n.setCenterY(newCoord.getY());
	    				n.setCenterX(e.getX());
	    				n.setCenterY(e.getY());
	    				display.getGraph().editNode(n.getNode(),
	    						new Coordinate((float) e.getX(), (float)e.getY(), (float) currentLevel));
	    				SelectEvent selectNodeEvent = new SelectEvent(SelectEvent.NODE_DESELECTED);
	    				n.fireEvent(selectNodeEvent);
	    			}
	    		}
    		}
    	});
    	
    	//Listen if editor pane sent out an Add/Edit/Delete event
    	//TODO remove later to work with new way
    	mapPane.addEventFilter(EditorEvent.EDIT_ELEMENT, e -> {
    		String eventName = e.getEventType().getName();
    		if(eventName == "ADD") isAddMode = true;
    		else isAddMode = false;
    		if(eventName == "EDIT") isEditMode = true;
    		else isEditMode = false;
    		if(eventName == "DELETE") isDeleteMode = true;
    		else isDeleteMode = false;
    		////System.out.println(e.getEventType());
    	});
    	//Listen if editor pane sent out an Map/Node/Edge event
    	//TODO do something with it. right now it only gets the name
    	//		remove later to work with new way
    	mapPane.addEventFilter(EditorEvent.DISPLAY_ITEM, e -> {
    		String eventName = e.getEventType().getName();
    		if(eventName == "MAP") isMapEditor = true;
    		else isMapEditor = false;
    		if(eventName == "NODE") isNodeEditor = true;
    		else isNodeEditor = false;
    		if(eventName == "EDGE") isEdgeEditor = true;
    		else isEdgeEditor = false;

    	});

    	edgeLayer.addEventFilter(EditorEvent.DRAW_EDGES, e -> {
    		if(isEdgeEditor) addEdgeDisplayFromQueue();
    	});
    }

	/**
	 * Add a NodeDisplay using existing Node
	 * @param nodes
	 */
	void addNodeDisplayFromList(Collection<Node> nodes){
		////System.out.println(nodes.size());
		Node[] nodeArr = new Node[nodes.size()];
		nodes.toArray(nodeArr); // To avoid ConcurrentModificationException
		for(Node n : nodeArr){
			if(n == null) {
				continue;
			}
			Coordinate c = n.getCoordinate();
			
			double tx = c.getX();// - (localBounds.getMaxX() / 2);
	        double ty = c.getY();// - (localBounds.getMaxY() / 2);
	        double tz = c.getZ();
			
			NodeDisplay newNode = new NodeDisplay(display, n.getId(),
					new SimpleDoubleProperty(c.getX()), 
					new SimpleDoubleProperty(c.getY()),
					new SimpleDoubleProperty(c.getZ()));
			newNode.centerXProperty().addListener(e -> {
		    	newNode.setTranslateX(newNode.getCenterX());
		    });
		    newNode.centerYProperty().addListener(e -> {
		    	newNode.setTranslateY(newNode.getCenterY());
		    });
	        newNode.addEventFilter(SelectEvent.NODE_SELECTED, event -> {
	           ////System.out.println("Node Selected");
		       newNode.selectNode();
			   nodeQueue.add(newNode);
			   if(nodeQueue.size() == 2 && !isEditMode){
			    	drawPath();
			    	nodeQueue.clear();
			    	newNode.fireEvent(new SelectEvent(SelectEvent.NODE_DESELECTED));
			    }
			   // Add selected node to selected node queue
			   
			   ControllerSingleton.getInstance().getMenuPane().getDevToolsMenuPane().getNodeDevToolPane().displayNodeInfo(newNode);
	        });
	        
	        newNode.addEventFilter(SelectEvent.NODE_DESELECTED, event -> {
	            nodeQueue.remove(newNode);
	        });
	        
	        newNode.addEventFilter(EditorEvent.DELETE_NODE, event -> {
	        	if(isNodeEditor){
		        	////System.out.println("Node deleted");
		        	Graph g = display.getGraph();
		        	Id id = newNode.getNode();
		        	////System.out.println(id);
		        	////System.out.println(display);
		        	//for(int i = 0; i < edges.size(); i++)  g.deleteEdge(edges.get(i));
		        	g.deleteNode(id);
		        	//mapPane.getChildren().remove(this);
		        	//remove edge display as well
		        	//right now this throws nullpointerexception.
		        	render();
		        	//updateDisplay(display, "NEW");
	        	}
	        });
	        nodeLayer.getChildren().add(newNode);
	    }
	}

	/**
	 * Add a NodeDisplay using coordinates
	 * Use to add a non-existing NodeDisplay and Node to the display
	 * @param x
	 * @param y
	 */
	void addNodeDisplay(double x, double y){
		////System.out.println("Added Node");
		float tx = (float) x;// - (localBounds.getMaxX() / 2);
        float ty = (float) y;// - (localBounds.getMaxY() / 2);
		Coordinate c = invview.transform(new Coordinate(tx, ty));
		NodeDisplay newNode = new NodeDisplay(display, 
				new SimpleDoubleProperty(c.getX()), 
				new SimpleDoubleProperty(c.getY()),
				new SimpleDoubleProperty(currentLevel));
		newNode.centerXProperty().addListener(e -> {
	    	newNode.setTranslateX(newNode.getCenterX());
	    });
	    newNode.centerYProperty().addListener(e -> {
	    	newNode.setTranslateY(newNode.getCenterY());
	    });
	    ////System.out.println(newNode.getNode());
	    newNode.addEventFilter(SelectEvent.NODE_SELECTED, event -> {
	    	
	        ////System.out.println("Node Selected");
	        newNode.selectNode();
		    nodeQueue.add(newNode);
		    if(nodeQueue.size() == 2 && !isEditMode){
		    	drawPath();
		    	nodeQueue.clear();
		    	newNode.fireEvent(new SelectEvent(SelectEvent.NODE_DESELECTED));
		    }
		    ////System.out.println(newNode.getCenterX() + " " + newNode.getCenterY());
		        // Add selected node to selected node queue
		    //ControllerSingleton.getInstance().getMainPane().getNodeTool().displayNodeInfo(newNode);
		    ControllerSingleton.getInstance().getMenuPane().getDevToolsMenuPane().getNodeDevToolPane().displayNodeInfo(newNode);
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

	    newNode.addEventFilter(EditorEvent.DELETE_NODE, event -> {
        	if(isNodeEditor){
	        	////System.out.println("Node deleted");
	        	////System.out.println(display);
	        	Graph g = display.getGraph();
	        	Id id = newNode.getNode();
	        	////System.out.println(id);
	        	g.deleteNode(id);
	        	//mapPane.getChildren().remove(this);
	        	//remove edge display as well
	        	//right now this throws nullpointerexception.
	        	render();
	        	//updateDisplay(display, "NEW");
	        	//mapPane.getChildren().remove();
        	}
        });

	    //newNode.visibleProperty().bind(showNodes.selectedProperty());

	    // Add to the scene
	    nodeLayer.getChildren().add(newNode);
	    render();
	}

	/**
	 * Add EdgeDisplays from selected NodeQueue
	 * Use to add a non-existing EdgeDisplay and Edge to the display
	 */
	public void addEdgeDisplayFromQueue(){
		SelectEvent selectNodeEvent = new SelectEvent(SelectEvent.NODE_DESELECTED);
	    if(!nodeQueue.isEmpty()){
	        //////System.out.println(nodeQueue.size());
	        while(nodeQueue.size() > 1){
	            NodeDisplay aND = nodeQueue.poll();
	            NodeDisplay bND = nodeQueue.peek();
	            Graph g = display.getGraph();
	            Node a = g.returnNodeById(aND.getNode());
	            Node b = g.returnNodeById(bND.getNode());
	            try{
	            }
	            catch(NullPointerException exception){
	                ////System.out.println("a thing broke");
	                break;
	            }


	            Id newEdge = g.addEdgeRint(a.getId(), b.getId());
	            EdgeDisplay e = new EdgeDisplay(display, newEdge);
	            edgeLayer.getChildren().add(e);
	            aND.fireEvent(selectNodeEvent);
	            /*
	            e.addEventFilter(SelectEvent.EDGE_SELECTED, ev -> {
	            	if(isEdgeEditor){
	            		////System.out.println("Edge deleted");
	    	        	Id id = e.getEdge();
	    	        	display.getGraph().deleteEdge(id);
	    	        	edgeLayer.getChildren().remove(e);
	            	} else {
	            		////System.out.println("Edge selected");
		            	e.selectEdge();
		            	edgeQueue.clear();
		            	edgeQueue.add(e);
		            	//TODO @jules find a way to display edge information using new controller
		            	//ControllerMediator cm = ControllerMediator.getInstance();
		            	//cm.viewDisplayItem(e);
	            	}
	            });
	            e.addEventFilter(SelectEvent.EDGE_DESELECTED, ev -> {
	            	//do sth;
	            	edgeQueue.remove(e);
	            });
	            
	            e.addEventFilter(EditorEvent.DELETE_EDGE, ev -> {
	            	edgeLayer.getChildren().remove(e);
	            	display.getGraph().deleteEdge(e.getEdge());
	            	render();
	            });
	            */
	        }
	        nodeQueue.remove().fireEvent(selectNodeEvent);;
	    } else {
	    	//TODO: Display message in editor that says no nodes are being selected
	    }
	    render();
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
	    	if(edge == null) continue;
	    	Id aID = edge.getNodeA();
		    Id bID = edge.getNodeB();
		    Node a = graph.returnNodeById(aID);
		    Node b = graph.returnNodeById(bID);
		    if(a == null || b == null){
		    	graph.deleteEdge(edge.getId());
		    	continue;
		    }

	     
	        //////System.out.println("Edge size" + g.getEdges().size());
	        EdgeDisplay e = new EdgeDisplay(display, aID, bID);
            
            //e.setStroke(Color.BLUE);
            edgeLayer.getChildren().add(e);

            e.addEventFilter(SelectEvent.EDGE_SELECTED, ev -> {
            	////System.out.println("Edge selected");
            	e.selectEdge();
            	edgeQueue.clear();
            	edgeQueue.add(e);
            	//TODO @jules same thing about viewing selected edge
            	//ControllerMediator cm = ControllerMediator.getInstance();
            	//cm.viewDisplayItem(e);
            });

            e.addEventFilter(SelectEvent.EDGE_DESELECTED, ev -> {
            	//do sth;
            	edgeQueue.remove(e);
            });
            
            e.addEventFilter(EditorEvent.DELETE_EDGE, ev -> {
            	edgeLayer.getChildren().remove(e);
            	display.getGraph().deleteEdge(e.getEdge());
            	render();
            });
     
	    }
	}
    /**
     * Draws a path from the last two selected nodes
     */
	public void drawPath(){
		pathPane.getChildren().clear();
        NodeDisplay startNode = nodeQueue.poll();
        NodeDisplay endNode = nodeQueue.poll();
        if(startNode != null && endNode != null){
            //display.drawPath(startNode.node.getId(), endNode.node.getId());
            int idx = 0;
            Vector<Node> nodes = display.getGraph().getNodes();

            p = new Path(startNode.getNode(), endNode.getNode());
            Graph g = display.getGraph();
            p.runAStar(g); //Change this later??
            currentRoute = p.getRoute();
            
            render();
            SelectEvent selectNodeEvent = new SelectEvent(SelectEvent.NODE_DESELECTED);
            startNode.fireEvent(selectNodeEvent);
            endNode.fireEvent(selectNodeEvent);
            showDirections();
        }
	}

    public void showDirections(){
        ObservableList<String> pathList = FXCollections.observableList(p.getTextual());
        ControllerSingleton.getInstance().getMenuPane().getDirectionsMenuPane().getdirectionsListView().setItems(pathList);
    }

}
