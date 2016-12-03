package com.framework.runtime.application.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleManager {
	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
	
	public static ScheduleManager instance;
	
	public static ScheduleManager getInstance() {
		if(instance == null) {
			instance = new ScheduleManager();
		}
		
		return instance;
	}
	
	public void schedule(Runnable runnable, int delay) {
		scheduledExecutorService.schedule(runnable, delay, TimeUnit.SECONDS);
	}
}
