package com.framework.runtime.application.process.config;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
@Root
public class StatusNode {
    @ElementList(entry="event", type=EventNode.class, inline=true, required=false)
	private List<EventNode> events;
    
    @ElementList(entry="trigger", type=TriggerNode.class, inline=true, required=false)
	private List<TriggerNode> triggers;
    
	@Attribute
	private String code;
	@Attribute
	private String name;
	@Attribute(required=false)
	private String trigger;
	@Attribute(required=false)
	private boolean started;
	

	public List<TriggerNode> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<TriggerNode> triggers) {
		this.triggers = triggers;
	}

	public List<EventNode> getEvents() {
		return events;
	}

	public void setEvents(List<EventNode> events) {
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

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	
}
