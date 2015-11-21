package edu.wpi.off.by.one.errors.code.application.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class Select extends Event{

	public static final EventType<Select> SELECTED = new EventType<>(ANY, "SELECTED");
	public static final EventType<Select> NODE_SELECTED = new EventType<>(ANY, "NODE_SELECTED");
	public static final EventType<Select> NODE_DESELECTED = new EventType<>(ANY, "NODE_DESELECTED");
	public static final EventType<Select> EDGE_SELECTED = new EventType<>(ANY, "EDGE_SELECTED");
	public static final EventType<Select> EDGE_DESELECTED = new EventType<>(ANY, "EDGE_DESELECTED");
	
	public Select() { this(SELECTED); }

	public Select(EventType<Select> selected) { super(selected); }

	public Select(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
        super(arg0, arg1, arg2);
    } 
	
}

