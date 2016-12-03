package com.framework.runtime.application.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.ds.ef.StateEnum;
import au.com.ds.ef.Trigger;

public class Status implements StateEnum {
	
	private String code;
	private String name;
	private String group;
	private String trigger;
	private boolean started;
	
	private List<Trigger> triggers;
	
	private Map<String, Event> events = new HashMap();
	
	public void addTrigger(Trigger t) {
		if(triggers == null) {
			triggers = new ArrayList();
		}
		
		triggers.add(t);
	}
	
	public List<Trigger> triggers() {
		return this.triggers;
	}
	
	public void put(Event event) {
		events.put(event.getCode(), event);
	}
	
	public Collection<Event> events() {
		return events.values();
	}
	
	public int eventSize() {
		return events.size();
	}
	
	
	public Event get(String code) {
		return events.get(code);
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
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	@Override
	public String code() {
		return code;
	}
	@Override
	public String name() {
		return name;
	}


	public Event getEvent(String code) {
		Event event = this.events.get(code);
		return event;
	}
	
	public String toString() {
		return "Status-{" + code + ", " + name + ", " + group + "}";
	}

	public String trigger() {
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
