package com.framework.runtime.application.xflow.config;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
@Root
public class XEventNode {
	@Attribute
	private String code;
	@Attribute
	private String name;
	@Attribute(required=false)
	private String to;
	
	@ElementList(entry="when", type=XWhenNode.class, inline=true, required=false)
	private List<XWhenNode> whens;
	
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
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public List<XWhenNode> getWhens() {
		return whens;
	}
	public void setWhens(List<XWhenNode> whens) {
		this.whens = whens;
	}
	
	
	
}
