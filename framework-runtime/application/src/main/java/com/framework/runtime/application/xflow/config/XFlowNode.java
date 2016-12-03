package com.framework.runtime.application.xflow.config;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
@Root
public class XFlowNode {
	@ElementList(entry="status", type=XStatusNode.class, inline=true)
	private List<XStatusNode> statuses;
	@Attribute
	private String code;
	@Attribute
	private String name;
	
	
	@Attribute(required = false)
	private boolean async = true;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<XStatusNode> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<XStatusNode> statuses) {
		this.statuses = statuses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean isAsync() {
		return async;
	}


	public void setAsync(boolean async) {
		this.async = async;
	}
	
	
}
