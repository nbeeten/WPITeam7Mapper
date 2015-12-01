package edu.wpi.off.by.one.errors.code.controller;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller that manages the logic for most of the application view:
 * 		- Map
 * 		- Menu
 *		- Directions
 * 
 */
public class MainPane extends BorderPane {
    private BooleanProperty isDevModeOn;

    @FXML private Button openNavigationPaneButton;
    @FXML private NavigationPane navigationPane;

    public MainPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }

        addListeners();
    }

    private void addListeners(){
        openNavigationPaneButton.visibleProperty().bind(navigationPane.visibleProperty().not());

    }

    @FXML private void onOpenNavigationPaneButtonClick(){
        navigationPane.open();
    }
}

