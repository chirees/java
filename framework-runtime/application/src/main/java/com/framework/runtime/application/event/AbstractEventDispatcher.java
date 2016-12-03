package com.framework.runtime.application.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.framework.runtime.application.LogU;

public abstract class AbstractEventDispatcher implements EventDispatcher {
	
	private static final String TAG = "EVENT-P";
	
	private Map<String, EventHandler> handlers = new ConcurrentHashMap();
			
	@Override
	public void registerHandler(String eventKey, EventHandler handler) {
		handlers.put(eventKey, handler);
		handler.setEventDispatcher(this);
	}
	
	public void fireEvent(EventMessage event) {
		LogU.n(TAG, "fireEvent", event.getKey());
		EventHandler handler = handlers.get(event.getKey());
		getCaller().call(event, handler);
	}
	
	public abstract EventCaller getCaller();

}
