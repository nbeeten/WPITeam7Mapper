package edu.wpi.off.by.one.errors.code.controller.menupanes.devtoolspanes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.wpi.off.by.one.errors.code.controller.ControllerSingleton;
import edu.wpi.off.by.one.errors.code.controller.MainPane;
import edu.wpi.off.by.one.errors.code.controller.MapRootPane;
import edu.wpi.off.by.one.errors.code.model.Coordinate;
import edu.wpi.off.by.one.errors.code.model.Display;
import edu.wpi.off.by.one.errors.code.model.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
	private ArrayList<Map> mapList = new ArrayList<Map>();
	private Map selectedMap;
	

    public MapDevToolPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/menupanes/devtoolspanes/MapDevToolPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException excpt) {
            throw new RuntimeException(excpt);
        }
        ControllerSingleton cs = ControllerSingleton.getInstance();
        setListeners();
        cs.registerMapDevToolPane(this);
    }
    
    private void setListeners(){  	
    	MapRootPane mapRoot = ControllerSingleton.getInstance().getMapRootPane();
    	this.mapChoiceBox.setOnAction(e -> {
    		//should be change map info
    		changeDisplay();
    	});
    	
    	this.mapChoiceBox.setOnContextMenuRequested(e->{
    		mapChoiceBox.getItems().clear();
    		this.mapList = mapRoot.getDisplay().getMaps(); 
        	for(Map m : mapRoot.getDisplay().getMaps()){
            	String name = (m.getName() == null) ? m.getImgUrl() : m.getName();
            	mapChoiceBox.getItems().add(name);
            }
    	});
    	this.nameTextField.setOnKeyPressed(e -> {
    		String s = nameTextField.getText();
    		s = s.trim();
    		currentMap.setName(s);
    		mapRoot.render();
    	});
    	
    	this.rotationTextField.setOnKeyPressed(e -> {
    		String s = rotationTextField.getText();
    		currentMap.setRotation(toFloat(s));
    		mapRoot.render();
    	});
    	
    	this.scaleTextField.setOnKeyPressed(e -> {
    		String s = scaleTextField.getText();
    		currentMap.setScale(toFloat(s));
    		mapRoot.render();
    	});
    	
    	this.xTextField.setOnKeyPressed(e -> {
    		String s = xTextField.getText();
    		Coordinate currentc = currentMap.getCenter();
    		currentMap.setCenter(new Coordinate(toFloat(s),
    				currentc.getY(), currentc.getZ()));
    		mapRoot.render();
    	});
    	
    	this.yTextField.setOnKeyPressed(e -> {
    		String s = yTextField.getText();
    		Coordinate currentc = currentMap.getCenter();
    		currentMap.setCenter(new Coordinate(currentc.getX(),
    				toFloat(s), currentc.getZ()));
    		mapRoot.render();
    	});
    	
    	this.zTextField.setOnKeyPressed(e -> {
    		String s = zTextField.getText();
    		Coordinate currentc = currentMap.getCenter();
    		currentMap.setCenter(new Coordinate(currentc.getX(),
    				currentc.getY(), toFloat(s)));
    		mapRoot.render();
    	});
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
		Display display = ControllerSingleton.getInstance().getMapRootPane().getDisplay();
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Map File");
        fileChooser.getExtensionFilters().addAll(
                                                 new ExtensionFilter("Image Files", "*.png"),
                                                 new ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(mainPane.getWindow());
        if (selectedFile != null) {
            String inpath = selectedFile.getName();
            Map newmap = new Map();
            newmap.setImgUrl(inpath);
            ControllerSingleton.getInstance().getMapRootPane().updateDisplay(display, "NEW");
        }
    }
    
    public void changeDisplay(){
    	int index = mapChoiceBox.getItems().indexOf(mapChoiceBox.getSelectionModel().getSelectedItem());
		this.selectedMap = this.mapList.get(index);
		setMap(this.selectedMap);
    }
    
    public void updateMapList(ArrayList<Map> maps){
    	if(!this.mapList.containsAll(maps)){
    		this.mapList = maps;
    		mapChoiceBox.getItems().clear();
        	for(Map m : maps){
            	String name = (m.getName() == null) ? m.getImgUrl() : m.getName();
            	mapChoiceBox.getItems().add(name);
            }
    	}
    }
    
    /**
     * Parses/cleans input string to only contain
     * numerical values
     * TODO program still flips out at certain characters
     * try-catching is the current backup to this
     * @param s input string
     * @return cleaned string
     */
    private float toFloat(String s){
    	String regexString = "/^(-)?((\\d+(\\.\\d*)?)|(\\.\\d+))$/";
       	Pattern regex = Pattern.compile(regexString);
    	Matcher parser = regex.matcher(s);
    	parser = regex.matcher(s);
    	if(parser.matches()) s = parser.group(0);
    	else s = "0";
    	if(s == "" || s == null || s.isEmpty()) s = "0";
    	float newNum;
		try {
			newNum = Float.parseFloat(s);
		} catch (NumberFormatException ex){
			newNum = 0;
		}
		return newNum;
    }
}
