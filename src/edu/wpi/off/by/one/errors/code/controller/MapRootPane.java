package edu.wpi.off.by.one.errors.code.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by jules on 11/28/2015.
 */
public class MapRootPane extends AnchorPane{
    public MapRootPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MapRootPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException excpt) {
            throw new RuntimeException(excpt);
        }
    }
}
