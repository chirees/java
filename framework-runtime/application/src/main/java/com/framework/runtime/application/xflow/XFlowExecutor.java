package com.framework.runtime.application.xflow;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.framework.runtime.application.trace.TraceIdLocal;
import com.framework.runtime.application.util.TraceIdRunnable;

public class XFlowExecutor implements Executor {
	private static final String TAG = "AsyncQueue";
	
	private Queue<TraceIdRunnable> queue = new ConcurrentLinkedQueue<TraceIdRunnable>();
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	@Override
	public void execute(Runnable task) {
		final String requestTraceId = TraceIdLocal.getId();
		queue.offer(new TraceIdRunnable(requestTraceId, task));
		
		
		executor.execute(new Runnable() {

			@Override
			public void run() {
				
				
				while (!queue.isEmpty()) {
					TraceIdRunnable nextTask = queue.poll();
					if(nextTask != null) {
						TraceIdLocal.setId(nextTask.traceId);
		                nextTask.task.run();
					}
	            }
			}
    		
    	});
    }
	
	
	
}
