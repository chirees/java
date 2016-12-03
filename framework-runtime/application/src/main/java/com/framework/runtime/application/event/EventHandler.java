package com.framework.runtime.application.event;

public interface EventHandler {

	void onEvent(EventMessage event);
	
	void setEventDispatcher(EventDispatcher eventDispatcher);
	
}
