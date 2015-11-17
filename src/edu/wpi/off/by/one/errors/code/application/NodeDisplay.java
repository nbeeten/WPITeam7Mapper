package edu.wpi.off.by.one.errors.code.application;

import edu.wpi.off.by.one.errors.code.*;
import edu.wpi.off.by.one.errors.code.application.event.SelectNode;
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
	
	NodeDisplay self = this;
	Node node;
	
	public NodeDisplay(Number x, Number y, Number z){
		Node node = new Node(
				new Coordinate(x.floatValue(), y.floatValue(), z.floatValue()), -1);
		setCss();
		setHandlers();
	}
	
	public void setNode(Node node) { this.node = node; }
	public Node getNode() { return this.node; }
	
	/* Event handling */
	
	private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
		
		public void handle(MouseEvent e){
			SelectNode selectNodeEvent = new SelectNode();
			self.fireEvent(selectNodeEvent);
		}
	};
	
	private EventHandler<MouseEvent> onMouseEnteredEventHandler = new EventHandler<MouseEvent>() {
		
		public void handle(MouseEvent e){
			String style = self.getStyle();
			self.setStyle(style + "-fx-background-color: yellow;"
					+ "-fx-border-radius: 5em;" + "-fx-border-color:black;"
					+ "-fx-border-width: 1px;" + "-fx-border-style: solid;");
		}
	};
	
	private EventHandler<MouseEvent> onMouseExitedEventHandler = new EventHandler<MouseEvent>() {
		
		public void handle(MouseEvent e){
			String style = self.getStyle();
			self.setStyle(style + "-fx-background-color: blue;"
					+ "-fx-border-radius: none;" + "-fx-border-color: none;"
					+ "-fx-border-width: none;" + "-fx-border-style: none;");
		}
	};
	
	private void setHandlers() {
		this.setOnMouseEntered(onMouseEnteredEventHandler);
		this.setOnMouseExited(onMouseExitedEventHandler);
		this.setOnMouseClicked(onMouseClickedEventHandler);
	}
	
	private void setCss(){
		this.setStyle("-fx-background-color:blue;" + "-fx-background-radius: 5em;" + "-fx-min-width: 8px;"
				+ "-fx-min-height: 8px;" + "-fx-max-width: 8px;" + "-fx-max-height: 8px;");
	}
}
