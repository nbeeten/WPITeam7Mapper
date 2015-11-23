package edu.wpi.off.by.one.errors.code.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
public class MainViewControlller implements Initializable{
	//region fxmlControls
	@FXML
	private HBox rootMenuPane;

	@FXML
	private ToggleButton hamburgerToggleButton;

	@FXML
	private RadioButton searchRadioButton;

	@FXML
	private RadioButton directionsRadioButton;

	@FXML
	private RadioButton favoritesRadioButton;

	@FXML
	private RadioButton devModeRadioButton;

	@FXML
	private RadioButton settingsRadioButton;

	@FXML
	private RadioButton helpRadioButton;
	//endregion

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hamburgerToggleButton.selectedProperty().addListener((v, oldValue, newValue) -> openRootMenuPane(newValue));

	}

	/**
	 * Sets the width of the root menu pane to max width or minwitdh depending on a boolean
	 * True for opened false for closed
	 * @param b
     */
	public void openRootMenuPane(boolean b){
		if(b){
			rootMenuPane.setPrefWidth(rootMenuPane.getMaxWidth());
		}
		else{
			rootMenuPane.setPrefWidth(rootMenuPane.getMinWidth());
		}
	}

	public void showMenuDetailsPane(boolean b, AnchorPane paneToShow){
		if (b){
			paneToShow.setVisible(!b);
		}
		else {
			paneToShow.setVisible(b);
		}
	}

}
