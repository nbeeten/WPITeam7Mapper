package edu.wpi.off.by.one.errors.code;

//MY ATTEMPT AT THE GUI, THE DESIGN IS NOT APPEALING AS THIS IS STILL A WORK IN PROGRESS
//I MADE IT JUST TO ENSURE THAT THE BASIC FUNCTIONALITY OF THE UI IS IN ORDER
//THE NEXT ASPECTS THAT NEED TO BE IMPLEMENTED ARE THE BACKEND ALGO'S AND DATA STRUCTURES

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
//import javafx.scene.control.Separator;
import javafx.stage.Stage;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;



public class GUI extends Application {
	
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
	
	@Override
	public void start(Stage primaryStage){
		
		text.setFont(Font.font("Times New Roman"));
		text.setStrokeWidth(8);
		
		
		showMap.setOnAction(new EventHandler<ActionEvent>(){
			
			Image img = new Image("file:src/campusmap.png");
			
			@Override
			public void handle(ActionEvent e){
				imgpic.setImage(img);
			}//end of method handle
		});
		
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setPadding(new Insets(5.0));
		
		BorderPane root = new BorderPane();
		root.setCenter(imgpic);
		
//Scrolling capability
		ScrollPane sp = new ScrollPane();
		sp.setContent(root);
		sp.setPannable(true);
		sp.setFitToHeight(true);
		
		
//  ZOOM
//******************************************************************************************************************************************************************		
		//increases the value of the zoomProperty to be added to scroll pane for magnification
		zoomProperty.addListener(new InvalidationListener(){
			@Override
			public void invalidated(Observable arg){
				imgpic.setFitWidth(zoomProperty.get()*4);
				imgpic.setFitHeight(zoomProperty.get()*3);
			}
		});
		
		//adds the scroll pane event filter which increases the magnification
		sp.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>(){
			@Override
			public void handle(ScrollEvent e){
				if(e.getDeltaY() > 0){
					zoomProperty.set(zoomProperty.get()* 1.1);
				}else if(e.getDeltaY() < 0){
					zoomProperty.set(zoomProperty.get()/1.1);
				}
			}
		});
		
//********************************************************************************************************************************************************************	
		
		
		HBox buttons = new HBox();
		
		buttons.getChildren().addAll(newDest,addNode,editNode,addEdge,editEdge,saveNodes,showMap);
		
		grid.add(buttons,0,0);//buttons added to grid
		grid.add(sp, 0, 1); // scroll pane added to grid
		grid.add(location,0,480); //location label added
		grid.add(coord, 0, 500);// coordinates label added
		grid.add(text, 0,450); //information bar added...KINDA
				
		Scene scene = new Scene(grid, 500, 500);
		
		//displays the x and Y coordinates of the mouse click relative to the top-left corner of the map
		imgpic.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				String loc = ("X: "+e.getX()+", Y: "+e.getY());
				coord.setText(loc);
			}
		});
		
		primaryStage.setTitle("WPI map");
		primaryStage.setScene(scene);
		primaryStage.show();

		
	}//end method start
	
	
	public static void main(String[] args){
		launch(args);
	}//

}//end class GUI

