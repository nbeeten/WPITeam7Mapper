package edu.wpi.off.by.one.errors.code.controller.menucontrollers.menupanecontrollers;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.scene.layout.*;

/**
 * Created by Jules on 11/22/2015.
 */
public class SearchLocationMenuPane extends AnchorPane{


    public SearchLocationMenuPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/customcontrols/menupanes/SearchLocationPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
        }catch (IOException excpt){
            throw new RuntimeException(excpt);
        }
    }
}
