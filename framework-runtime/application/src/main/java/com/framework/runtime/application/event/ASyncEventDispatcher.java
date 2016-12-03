package com.framework.runtime.application.event;

public class ASyncEventDispatcher extends AbstractEventDispatcher {
	private EventCaller caller = new AsyncEventCaller();
	
	@Override
	public EventCaller getCaller() {
		return caller;
	}

}
