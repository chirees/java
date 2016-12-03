package com.framework.runtime.application.event;

public class SyncEventDispatcher extends AbstractEventDispatcher {

	private EventCaller caller = new SyncEventCaller();
	
	@Override
	public EventCaller getCaller() {
		return caller;
	}

}
