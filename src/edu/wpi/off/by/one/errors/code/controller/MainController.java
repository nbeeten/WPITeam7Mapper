package edu.wpi.off.by.one.errors.code.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import edu.wpi.off.by.one.errors.code.application.NodeDisplay;
import edu.wpi.off.by.one.errors.code.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class MainController implements Initializable{

	StackPane mapPane;
	@FXML
	ImageView mapView;
	@FXML
	ScrollPane mapScrollPane;
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
		mapView.setPreserveRatio(true);
        mapView.setSmooth(true);
        mapView.setCache(true);
	
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

}
