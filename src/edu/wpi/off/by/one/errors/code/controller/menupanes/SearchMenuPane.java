package edu.wpi.off.by.one.errors.code.controller.menupanes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.wpi.off.by.one.errors.code.controller.MainPane;
import edu.wpi.off.by.one.errors.code.controller.customcontrols.AutoCompleteTextField;
import edu.wpi.off.by.one.errors.code.model.Coordinate;
import edu.wpi.off.by.one.errors.code.model.Map;

/**
 * Created by jules on 11/28/2015.
 */
public class SearchMenuPane extends BorderPane {
	
	MainPane mainPane;
	@FXML AutoCompleteTextField searchField;
	@FXML ChoiceBox<Integer> floorChoiceBox;
	@FXML ComboBox<String> buildingChoiceBox;
	

	
    public SearchMenuPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/menupanes/SearchMenuPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
            setListeners();
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }
        this.getStylesheets().add(getClass().getResource("../../resources/stylesheets/menupanes/SearchPaneStyleSheet.css").toExternalForm());
        floorChoiceBox.getItems().addAll(0, 1, 2, 3);
        
        SortedSet<String> entries = new TreeSet<String>();
    }

	public void setMainPane(MainPane mainPane) {
		this.mainPane = mainPane;
		for(Map m : mainPane.getMapRootPane().getDisplay().getMaps()){
        	String name = (m.getName() == null) ? m.getImgUrl() : m.getName();
        	buildingChoiceBox.getItems().add(name);
        }
	}
	
	public void updateMapList(ArrayList<Map> maps){
		buildingChoiceBox.getItems().clear();
		for(Map m : mainPane.getMapRootPane().getDisplay().getMaps()){
        	String name = (m.getName() == null) ? m.getImgUrl() : m.getName();
        	buildingChoiceBox.getItems().add(name);
        }
	}
	
	private void setListeners(){
		
		this.floorChoiceBox.setOnAction(e->{
			int floor = floorChoiceBox.getSelectionModel().getSelectedItem();
			System.out.println(floor);
			//do a thing with it
			mainPane.getMapRootPane().translate.setAll(mainPane.getMapRootPane().translate.getX(), mainPane.getMapRootPane().translate.getY(), floor);
			mainPane.getMapRootPane().render();
		});
		
		this.buildingChoiceBox.setOnContextMenuRequested(e -> {
			//TODO update map list
		});
		
		this.buildingChoiceBox.setOnAction(e -> {
			int index = buildingChoiceBox.getItems().indexOf(buildingChoiceBox.getSelectionModel().getSelectedItem());
			System.out.println(index);
			Map m = mainPane.getMapRootPane().getDisplay().getMaps().get(index);
			if(m == null) return;

			mainPane.dropStartC = mainPane.getMapRootPane().translate;
			mainPane.dropStartR = mainPane.getMapRootPane().rot;
			mainPane.dropStartS = mainPane.getMapRootPane().zoom;
			
			//float zx = (float) (m.getCenter().getX() + m.getImage().getWidth() * 0.5f * m.getScale());
			//float zy = (float) (m.getCenter().getY() + m.getImage().getHeight() * 0.5f * m.getScale());
			mainPane.dropEndC = m.getCenter();
			mainPane.dropEndR = -m.getRotation();
			float mx = (float)(mainPane.getMapRootPane().canvas.getWidth()/(m.getImage().getWidth() * m.getScale()) );
			float my = (float)(mainPane.getMapRootPane().canvas.getHeight()/(m.getImage().getHeight() * m.getScale()));
			mainPane.dropEndS = mx < my ? mx : my;
			if(mainPane.dropEndS <= 0.05f) mainPane.dropEndS = 1.0f;
			mainPane.dropzoom.play();
			mainPane.getMapRootPane().render();
			buildingChoiceBox.getSelectionModel().clearSelection();
		});
	}
}
