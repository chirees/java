package com.framework.runtime.application.process;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.framework.runtime.application.LogU;
import com.framework.runtime.application.respcode.RespCodeable;

import au.com.ds.ef.EasyFlow;
import au.com.ds.ef.StateChangeListener;
import au.com.ds.ef.StatefulContext;
import au.com.ds.ef.call.FlowMonitor;
import au.com.ds.ef.err.LogicViolationError;


public class Processer {
	public static final String TAG = "Process";
	
	private EasyFlow flow;
	private Map<String, Status> statuses = new HashMap();
	private String code;
	private String name;
	private String group;
	private Status start;
	
	private boolean async;
	
	
	public void execute(String statusCode, String eventCode, StateChangeListener listener, Object data, RespCodeable resp)  throws Exception {
		LogU.i(TAG, "execute:" + code + ", " + name + ", " + group + ", " + statusCode + ", " + eventCode);
		StatefulContext context = new StatefulContext();
		Status status =  statuses.get(statusCode);
		context.setState(status);
		context.setStateChangeListener(listener);
		context.setData(data);
		context.setResponse(resp);
		Event event = status.getEvent(eventCode);
		try {
			flow.trigger(event, context);
		} catch (LogicViolationError e) {
			LogU.e(TAG, "执行流程错误：[" + code + ",  " + name + ", " + statusCode + ", " + eventCode, e);
			throw new Exception("执行流程状态错误：[" + code + ",  " + name + ", " + statusCode + ", " + eventCode, e);
		}
	}
	
	public void start(StateChangeListener listener, Object data, RespCodeable resp) {
		LogU.i(TAG, "start:" + code + ", " + name + ", " + group);
		StatefulContext context = new StatefulContext();
		context.setStateChangeListener(listener);
		context.setData(data);
		context.setResponse(resp);
		flow.trace().start(context);
	}
	
	public void start(FlowMonitor monitor, Object data, RespCodeable resp) {
		LogU.i(TAG, "start:" + code + ", " + name + ", " + group);
		StatefulContext context = new StatefulContext();
		context.setFlowMonitor(monitor);
		context.setData(data);
		context.setResponse(resp);
		flow.trace().start(context);
	}
	
	public void execute(String statusCode, String eventCode, FlowMonitor monitor, Object data, RespCodeable resp)  throws Exception {
		LogU.i(TAG, "execute:" + code + ", " + name + ", " + group + ", " + statusCode + ", " + eventCode);
		
		StatefulContext context = new StatefulContext();
		Status status =  statuses.get(statusCode);
		context.setState(status);
		context.setFlowMonitor(monitor);
		context.setData(data);
		context.setResponse(resp);
		
		flow.trace().start(false, context);
		
		Event event = status.getEvent(eventCode);
		try {
			flow.trigger(event, context);
		} catch (LogicViolationError e) {
			LogU.e(TAG, "执行流程错误：[" + code + ",  " + name + ", " + statusCode + ", " + eventCode, e);
			throw new Exception("执行流程状态错误：[" + code + ",  " + name + ", " + statusCode + ", " + eventCode, e);
		}
	}

	public void put(Status status) {
		statuses.put(status.getCode(), status);
	}
	
	public Status get(String code) {
		return statuses.get(code);
	}
	
	public Collection<Status> statuses() {
		return statuses.values();
	}

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	

	public Status getStart() {
		return start;
	}

	public void setStart(Status start) {
		this.start = start;
		
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

	public EasyFlow getFlow() {
		return flow;
	}

	public void setFlow(EasyFlow flow) {
		this.flow = flow;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}
	
	
	
}
