package com.framework.runtime.application.xflow.config;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
@Root
public class XStatusNode {
    @ElementList(entry="event", type=XEventNode.class, inline=true, required=false)
	private List<XEventNode> events;
    
    @ElementList(entry="trigger", type=XTriggerNode.class, inline=true, required=false)
	private List<XTriggerNode> triggers;
    
	@Attribute
	private String code;
	@Attribute
	private String name;
	@Attribute(required=false)
	private boolean started;
	

	public List<XTriggerNode> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<XTriggerNode> triggers) {
		this.triggers = triggers;
	}

	public List<XEventNode> getEvents() {
		return events;
	}

	public void setEvents(List<XEventNode> events) {
		this.events = events;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	
}
