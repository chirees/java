package com.framework.runtime.application.app;

import org.springframework.context.ApplicationEvent;

public class AppStartedEvent extends ApplicationEvent {

	public AppStartedEvent(Object source) {
		super(source);
	}

}
