package com.framework.runtime.application.process;

import java.util.HashMap;
import java.util.Map;

public class ProcesserPool {
	private Map<String, Processer> processes = new HashMap();
	
	private TriggerEventHandler eventHandler;
	
	private EventValidator eventValidator;
	
	public void put(Processer processer) {
		processes.put(processer.getCode(), processer);
	}
	
	
	public Processer get(String code) {
		return processes.get(code);
	}
	
	public void dispatch(TriggerEvent event) {
		if(eventHandler != null) {
			eventHandler.handle(event);
		}
	}


	public void setEventHandler(TriggerEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}


	public void setEventValidator(EventValidator eventValidator) {
		this.eventValidator = eventValidator;
	}


	public EventValidator getEventValidator() {
		return eventValidator;
	}
	
	
}
