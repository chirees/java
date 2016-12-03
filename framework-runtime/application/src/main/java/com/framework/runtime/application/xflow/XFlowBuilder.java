package com.framework.runtime.application.xflow;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import org.elasticsearch.common.lang3.StringUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.framework.runtime.application.xflow.config.XConfig;
import com.framework.runtime.application.xflow.config.XEventNode;
import com.framework.runtime.application.xflow.config.XFlowNode;
import com.framework.runtime.application.xflow.config.XStatusNode;
import com.framework.runtime.application.xflow.config.XTriggerNode;
import com.framework.runtime.application.xflow.config.XWhenNode;
import com.framework.runtime.application.xflow.node.XEvent;
import com.framework.runtime.application.xflow.node.XEventTrigger;
import com.framework.runtime.application.xflow.node.XFlow;
import com.framework.runtime.application.xflow.node.XStatus;
import com.framework.runtime.application.xflow.node.XTrigger;

public class XFlowBuilder {
	
	private static Map<String, XFlow> flows = new HashMap();
	
	private static Executor excuter = new XFlowExecutor();

	public static void build(String path) throws Exception {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resourcePatternResolver.getResources(path);
		Serializer serializer = new Persister();
		for (Resource resource : resources) {
			XConfig config = serializer.read(XConfig.class, resource.getInputStream());
			for(XFlowNode flowNode:config.getFlows()) {
				XFlow xflow = new XFlow();
				xflow.setCode(flowNode.getCode());
				xflow.setName(flowNode.getName());
				
				flows.put(xflow.getCode(), xflow);
				
				for(XStatusNode statusNode:flowNode.getStatuses()) {
					XStatus status = new XStatus();
					status.setCode(statusNode.getCode());
					status.setName(statusNode.getName());
					status.setStarted(statusNode.isStarted());
					xflow.putStatus(status.getCode(), status);
				}
				
				for(XStatusNode statusNode:flowNode.getStatuses()) {
					
					XStatus status = xflow.getStatus(statusNode.getCode());
					
					if(statusNode.getEvents() != null) {
						for(XEventNode eventNode:statusNode.getEvents()) {
							XEvent event = new XEvent();
							event.setCode(eventNode.getCode());
							event.setName(eventNode.getName());
							
							if(!StringUtils.isEmpty(eventNode.getTo()))
								event.setTo(xflow.getStatus(eventNode.getTo()));
							
							if(eventNode.getWhens() != null && ! eventNode.getWhens().isEmpty()) {
								for(XWhenNode when:eventNode.getWhens()) {
									event.put(when.getValue(), xflow.getStatus(when.getTo()));
								}
							}
							
							status.putEvent(event.getCode(), event);
						}
					}
					
					if(statusNode.getTriggers() != null) {
						for(XTriggerNode triggerNode:statusNode.getTriggers()) {
							
							if(triggerNode.getEvent() == null) {
								XTrigger trigger = new XTrigger();
								trigger.setCode(triggerNode.getCode());
								status.addTrigger(trigger);
							}
							else {
								XEventTrigger trigger = new XEventTrigger();
								trigger.setCode(triggerNode.getCode());
								trigger.setEvent(triggerNode.getEvent());
								status.addTrigger(trigger);
							}
							
							
						}
					}
				}
			}
		}
	}

	public static XFlowProcesser getProcesser(String code) {
		XFlow xflow = flows.get(code);
		XFlowProcesser xp = new XFlowProcesser(excuter, xflow);
		return xp;
	}
	

}
