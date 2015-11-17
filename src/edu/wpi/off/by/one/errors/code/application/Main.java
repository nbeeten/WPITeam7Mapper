package edu.wpi.off.by.one.errors.code.application;

import java.util.Collection;

<<<<<<< HEAD
import edu.wpi.off.by.one.errors.code.Display;
=======
import edu.wpi.off.by.one.errors.code.*;
>>>>>>> 48c24583a027b026cad697a94b87598a3c2d594a
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This Main class launches the application and sets up all the GUI interactions
 * Mainly talks to the Display class to send manipulate data in some way to the
 * underlying data.
 * 
 * p.s. whoever is going to be working on GUI feel free to add your name to this?
 * @author kezhang
 *
 */
public class Main extends Application {
	
	Display display;

	private Stage window;
	private Scene scene;
	
	private BorderPane root;
	private ScrollPane scroll;
	
	private ImageView mapView;
	private StackPane mapPane;
	
	final SimpleDoubleProperty zoomProperty = new SimpleDoubleProperty(200);

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * Last Edited 11/9/15 I apologize in advance if this code is extremely
	 * messy This is just a starting point for now I'll get a better version of
	 * the map for later - Kelly
	 */

	@Override
	public void start(Stage primaryStage) {

		window = primaryStage; //Main window

		// Set main scene
		root = new BorderPane();
		scene = new Scene(root, 1600, 1000);
		
		/* root BorderPane
		 * TOP: MenuBar
		 * BOTTOM: Editor logger (Maybe?????)
		 * CENTER: Inner BorderPane
		 * 
		 * Inner BorderPane will contain
		 * CENTER: Map component
		 * RIGHT: Editor section/Logo + Directions
		 * 
		 */
		BorderPane innerBorderPane = new BorderPane();
		innerBorderPane.setPadding(new Insets(20, 20, 20, 20));


		VBox infoBox = new VBox(2);
		
		// START: BORDERPANE RIGHT COMPONENTS --------------------------------------------
		VBox editorBox = new VBox();
		VBox addPane = new VBox(5);
		editorBox.setPrefSize(scene.getWidth() * 0.3, 600);
		editorBox.setPadding(new Insets(0, 0, 0, 10));
		addPane.setPadding(new Insets(0, 0, 100, 0));

		Label nodeLabel = new Label("Editor");
		Label settingsLabel = new Label("Directions");
		Separator sep = new Separator();
		Separator sep1 = new Separator();

		VBox directionsBox = new VBox(10);
		Button setStart = new Button("Set Start");
		Button setEnd = new Button("Set End");
		Button drawPathButton = new Button("Draw Path");
		// Add/Edit Buttons
		/* IN PROGRESS PLIS WAIT */
		Button addNode = new Button("Add Node");
		Button editNode = new Button("Edit Node");
		Button addEdge = new Button("Add Edge");
		Button editEdge = new Button("Edit Edge");
		Button saveNodes = new Button("Save All");
		
		//This was to test the Dialog box, feel free to delete it.
		editNode.setOnAction(e ->{
			Coordinate testing = new Coordinate(1, 2, 3);
			CoordinateDialogBox box = new CoordinateDialogBox(testing);
			box.display("Edit Node");
			System.out.println(String.valueOf(testing.getX()));
		});
		
		//Button addGraph = new Button("Add Graph");
		Button clearNodes = new Button("Clear All");

		Label show = new Label("Show");
		CheckBox showNodes = new CheckBox("Nodes");
		CheckBox showEdges = new CheckBox("Edges");

				addPane.getChildren().addAll(nodeLabel, sep, addNode, editNode, addEdge, editEdge, 
						saveNodes, clearNodes, show,						
						showNodes, showEdges);

		editorBox.getChildren().addAll(addPane, settingsLabel, sep1);
		
		// END: BORDERPANE RIGHT COMPONENTS --------------------------------------------
		
		// START: BORDERPANE CENTER COMPONENTS -----------------------------------------
		// AKA - Map stuff
		
		mapPane = new StackPane();
		mapView = GetMapView();
		
		// Create Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.autosize();
		Menu fileMenu = new Menu("File");
		Menu viewMenu = new Menu("View");
		Menu helpMenu = new Menu("Help");

		MenuItem importMenuItem = new MenuItem("Import...");
		fileMenu.getItems().add(importMenuItem);

		CheckMenuItem setEditorMode = new CheckMenuItem("Editor Mode");
		setEditorMode.setSelected(true);
		viewMenu.getItems().add(setEditorMode);
		addPane.visibleProperty().bind(setEditorMode.selectedProperty());
		menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);
		
