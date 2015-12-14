package edu.wpi.off.by.one.errors.code.controller;

import java.io.IOException;

import edu.wpi.off.by.one.errors.code.controller.customcontrols.IconedLabel;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * Created by jules on 11/28/2015.
 */
public class NavigationPane extends GridPane {
    //region FXML attributes
	@FXML private Label InstructionLabel;
    //endregion

    //region Properties
    private BooleanProperty isExpandedProperty;
    //endregion

    //region attributes
    private double expandedHeight;

    private final Animation collapseAnimation = new Transition() {
        {
            setCycleDuration(Duration.millis(100));
        }
        protected void interpolate(double frac) {
            final double currentHeight = expandedHeight * (1.0 - frac);
            setPrefHeight(currentHeight);
            setTranslateY(expandedHeight + currentHeight);
        }
    };

    private final Animation expandAnimation = new Transition() {
        {
            setCycleDuration(Duration.millis(100));
        }
        protected void interpolate(double frac) {
            final double currentHeight = expandedHeight * frac;

            setPrefHeight(currentHeight);
            setTranslateY(expandedHeight - currentHeight);
        }
    };
    //endregion

    //region Constructor
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
        this.setPrefHeight(150);
        this.expandedHeight = this.getPrefHeight();
        this.isExpandedProperty = new SimpleBooleanProperty(true);
        setExpandAnimationOnFinishedProperty();
        setCollapseAnimationOnFinsishedProperty();
    }
    //endregion

    //region Getters, Setters
    private void setExpandAnimationOnFinishedProperty(){
        expandAnimation.onFinishedProperty().set(e -> {
            isExpandedProperty.set(true);
        });
    }

    private void setCollapseAnimationOnFinsishedProperty(){
        collapseAnimation.onFinishedProperty().set(e -> {
            isExpandedProperty.set(false);
        });
    }

    public BooleanProperty getIsExpandedProperty(){
        return this.isExpandedProperty;
    }
    //endregion

    //region Helper Methods
    public void expand(){
        expandAnimation.play();
    }
    //endregion

    //region Listener Methods
    @FXML private void onClosePaneButtonClick(){
        collapseAnimation.play();
    }
    //endregion

}
