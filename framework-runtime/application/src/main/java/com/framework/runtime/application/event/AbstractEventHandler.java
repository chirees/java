package com.framework.runtime.application.event;

public abstract class AbstractEventHandler implements EventHandler {

	private EventDispatcher eventDispatcher;
	
	protected void fireEvent(EventMessage event) {
		eventDispatcher.fireEvent(event);
	}
	
	protected void registerThis(EventMessage event) {
		eventDispatcher.registerHandler(event.getKey(), this);
	}
	
	protected void registerThis(String eventKey) {
		eventDispatcher.registerHandler(eventKey, this);
	}

	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}
	

}