		scroll = createScrollPane(mapPane);
		innerBorderPane.setCenter(scroll);
		innerBorderPane.setRight(editorBox);

		/* SOMEWHERE AROUND HERE DO STUFF WITH DISPLAY CLASS */
		/* OR! Leave map empty and wait for file loading */
		
		//mapPane.scaleXProperty().bind(zoomProperty);
		//mapPane.scaleYProperty().bind(zoomProperty);
		mapPane.getChildren().add(mapView);
		
		root.setTop(menuBar);
		root.setCenter(innerBorderPane);
		root.setBottom(infoBox);

		AddSceneListeners();
		window.setTitle("WPI Map Application");
		window.setScene(scene);
		window.show();

		scroll.setCenterShape(true);
		
		scroll.prefWidthProperty().bind(scene.widthProperty());
		scroll.prefHeightProperty().bind(scene.widthProperty());

		// center the scroll contents.
		scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
		scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

		//mapPane.setMaxSize(1000, 1000);
		
	} 
	
	// Gets the map image, sets properties, returns a usable node by JavaFX
	// Should be getting it from Display instead (?)
	// TODO delegate this to Map????? or even Display???????????
	private ImageView GetMapView() {
		Image map = new Image("campusmap.png");
		ImageView mapIV = new ImageView();
		mapIV.setImage(map);
		mapIV.setPreserveRatio(true);
		mapIV.setSmooth(true);
		mapIV.setCache(true);
		return mapIV;
	}

	private ScrollPane createScrollPane(Pane layout) {
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setPannable(true);
		scroll.setPrefSize(600, 600);
		scroll.setContent(layout);
		return scroll;
	}
	
	private void AddSceneListeners() {
	
		// TODO oh my god please i need to clean this up
		
		//increases the value of the zoomProperty to be added to scroll pane for magnification
		/*
		zoomProperty.addListener(new InvalidationListener(){
			@Override
			public void invalidated(Observable arg){
				mapView.setFitWidth(zoomProperty.get()*4);
				mapView.setFitHeight(zoomProperty.get()*3);
			}
		});
		
		//adds the scroll pane event filter which increases the magnification
		scroll.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>(){
			@Override
			public void handle(ScrollEvent e){
				if(e.getDeltaY() > 0){
					zoomProperty.set(zoomProperty.get()* 1.1);
				}else if(e.getDeltaY() < 0){
					zoomProperty.set(zoomProperty.get()/1.1);
				}
			}
		});
		*/
		// Listens to clicks on the map
		mapView.setOnMouseClicked(e -> {
			if(e.isStillSincePress()) {
				// If user did not click-drag the map 
				// Data to show coordinates of where user has clicked on map, delete later
				String msg = "(x: " + e.getX() + ", y: " + e.getY() + ") -- " + "(sceneX: " + e.getSceneX() + ", sceneY: "
						+ e.getSceneY() + ") -- " + "(screenX: " + e.getScreenX() + ", screenY: " + e.getScreenY() + ")";
				System.out.println(msg);
			}
			
			// If double-click
			if (e.getClickCount() == 2) {
				// Creates a NodeDisplay that contains the node object
				NodeDisplay newNode = new NodeDisplay(e.getX(), e.getY(), 0);

				// Shift coordinates for node so that it is placed where user
				// clicks
				Bounds localBounds = newNode.localToScene(mapView.getBoundsInLocal());
				newNode.setTranslateX(e.getX() - localBounds.getMaxX() / 2);
				newNode.setTranslateY(e.getY() - localBounds.getMaxY() / 2);

				newNode.addEventFilter(newNode.NODE_SELECTED, event -> {
					System.out.println("Node Selected");
					// Add selected node to selected ndoe queue
					
					//TODO stuff regarding info about the node clicked
					//if double-clicked
					//if in edit mode
					//if in add edge mode
					//	if 2 nodes are already selected when add edge is pressed,
					//	then create an edge between those two nodes
					//	if >2 nodes are selected, then edges will be added in order
					//	of selection
				});
				
				// Add to the scene
				mapPane.getChildren().add(newNode);
			}
		});		
	}	
}
