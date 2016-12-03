package com.framework.runtime.application.xflow.node;

public class XStartEvent extends XEvent {
	
	public XStartEvent(XStatus startStatus) {
		this.setCode("__EVT_START__");
		this.setCode("StartEvent");
		this.setTo(startStatus);
	}

}
