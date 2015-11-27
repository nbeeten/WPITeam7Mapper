package edu.wpi.off.by.one.errors.code.controller.menucontrollers.menupanecontrollers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

import java.io.IOException;

/**
 * Created by jules on 11/26/2015.
 */
public class SettingsMenuPane extends AnchorPane{

    public SettingsMenuPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/customcontrols/menupanes/SettingsPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        }
        catch (IOException excpt){
            throw new RuntimeException(excpt);
        }
    }


}
