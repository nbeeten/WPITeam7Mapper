package edu.wpi.off.by.one.errors.code.controller.menupanes;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

import java.io.IOException;

/**
 * Created by jules on 11/28/2015.
 */
public class SearchMenuPane extends BorderPane {
    public SearchMenuPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/menupanes/SearchMenuPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }
    }
}
