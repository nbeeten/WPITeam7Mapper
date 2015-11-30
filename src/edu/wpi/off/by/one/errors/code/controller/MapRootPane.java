package edu.wpi.off.by.one.errors.code.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import edu.wpi.off.by.one.errors.code.application.EdgeDisplay;
import edu.wpi.off.by.one.errors.code.application.NodeDisplay;
import edu.wpi.off.by.one.errors.code.application.event.EditorEvent;
import edu.wpi.off.by.one.errors.code.application.event.SelectEvent;
import edu.wpi.off.by.one.errors.code.model.Coordinate;
import edu.wpi.off.by.one.errors.code.model.Display;
import edu.wpi.off.by.one.errors.code.model.Edge;
import edu.wpi.off.by.one.errors.code.model.FileIO;
import edu.wpi.off.by.one.errors.code.model.Graph;
import edu.wpi.off.by.one.errors.code.model.Id;
import edu.wpi.off.by.one.errors.code.model.Map;
import edu.wpi.off.by.one.errors.code.model.Node;
import edu.wpi.off.by.one.errors.code.model.Path;

/**
 * Created by jules on 11/28/2015.
 * Edited by Kelly on 11/30/2015.
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
