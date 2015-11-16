package edu.wpi.off.by.one.errors.code.application;

import java.util.Collection;
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

public class Main extends Application {

	private Scene scene;
	private ImageView mapView;
	private StackPane mapPane;
	private Stage window;
	private BorderPane root;
	private ScrollPane scroll;
	
	double x;
	double y;
	
	Label location = new Label("Mouse location");
	Label coord = new Label("coordinates:");
	Text text = new Text();
	
	Button newDest = new Button("add Destination");
	Button addNode = new Button("Add Node");
	Button editNode = new Button("Edit Node");
	Button addEdge = new Button("Add Edge");
	Button editEdge = new Button("Edit Edge");
	Button saveNodes = new Button("Save All");
	Button showMap = new Button("Show map");
	
	ImageView imgpic = new ImageView();
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

		window = primaryStage;

		// Set main scene
		root = new BorderPane();
		scene = new Scene(root, 1000, 800);

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(20, 20, 20, 20));

		// SettingsBox contains AddPane and StackPane
		VBox settingsBox = new VBox();
		VBox addPane = new VBox(5);
		settingsBox.setPrefSize(scene.getWidth() * 0.3, 600);
		settingsBox.setPadding(new Insets(0, 0, 0, 10));
		addPane.setPadding(new Insets(0, 0, 100, 0));

		Label nodeLabel = new Label("Editor");
		Label settingsLabel = new Label("Directions");
		Separator sep = new Separator();
		Separator sep1 = new Separator();

		VBox directionsBox = new VBox(10);

		// Add/Edit Buttons
		Button addNode = new Button("Add Node");
		Button editNode = new Button("Edit Node");
		Button addEdge = new Button("Add Edge");
		Button editEdge = new Button("Edit Edge");
		Button saveNodes = new Button("Save All");

		Button clearNodes = new Button("Clear All");

		Label show = new Label("Show");
		CheckBox showNodes = new CheckBox("Nodes");
		CheckBox showEdges = new CheckBox("Edges");

		/*
		addNode.setOnMouseReleased(e -> {
			Dialog d = new Dialog();
			d.initOwner(primaryStage);
			d.initStyle(StageStyle.UTILITY);
			d.initModality(Modality.NONE);
			d.setTitle("Add Node");

			Label mainLabel = new Label("Set Coordinates");
			Label xLabel = new Label("X\t");
			Label yLabel = new Label("Y\t");
			Label zLabel = new Label("Z\t");

			TextField xField = new TextField("0");
			TextField yField = new TextField("0");
			TextField zField = new TextField("0");

			Button addButton = new Button("Add");
			Button cancelButton = new Button("Cancel");
			cancelButton.setOnMouseClicked(ev -> {
				d.close();
			});

			GridPane grid = new GridPane();
			grid.add(mainLabel, 1, 1);
			grid.add(xLabel, 1, 2);
			grid.add(yLabel, 1, 3);
			grid.add(zLabel, 1, 4);

			grid.add(xField, 2, 2);
			grid.add(yField, 2, 3);
			grid.add(zField, 2, 4);

			grid.add(addButton, 2, 5);
			grid.add(cancelButton, 2, 5);

			HBox buttonPane = new HBox(10);
			buttonPane.getChildren().addAll(addButton, cancelButton);

			VBox pane = new VBox(10);
			pane.getChildren().addAll(grid, buttonPane);

			d.getDialogPane().setContent(pane);
			d.show();

		});
		*/
		addPane.getChildren().addAll(nodeLabel, sep, addNode, editNode, addEdge, editEdge, saveNodes, clearNodes, show,
				showNodes, showEdges);

		settingsBox.getChildren().addAll(addPane, settingsLabel, sep1);

		mapPane = new StackPane();
		mapView = GetMapView();
		scroll = createScrollPane(mapPane);

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

		borderPane.setCenter(scroll);
		borderPane.setRight(settingsBox);

		//mapPane.scaleXProperty().bind(zoomProperty);
		//mapPane.scaleYProperty().bind(zoomProperty);
		mapPane.getChildren().add(mapView);

		root.setTop(menuBar);
		root.setCenter(borderPane);

		AddSceneListeners();
		window.setTitle("WPI Map Application");
		window.setScene(scene);
		window.show();

		scroll.prefWidthProperty().bind(scene.widthProperty());
		scroll.prefHeightProperty().bind(scene.widthProperty());

		// center the scroll contents.
		scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
		scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);

		//mapPane.setMaxSize(1000, 1000);
		
	}

	// Gets the map image, sets properties, returns a usable node by JavaFX
	private ImageView GetMapView() {
		Image map = new Image("campusmap.png");
		ImageView mapIV = new ImageView();
		mapIV.setImage(map);
		// mapIV.setFitWidth(scene.widthProperty().get() / 2);
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
		//scroll.setVmax(mapPane.getHeight());
		//scroll.setHmax(mapPane.getWidth());
		//scroll.setFitToHeight(true);
		scroll.setContent(layout);
		return scroll;
	}

	
	private void AddSceneListeners() {
		
		//increases the value of the zoomProperty to be added to scroll pane for magnification
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

		mapView.setOnMouseDragged(e -> {
			System.out.println("I'm being dragged");
		});
		
		// Listens to clicks on the map
		mapView.setOnMouseClicked(e -> {
			// Data where user has clicked relative to map, scene, and screen
			// Delete later
			if(e.isStillSincePress()) {
				//
			}
			String msg = "(x: " + e.getX() + ", y: " + e.getY() + ") -- " + "(sceneX: " + e.getSceneX() + ", sceneY: "
					+ e.getSceneY() + ") -- " + "(screenX: " + e.getScreenX() + ", screenY: " + e.getScreenY() + ")";

			System.out.println(msg);
			if (e.getClickCount() == 2) {
				// Replace this with a node object
				Button roundButton = new Button("a");

				// Shift coordinates for node so that it is placed where user
				// clicks
				Bounds localBounds = roundButton.localToScene(mapView.getBoundsInLocal());
				roundButton.setTranslateX(e.getX() - localBounds.getMaxX() / 2);
				roundButton.setTranslateY(e.getY() - localBounds.getMaxY() / 2);

				// Set CSS Style of the node
				roundButton
						.setStyle("-fx-background-color:blue;" + "-fx-background-radius: 5em;" + "-fx-min-width: 8px;"
								+ "-fx-min-height: 8px;" + "-fx-max-width: 8px;" + "-fx-max-height: 8px;");

				roundButton.setOnMouseEntered(be -> {
					String style = roundButton.getStyle();
					roundButton.setStyle(style + "-fx-border-radius: 5em;" + "-fx-border-color:black;"
							+ "-fx-border-width: 1px;" + "-fx-border-style: solid;");
				});

				roundButton.setOnMouseExited(be -> {
					String style = roundButton.getStyle();
					roundButton.setStyle(style + "-fx-border-radius: none;" + "-fx-border-color: none;"
							+ "-fx-border-width: none;" + "-fx-border-style: none;");
				});

				// Add to the scene
				mapPane.getChildren().add(roundButton);
			}
		});		
		/*
		mapView.setOnScroll(e -> {
			double delta = 1.2;

            double scale = zoomProperty.get(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            if (e.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = clamp( scale, 0.1d, 10.0d);

            double f = (scale / oldScale)-1;

            double dx = (e.getSceneX() - (mapPane.getBoundsInParent().getWidth()/2 + mapView.getBoundsInParent().getMinX()));
            double dy = (e.getSceneY() - (mapView.getBoundsInParent().getHeight()/2 + mapView.getBoundsInParent().getMinY()));

            zoomProperty.set(scale);

            // note: pivot value must be untransformed, i. e. without scaling
            mapView.setTranslateX(mapView.getTranslateX()-f*dx);
            mapView.setTranslateY(mapView.getTranslateY()-f*dy);

            e.consume();
		});
		*/
	}
	
	public static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }
}
