package edu.wpi.off.by.one.errors.code.controller;

import java.io.IOException;

import edu.wpi.off.by.one.errors.code.controller.customcontrols.IconedLabel;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * Created by jules on 11/28/2015.
 */
public class NavigationPane extends GridPane {
	@FXML private IconedLabel InstructionLabel;

    public NavigationPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NavigationPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }
        ControllerSingleton.getInstance().registerNavigationPane(this);
        this.getStylesheets().add(getClass().getResource("../resources/stylesheets/NavigationPaneStyleSheet.css").toExternalForm());
    }

    public void open(){
        /*this.setPrefHeight(this.getMaxHeight());
        this.setVisible(true);*/

        final Animation hideSidebar = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }

            protected void interpolate(double frac) {
                final double curWidth = getPrefHeight() * frac;
                setPrefHeight(curWidth);
            }
        };

        hideSidebar.play();

    }

    public void close(){
        //this.setPrefHeight(this.getMinHeight());
        //this.setVisible(false);

        final Animation hideSidebar = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }

            protected void interpolate(double frac) {
                final double curWidth = getPrefHeight() * (1.0 - frac);
                setPrefHeight(curWidth);
            }
        };

        hideSidebar.play();
    }

    @FXML private void onClosePaneButtonClick(){
        close();
    }

}
