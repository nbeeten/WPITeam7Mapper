package edu.wpi.off.by.one.errors.code.controller.menupanes.devtoolspanes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.off.by.one.errors.code.controller.MainPane;
import edu.wpi.off.by.one.errors.code.model.Coordinate;
import edu.wpi.off.by.one.errors.code.model.Display;
import edu.wpi.off.by.one.errors.code.model.Map;

/**
 * Created by jules on 11/30/2015.
 */
public class MapDevToolPane extends VBox {
	
	Display currentDisplay;
	Map currentMap;
	
	MainPane mainPane;
	HashMap<String,Display> displayList;
	
	@FXML public ChoiceBox<String> mapChoiceBox;
	@FXML TextField nameTextField;
	@FXML TextField xTextField;
	@FXML TextField yTextField;
	@FXML TextField zTextField;
	@FXML TextField rotationTextField;
	@FXML TextField scaleTextField;
	@FXML Label pathLabel;
	@FXML Button changeImageButton;
	private ArrayList<Map> mapList;
	private Map selectedMap;
	

    public MapDevToolPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/menupanes/devtoolspanes/MapDevToolPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            setListeners();
        } catch (IOException excpt) {
            throw new RuntimeException(excpt);
        }
    }
    
    private void setListeners(){  	
    	this.mapChoiceBox.setOnAction(e -> {
    		//should be change map info
    		changeDisplay();
    	});
    	
    	this.mapChoiceBox.setOnContextMenuRequested(e->{
    		mapChoiceBox.getItems().clear();
    		this.mapList = mainPane.getMapRootPane().getDisplay().getMaps(); 
        	for(Map m : mainPane.getMapRootPane().getDisplay().getMaps()){
            	String name = (m.getName() == null) ? m.getImgUrl() : m.getName();
            	mapChoiceBox.getItems().add(name);
            }
    	});
    	this.nameTextField.setOnKeyPressed(e -> {
    		String s = nameTextField.getText();
    		currentMap.setName(s);
    		mainPane.getMapRootPane().render();
    		//updateDisplayList(old_s, s);
    	});
    	
    	this.rotationTextField.setOnKeyPressed(e -> {
    		String s = rotationTextField.getText();
    		//System.out.println(s);
    		if(s == null) s = "0";
    		currentMap.setRotation(Float.parseFloat(s));
    		mainPane.getMapRootPane().render();
    		//d.setMap(currentMap);
    		//displayList.put(name, d);
    	});
    	
    	this.scaleTextField.setOnKeyPressed(e -> {
    		String s = scaleTextField.getText();
    		if(s == null) s = "0";
    		currentMap.setScale(Float.parseFloat(s));
    		mainPane.getMapRootPane().render();
    		//d.setMap(currentMap);
    		//displayList.put(name, d);
    	});
    	
    	this.xTextField.setOnKeyPressed(e -> {
    		String s = xTextField.getText();
    		if(s == null) s = "0";
    		Coordinate currentc = currentMap.getCenter();
    		currentMap.setCenter(new Coordinate(Float.parseFloat(s),
    				currentc.getY(), currentc.getZ()));
    		mainPane.getMapRootPane().render();
    		//d.setMap(currentMap);
    		//displayList.put(name, d);
    	});
    	
    	this.yTextField.setOnKeyPressed(e -> {
    		String s = yTextField.getText();
    		if(s == null) s = "0";
    		Coordinate currentc = currentMap.getCenter();
    		currentMap.setCenter(new Coordinate(currentc.getX(),
    				Float.parseFloat(s), currentc.getZ()));
    		mainPane.getMapRootPane().render();
    		//d.setMap(currentMap);
    		//displayList.put(name, d);
    	});
    	
    	this.zTextField.setOnKeyPressed(e -> {
    		String s = zTextField.getText();
    		if(s == null) s = "0";
    		Coordinate currentc = currentMap.getCenter();
    		currentMap.setCenter(new Coordinate(currentc.getX(),
    				currentc.getY(), Float.parseFloat(s)));
    		mainPane.getMapRootPane().render();
    		//d.setMap(currentMap);
    		//displayList.put(name, d);
    	});
    }
    
    public void setMainPane(MainPane mainPane) { 
    	this.mainPane = mainPane; 
    	this.mapList = mainPane.getMapRootPane().getDisplay().getMaps(); 
    	for(Map m : mainPane.getMapRootPane().getDisplay().getMaps()){
        	String name = (m.getName() == null) ? m.getImgUrl() : m.getName();
        	mapChoiceBox.getItems().add(name);
        }
    }
    
    public void setMap(Map m) { 
    	this.currentMap = m;
    	if(m.getName() != null) this.nameTextField.setText(m.getName());
    	else this.nameTextField.clear();
    	this.xTextField.setText(Float.toString(m.getCenter().getX()));
    	this.yTextField.setText(Float.toString(m.getCenter().getY()));
    	this.zTextField.setText(Float.toString(m.getCenter().getZ()));
    	this.scaleTextField.setText(Float.toString(m.getScale()));
    	this.rotationTextField.setText(Float.toString(m.getRotation()));
    	this.pathLabel.setText(m.getImgUrl());
    }
    
    @FXML public void changeImage(){
		Display display = mainPane.getMapRootPane().getDisplay();
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Map File");
        fileChooser.getExtensionFilters().addAll(
                                                 new ExtensionFilter("Image Files", "*.png"),
                                                 new ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(mainPane.getWindow());
        if (selectedFile != null) {
            String inpath = selectedFile.getName();
            //System.out.println(inpath);
            Map newmap = new Map();
            newmap.setImgUrl(inpath);
            //display.setMap(newmap);
            mainPane.getMapRootPane().updateDisplay(display, "NEW");
            //mapView.setImage(new Image("/edu/wpi/off/by/one/errors/code/resources/" + inpath));
            //window.display(selectedFile);
        }
    }
    
    public void changeDisplay(){
    	String k = mapChoiceBox.getSelectionModel().getSelectedItem();
    	int index = mapChoiceBox.getItems().indexOf(mapChoiceBox.getSelectionModel().getSelectedItem());
		//System.out.println(index);
		this.selectedMap = this.mapList.get(index);
		setMap(this.selectedMap);
    }
    
    public void updateMapList(ArrayList<Map> maps){
    	this.mapList = maps;
    	mapChoiceBox.getItems().clear();
    	//System.out.println(maps.size());
    	for(Map m : maps){
        	String name = (m.getName() == null) ? m.getImgUrl() : m.getName();
        	mapChoiceBox.getItems().add(name);
        }
    }
    
    /*
    private void updateDisplayList(String old_s, String new_s){
    	Display disp = displayList.get(old_s);
		//disp.setMap(this.currentMap);
		displayList.put(new_s, disp);
		displayList.remove(old_s);
		int i = displayChoiceBox.getItems().indexOf(old_s);
		displayChoiceBox.getItems().set(i, new_s);
    }
    */
}
