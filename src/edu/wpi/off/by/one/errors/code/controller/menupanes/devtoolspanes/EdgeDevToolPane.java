package edu.wpi.off.by.one.errors.code.controller.menupanes.devtoolspanes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;

import java.io.IOException;

import edu.wpi.off.by.one.errors.code.controller.ControllerSingleton;
import edu.wpi.off.by.one.errors.code.controller.MainPane;

/**
 * Created by jules on 11/30/2015.
 */
public class EdgeDevToolPane extends VBox {
	
	MainPane mainPane;
	@FXML CheckBox accessibleCheckbox;
	
    public EdgeDevToolPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/menupanes/devtoolspanes/EdgeDevToolPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }
    }
    
    public void setMainPane(MainPane m){ this.mainPane = m; } 
    
    @FXML public void connectEdgesAction(){
    	ControllerSingleton.getInstance().getMapRootPane().addEdgeDisplayFromQueue();
    }
    
    @FXML public void switchAccessible(){
    	if(accessibleCheckbox.isSelected()){
    		//swap accessible once edges are selectable
    	} else{
    		//swap accessible once edges are selectable
    	}
    }
}
