import java.util.Collection;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Screen;
import javafx.stage.Stage;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application{
	
	private Scene scene;
	private ImageView mapView;
	private StackPane mapPane;
	private Stage window;
	private StackPane root;

	public static void main(String[] args) {
		launch(args);
	}
	
	/*
	 * Last Edited 11/9/15
	 * I apologize in advance if this code is extremely messy
	 * This is just a starting point for now
	 * I'll get a better version of the map for later
	 * - Kelly
	 */
	
	@Override
	public void start(Stage primaryStage) {
		
		window = primaryStage;
		
		//Set main scene
		root = new StackPane();
		scene = new Scene(root, 1000, 800);
		
		//Set Tabs
		TabPane tabPane = new TabPane();
		Tab editTab = new Tab();
		Tab userTab = new Tab();
		editTab.setText("Editor");
		userTab.setText("User");
		
		//Set BorderLayout in Editor Tab
		// TODO: Also do the same for User tab later on
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(20, 20, 20, 20));

		// SettingsBox contains AddPane and StackPane
		VBox settingsBox = new VBox();
		VBox addPane = new VBox(5);
		StackPane settingsPane = new StackPane();
		
		Label nodeLabel = new Label("Node Tools");
		Label settingsLabel = new Label("Additional Settings");
		Separator sep = new Separator();
		Separator sep1 = new Separator();
		
		//Add/Edit Buttons
		Button addNode = new Button("Add Node");
		Button editNode = new Button("Edit Node");
		Button saveNodes = new Button("Save Nodes");
		Button clearNodes = new Button("Clear Nodes");
		
		
		addPane.getChildren().addAll(addNode, editNode, saveNodes, clearNodes);
		
		settingsBox.getChildren().addAll(nodeLabel, sep, 
									addPane,
									settingsLabel, sep1);
		
		mapPane = new StackPane();
		mapPane.setPadding(new Insets(10, 10, 10, 10));
		mapView = GetMapView();

		editTab.setContent(borderPane);		

		borderPane.setCenter(mapPane);
		borderPane.setRight(settingsBox);
		
		mapPane.getChildren().add(mapView);
		
		tabPane.getTabs().addAll(editTab, userTab);
		
		root.getChildren().setAll(
				tabPane
		); 
		
		AddSceneListeners();
		window.setTitle("Map Application");
		window.setScene(scene);
		window.show();
	}

	//Gets the map image, sets properties, returns a usable node by JavaFX
	private ImageView GetMapView(){
		Image map = new Image("/userinterface/resources/map.png");
		ImageView mapIV = new ImageView();
		mapIV.setImage(map);
		mapIV.setFitWidth(scene.widthProperty().get() / 2);
		mapIV.setPreserveRatio(true);
		mapIV.setSmooth(true);
		mapIV.setCache(true);
		
		return mapIV;
	}
	
	private void AddSceneListeners(){
		
		//Listens to clicks on the map
		mapView.setOnMousePressed(e -> {
			//Data where user has clicked relative to map, scene, and screen
			//Delete later
			String msg =
			          "(x: "       + e.getX()      + ", y: "       + e.getY()       + ") -- " +
			          "(sceneX: "  + e.getSceneX() + ", sceneY: "  + e.getSceneY()  + ") -- " +
			          "(screenX: " + e.getScreenX()+ ", screenY: " + e.getScreenY() + ")";

			System.out.println(msg);
			
			//Replace this with a node object
			Button roundButton = new Button("a");
			
			//Shift coordinates for node so that it is placed where user clicks
	        Bounds localBounds = roundButton.localToScene(mapView.getBoundsInLocal());
			roundButton.setTranslateX(e.getX() - localBounds.getMaxX()/2);
			roundButton.setTranslateY(e.getY() - localBounds.getMaxY()/2);
			
			//Set CSS Style of the node
			roundButton.setStyle(
					"-fx-background-color:blue;"
					+ "-fx-background-radius: 5em;"
					+ "-fx-min-width: 20px;"
					+ "-fx-min-height: 20px;"
					+ "-fx-max-width: 20px;"
					+ "-fx-max-height: 20px;");

			roundButton.setOnMouseEntered(be -> {
				String style = roundButton.getStyle();
				roundButton.setStyle(style 
						+ "-fx-border-radius: 5em;"
						+ "-fx-border-color:black;"
						+ "-fx-border-width: 1px;"
						+ "-fx-border-style: solid;");
			});
			
			roundButton.setOnMouseExited(be -> {
				String style = roundButton.getStyle();
				roundButton.setStyle(style 
						+ "-fx-border-radius: none;"
						+ "-fx-border-color: none;"
						+ "-fx-border-width: none;"
						+ "-fx-border-style: none;");
			});
			
			//Add to the scene
	        mapPane.getChildren().add(roundButton);
		});
		
	} 
}
