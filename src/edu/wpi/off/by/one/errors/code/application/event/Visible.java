package edu.wpi.off.by.one.errors.code.application.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class Visible extends Event{

	public static final EventType<Visible> VISIBLE = new EventType<>(ANY, "SELECTED");
	public static final EventType<Visible> NODE_SHOW = new EventType<>(ANY, "NODE_SHOW");
	public static final EventType<Visible> NODE_HIDE = new EventType<>(ANY, "NODE_HIDE");
	public static final EventType<Visible> EDGE_SHOW = new EventType<>(ANY, "EDGE_SHOW");
	public static final EventType<Visible> EDGE_HIDE = new EventType<>(ANY, "EDGE_HIDE");
	
	public Visible() { this(VISIBLE); }

	public Visible(EventType<Visible> selected) { super(selected); }

	public Visible(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
        super(arg0, arg1, arg2);
    }
	
}
