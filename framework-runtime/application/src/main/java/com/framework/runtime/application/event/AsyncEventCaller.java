package com.framework.runtime.application.event;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.framework.runtime.application.trace.TraceIdLocal;
import com.framework.runtime.application.util.TraceIdRunnable;

public class AsyncEventCaller implements EventCaller {

	private Queue<TraceIdRunnable> queue = new ConcurrentLinkedQueue<TraceIdRunnable>();
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	@Override
	public void call(final EventMessage event, final EventHandler handler) {
		final String requestTraceId = TraceIdLocal.getId();
		queue.offer(new TraceIdRunnable(requestTraceId, new Runnable() {

			@Override
			public void run() {
				handler.onEvent(event);
			}
			
		}));
		
		executor.execute(new Runnable() {

			@Override
			public void run() {
				while (!queue.isEmpty()) {
					TraceIdRunnable nextTask = queue.poll();
					TraceIdLocal.setId(nextTask.traceId);
	                nextTask.task.run();
	            }
			}
    		
    	});
	}

}
