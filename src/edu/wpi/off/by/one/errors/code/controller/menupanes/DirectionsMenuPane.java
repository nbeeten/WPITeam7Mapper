package edu.wpi.off.by.one.errors.code.controller.menupanes;

import edu.wpi.off.by.one.errors.code.controller.customcontrols.ClearableTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

import java.io.IOException;

import edu.wpi.off.by.one.errors.code.controller.ControllerSingleton;
import edu.wpi.off.by.one.errors.code.controller.MainPane;

/**
 * Created by jules on 11/28/2015.
 */
public class DirectionsMenuPane extends BorderPane {
	@FXML private ClearableTextField originTextField;
    @FXML private ClearableTextField destinationTextField;
	@FXML private Button routeButton;
    @FXML private ListView directionsListView;
	
    public DirectionsMenuPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/menupanes/DirectionsMenuPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
            setListeners();
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }

        this.getStylesheets().add(getClass().getResource("../../resources/stylesheets/menupanes/DirectionsPaneStyleSheet.css").toExternalForm());
    }

	private void setListeners(){
		this.routeButton.setOnAction(e -> {
			ControllerSingleton.getInstance().getMapRootPane().drawPath();
		});
	}

    public ListView getdirectionsListView(){
        return this.directionsListView;
    }

    @FXML private void onSwitchDirectionsButtonClick(){
        String originContent = originTextField.getText();
        originTextField.setText(destinationTextField.getText());
        destinationTextField.setText(originContent);
    }
}
