package edu.wpi.off.by.one.errors.code.controller.menupanes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import edu.wpi.off.by.one.errors.code.controller.MainPane;
import edu.wpi.off.by.one.errors.code.controller.MapRootPane;
import edu.wpi.off.by.one.errors.code.controller.menupanes.devtoolspanes.*;
import edu.wpi.off.by.one.errors.code.model.Display;
import edu.wpi.off.by.one.errors.code.model.Edge;
import edu.wpi.off.by.one.errors.code.model.FileIO;
import edu.wpi.off.by.one.errors.code.model.Graph;
import edu.wpi.off.by.one.errors.code.model.Map;

/**
 * Created by jules on 11/28/2015.
 */
public class DevToolsMenuPane extends BorderPane {
	
	MainPane mainPane;
	
	@FXML RadioButton mapPaneRadioButton;
	@FXML MapDevToolPane mapDevToolPane;
	@FXML NodeDevToolPane nodeDevToolPane;
	@FXML EdgeDevToolPane edgeDevToolPane;
	@FXML PathDevToolPane pathDevToolPane;
	@FXML Button loadNewImageButton;
	@FXML Button loadNewMapButton;
	@FXML Button appendMapButton;
	@FXML Button saveCurrentMapButton;
	

    public DevToolsMenuPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/menupanes/DevToolsMenuPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        
        try{
            loader.load();
            
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }
        setListeners();
        this.getStylesheets().add(getClass().getResource("../../resources/stylesheets/menupanes/DevToolsPaneStyleSheet.css").toExternalForm());

    }
    
    private void setListeners(){
    	this.visibleProperty().addListener(change -> {
    		//Map currentMap = mainPane.getMapRootPane().getDisplay().getMap();
    		//mapDevToolPane.setMap(currentMap);
    		mainPane.getMapRootPane().isEditMode = this.isVisible() ? true : false;
    		mainPane.getMapRootPane().render();
    		
    	});
    	
    	this.loadNewImageButton.setOnAction(e -> {
    		Display display = mainPane.getMapRootPane().getDisplay();
        	FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Map File");
            fileChooser.getExtensionFilters().addAll(
                                                     new ExtensionFilter("Image Files", "*.png"),
                                                     new ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(mainPane.getWindow());
            if (selectedFile != null) {
                String inpath = selectedFile.getName();
                System.out.println(inpath);
                Map newmap = new Map();
                newmap.setImgUrl(inpath);
                newmap.setScale(0.5f);
                display.addMap(newmap);
                //display.setMap(newmap);
                mainPane.getMapRootPane().render();
                //mainPane.getMapRootPane().updateDisplay(display, "NEW");
                //mapView.setImage(new Image("/edu/wpi/off/by/one/errors/code/resources/" + inpath));
                //window.display(selectedFile);
                mapDevToolPane.updateMapList(mainPane.getMapRootPane().getDisplay().getMaps());
                //mapDevToolPane.setMap(newdisp.getMap());
                mainPane.getMenuPane().getSearchMenuPane().updateMapList(mainPane.getMapRootPane().getDisplay().getMaps());

            }
    	});
    	/*
    	this.loadNewMapButton.setOnAction(e -> {
    		Display newdisp = null;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Map File");
            fileChooser.getExtensionFilters().addAll(
                                                     new ExtensionFilter("Text Files", "*.txt"),
                                                     new ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(mainPane.getWindow());
            if (selectedFile != null) {
                String inpath = selectedFile.getPath();
                System.out.println(inpath);
                newdisp = FileIO.load(inpath, null);
                //mainPane.getMapRootPane().updateDisplay(newdisp, "NEW");
                mapDevToolPane.setMap(newdisp.getMaps().get(0));
            }
    	});
    	*/
    	// TODO Append new map onto current map on a separate pane layer
    	this.appendMapButton.setOnAction(e -> {
    		Display newdisp = mainPane.getMapRootPane().getDisplay();
    		
       
    		FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Map File");
            fileChooser.getExtensionFilters().addAll(
                                                     new ExtensionFilter("Text Files", "*.txt"),
                                                     new ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(mainPane.getWindow());
            if (selectedFile != null) {
                String inpath = selectedFile.getPath();
                System.out.println(inpath);
                newdisp = FileIO.load(inpath, newdisp);
                mainPane.getMapRootPane().updateDisplay(newdisp, "APPEND");
                mapDevToolPane.updateMapList(mainPane.getMapRootPane().getDisplay().getMaps());
                //mapDevToolPane.setMap(newdisp.getMap());
                mainPane.getMenuPane().getSearchMenuPane().updateMapList(mainPane.getMapRootPane().getDisplay().getMaps());
            }
           
            
    	});
    	
    	this.saveCurrentMapButton.setOnAction(e -> {
    		/*
    		FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Map File");
            fileChooser.getExtensionFilters().addAll(
                                                     new ExtensionFilter("Text Files", "*.txt"),
                                                     new ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showSaveDialog(mainPane.getWindow());
            */
            //selectedFile.getAbsolutePath();
            FileIO.save(mainPane.getMapRootPane().getFilePath(), mainPane.getMapRootPane().getDisplay());
    	});
    }
    public NodeDevToolPane getNodeDevToolPane() { return nodeDevToolPane; }
    public EdgeDevToolPane getEdgeDevToolPane() { return edgeDevToolPane; }
    public PathDevToolPane getPathDevToolPane() { return pathDevToolPane; }
	public void setMainPane(MainPane mainPane) {
		this.mainPane = mainPane;
		mapDevToolPane.setMainPane(mainPane);
		nodeDevToolPane.setMainPane(mainPane);
		edgeDevToolPane.setMainPane(mainPane);
		//pathDevToolPane.setMainPane(mainPane);
		
	}
    
}
