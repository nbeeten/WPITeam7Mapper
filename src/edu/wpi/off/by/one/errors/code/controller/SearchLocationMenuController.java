package edu.wpi.off.by.one.errors.code.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.*;

import javax.imageio.IIOException;

/**
 * Created by Jules on 11/22/2015.
 */
public class SearchLocationMenuController extends AnchorPane{


    public SearchLocationMenuController(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SearchLocationMenuView.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
        }catch (IOException excpt){
            throw new RuntimeException(excpt);
        }
    }
}
