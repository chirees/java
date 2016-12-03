package com.framework.runtime.application.event;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.framework.runtime.application.LogU;

public class SyncEventCaller implements EventCaller {
	private Queue<Runnable> queue = new ConcurrentLinkedQueue<Runnable>();

	@Override
	public void call(final EventMessage event, final EventHandler handler) {
		queue.add(new Runnable() {

			@Override
			public void run() {
				LogU.n("SyncEvent", "run", event.getKey());
				handler.onEvent(event);
			}
			
		});

        while (!queue.isEmpty()) {
            Runnable nextTask = queue.poll();
            nextTask.run();
        }
	}

}
