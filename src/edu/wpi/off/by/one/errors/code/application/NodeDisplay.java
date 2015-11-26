package edu.wpi.off.by.one.errors.code.application;

import java.util.ArrayList;

import edu.wpi.off.by.one.errors.code.application.event.SelectEvent;
import edu.wpi.off.by.one.errors.code.model.Coordinate;
import edu.wpi.off.by.one.errors.code.model.Display;
import edu.wpi.off.by.one.errors.code.model.Id;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * GUI "overlay" of the node
 * Contains the node itself + events
 *
 */
public class NodeDisplay extends Button implements IDisplayItem{
	
	Display display;
	NodeDisplay self = this;
	Id node;
	Coordinate nodeCoords;
	int edgeNum;
	ArrayList<String> tags;
	int size = 8; // in px format
	String color = Color.BLUE.toString().substring(2); 
	public boolean isSelected = false;
	
	BooleanProperty show = new SimpleBooleanProperty(this, "show", true);
	
	//
	//BooleanProperty clicked = new SimpleBooleanProperty();
	//private int id;
	
	/*
	public final boolean getClicked(){
		return clicked.get();
	}//end method getClicked
	
	public BooleanProperty clickedProperty(){
		return clicked;
	}//end method clickedProperty
	
	public final void setClicked(boolean val){
		clicked.set(val);
	}//end setClicked method
	
	public NodeDisplay(int iD){  ///I add this for completeness sake
		setClicked(false);
		id = iD;
	}
	*/
	public NodeDisplay(Display display, Id node){
		this.display = display;
		this.node = node;
		setCss();
		setHandlers();
	}
	
	public NodeDisplay(Display display, Number x, Number y, Number z){
		this.display = display;
		this.node = display.getGraph().addNodeRint(
				new Coordinate(x.floatValue(), y.floatValue(), z.floatValue()));

		setCss();
		setHandlers();
	}
	
	public BooleanProperty showProperty() { return show; }
	public void setNode(Id node) { this.node = node; }
	public Id getNode() { return this.node; }
	
	public NodeDisplay getItemInfo(){ return self; }
	@Override
	public boolean updateItemInfo(IDisplayItem newNodeDisplay){
		//TODO
		return false;
	}
	
	public void selectNode() {
		this.isSelected = true;
		String style = self.getStyle();
		self.setStyle(style + "-fx-background-color: purple;"
				+ "-fx-border-radius: none;" + "-fx-border-color: none;"
				+ "-fx-border-width: none;" + "-fx-border-style: none;");
	}
	
	/* Event handling */
	
	private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
		
		public void handle(MouseEvent e){
			
			//If double-click and selected on a building node
			if(e.isStillSincePress()){
				//TODO zoom and rotate onto location
				
			}
			
			if(!isSelected){
				SelectEvent selectNodeEvent = new SelectEvent(SelectEvent.NODE_SELECTED);
				self.fireEvent(selectNodeEvent);
			} else {
				SelectEvent selectNodeEvent = new SelectEvent(SelectEvent.NODE_DESELECTED);
				self.fireEvent(selectNodeEvent);
			}
			
		}
	};
	
	private EventHandler<MouseEvent> onMouseEnteredEventHandler = new EventHandler<MouseEvent>() {
		
		public void handle(MouseEvent e){
			if(!isSelected) {
				String style = self.getStyle();
				self.setStyle(style + "-fx-background-color: yellow;"
						+ "-fx-border-radius: 5em;" + "-fx-border-color:black;"
						+ "-fx-border-width: 1px;" + "-fx-border-style: solid;");
			}
		}
	};
	
	private EventHandler<MouseEvent> onMouseExitedEventHandler = new EventHandler<MouseEvent>() {
		
		public void handle(MouseEvent e){
			if(!isSelected){
				String style = self.getStyle();
				self.setStyle(style + "-fx-background-color: blue;"
						+ "-fx-border-radius: none;" + "-fx-border-color: none;"
						+ "-fx-border-width: none;" + "-fx-border-style: none;");
			}
		}
	};
	
	private void onDeselectEventHandler(){
		this.addEventFilter(SelectEvent.NODE_DESELECTED, event -> {
			String style = self.getStyle();
			self.setStyle(style + "-fx-background-color: blue;"
					+ "-fx-border-radius: none;" + "-fx-border-color: none;"
					+ "-fx-border-width: none;" + "-fx-border-style: none;");
			this.isSelected = false;
		});
	}
	
	/*
	//HOW TO IMPLEMENT THE WRAPPED CODE WITHOUT PUTTING THE WHOLE THING IN A METHOD
	public void attachEventHandler(Button btn){

		this.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
			@Override
			public void handle(MouseEvent e){
				btn.setDisable(false);
			}
		});
	} 
	
	*/
	/*
	EventHandler nodeClicked = new EventHandler(){
		@Override
		public void handle(Event e){
			clicked.set(true);
		}//end of method handle
		
	};
	*/
	
	private void setHandlers() {
		this.setOnMouseEntered(onMouseEnteredEventHandler);
		this.setOnMouseExited(onMouseExitedEventHandler);
		this.setOnMouseClicked(onMouseClickedEventHandler);
		this.onDeselectEventHandler();
		this.showProperty().addListener((v, oldVal, newVal) -> {
			if(newVal){
				this.setVisible(false);
				this.setMouseTransparent(true); //TODO: figure out a better way to handle this
			} else {
				this.setVisible(true);
				this.setMouseTransparent(false);
			}
		});
	}
	
	private void setCss(){
		this.setStyle("-fx-background-color:#" + this.color + ";" 
				+ "-fx-background-radius: 5em;" 
				+ "-fx-min-width: " + this.size + "px;"
				+ "-fx-min-height: " + this.size + "px;" 
				+ "-fx-max-width: " + this.size + "px;" 
				+ "-fx-max-height: " + this.size + "px;");
	}
}
