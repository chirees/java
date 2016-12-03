package com.framework.runtime.application.util;

public class TraceIdRunnable  {
	public String traceId;
	public Runnable task;
	
	public TraceIdRunnable(String traceId, Runnable task) {
		this.traceId = traceId;
		this.task = task;
	}
	
}