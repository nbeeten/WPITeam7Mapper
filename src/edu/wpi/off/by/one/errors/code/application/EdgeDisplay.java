package edu.wpi.off.by.one.errors.code.application;

import edu.wpi.off.by.one.errors.code.application.event.Select;
import edu.wpi.off.by.one.errors.code.model.Coordinate;
import edu.wpi.off.by.one.errors.code.model.Display;
import edu.wpi.off.by.one.errors.code.model.Id;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class EdgeDisplay extends Line{
	Display display;
	EdgeDisplay self = this;
	Id edge;
	public boolean isSelected = false;
	
	public EdgeDisplay(Display display, Id edge){
		this.display = display;
		this.edge = edge;
		setCss();
		setHandlers();
	}
	
	public EdgeDisplay(Display display, Id nodeA, Id nodeB){
		this.display = display;
		this.edge = display.getGraph().addEdgeRint(nodeA, nodeB);

		setCss();
		setHandlers();
	}
	
	public void setEdge(Id edge) { this.edge = edge; }
	public Id getEdge() { return this.edge; }
	
	public void selectEdge() {
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
				Select selectNodeEvent = new Select(Select.EDGE_SELECTED);
				self.fireEvent(selectNodeEvent);
			} else {
				Select selectNodeEvent = new Select(Select.EDGE_DESELECTED);
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
		this.addEventFilter(Select.EDGE_DESELECTED, event -> {
			String style = self.getStyle();
			self.setStyle(style + "-fx-background-color: blue;"
					+ "-fx-border-radius: none;" + "-fx-border-color: none;"
					+ "-fx-border-width: none;" + "-fx-border-style: none;");
			this.isSelected = false;
		});
	}

	
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
