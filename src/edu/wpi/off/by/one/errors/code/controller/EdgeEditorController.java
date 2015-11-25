package edu.wpi.off.by.one.errors.code.controller;

import java.net.URL;
import java.util.ResourceBundle;

import edu.wpi.off.by.one.errors.code.application.event.EditorEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class EdgeEditorController implements Initializable {

	@FXML private Button drawEdgeFromSelectedButton;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("Edge Controller initiated");
		ControllerMediator.getInstance().registerEdgeEditorController(this);
	}
	
	@FXML
	private void onDrawEdgeFromSelected(){
		EditorEvent selectedEvent = new EditorEvent(EditorEvent.DRAW_EDGES);
		drawEdgeFromSelectedButton.fireEvent(selectedEvent);
	}

}
