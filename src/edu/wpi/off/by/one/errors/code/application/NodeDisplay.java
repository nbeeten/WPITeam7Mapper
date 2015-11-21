package edu.wpi.off.by.one.errors.code.application;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.sg.prism.NGNode;

import edu.wpi.off.by.one.errors.code.*;
import edu.wpi.off.by.one.errors.code.application.event.SelectNode;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * GUI "overlay" of the node
 * Contains the node itself + events
 * @author Kelly
 *
 */
public class NodeDisplay extends Button{
	
	Display display;
	NodeDisplay self = this;
	Id node;
	public boolean isSelected = false;
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
	
	public void setNode(Id node) { this.node = node; }
	public Id getNode() { return this.node; }
	
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
			if(!isSelected){
				SelectNode selectNodeEvent = new SelectNode(SelectNode.NODE_SELECTED);
				self.fireEvent(selectNodeEvent);
			} else {
				SelectNode selectNodeEvent = new SelectNode(SelectNode.NODE_DESELECTED);
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
		this.addEventFilter(SelectNode.NODE_DESELECTED, event -> {
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
	
	
	@Override
	protected boolean impl_computeContains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds arg0, BaseTransform arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NGNode impl_createPeer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object impl_processMXNode(MXNodeAlgorithm arg0, MXNodeAlgorithmContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	private void setHandlers() {
		this.setOnMouseEntered(onMouseEnteredEventHandler);
		this.setOnMouseExited(onMouseExitedEventHandler);
		this.setOnMouseClicked(onMouseClickedEventHandler);
		this.onDeselectEventHandler();
	}
	
	private void setCss(){
		this.setStyle("-fx-background-color:blue;" + "-fx-background-radius: 5em;" + "-fx-min-width: 8px;"
				+ "-fx-min-height: 8px;" + "-fx-max-width: 8px;" + "-fx-max-height: 8px;");
	}
}
