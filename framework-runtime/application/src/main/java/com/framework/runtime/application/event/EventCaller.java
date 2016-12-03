package com.framework.runtime.application.event;

public interface EventCaller {

	void call(EventMessage event, EventHandler handler);
	
}
