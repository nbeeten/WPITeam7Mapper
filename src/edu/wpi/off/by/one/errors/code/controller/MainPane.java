package edu.wpi.off.by.one.errors.code.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import java.io.IOException;


/**
 * The controller that manages the logic for most of the application view:
 * 		- Map
 * 		- Menu
 *		- Directions
 * 
 */
public class MainPane extends BorderPane {
	
	@FXML ScrollPane mapScrollPane;
	
    public MainPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
            mapScrollPane.setHvalue(mapScrollPane.getHmin() + (mapScrollPane.getHmax() - mapScrollPane.getHmin()) / 2);
            mapScrollPane.setVvalue(mapScrollPane.getVmin() + (mapScrollPane.getVmax() - mapScrollPane.getVmin()) / 2);
            
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }
    }
    
        
}
