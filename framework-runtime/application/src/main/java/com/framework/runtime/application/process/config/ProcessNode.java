package com.framework.runtime.application.process.config;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
@Root
public class ProcessNode {
	@ElementList(entry="status", type=StatusNode.class, inline=true)
	private List<StatusNode> statuses;
	@Attribute
	private String code;
	@Attribute
	private String name;
	@Attribute
	private String group;
	
	@Attribute(required = false)
	private boolean async = true;
	
	public StatusNode getStartNode() {
		return statuses.get(0);
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<StatusNode> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<StatusNode> statuses) {
		this.statuses = statuses;
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


	public boolean isAsync() {
		return async;
	}


	public void setAsync(boolean async) {
		this.async = async;
	}
	
	
}
