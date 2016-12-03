package com.framework.runtime.application.event;

public interface EventDispatcher {

	void registerHandler(String eventKey, EventHandler handler);
	
	void fireEvent(EventMessage event);
}
