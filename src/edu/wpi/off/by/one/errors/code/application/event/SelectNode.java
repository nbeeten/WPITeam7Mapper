package edu.wpi.off.by.one.errors.code.application.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class SelectNode extends Event{

	public static final EventType<SelectNode> NODE_SELECTED = new EventType<>(ANY, "NODE_SELECTED");
	
	public SelectNode() { this(NODE_SELECTED); }

	public SelectNode(EventType<SelectNode> nodeSelected) { super(nodeSelected); }

	public SelectNode(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
        super(arg0, arg1, arg2);
    } 
	
}

