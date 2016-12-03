package com.framework.runtime.application.queue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.framework.runtime.application.LogU;
import com.framework.runtime.application.util.SecrectUtil;

public class MapDelayQueue<V> {
	private Map<String, V> cacheObjMap = new ConcurrentHashMap<String, V>();
	private DelayQueue<DelayItem<String>> delayQueue = new DelayQueue();
	private ExecutorService executor = Executors.newSingleThreadExecutor();


	public MapDelayQueue() {

	}

	public void check(final DelayQueueListener<V> delayQueueListener) {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						// 从延迟队列中取值,如果没有对象过期则队列一直等待，
						DelayItem<String> item = delayQueue.take();
						if (item != null) {
							V v = cacheObjMap.remove(item.getItem());
							delayQueueListener.timeExpired(item.getItem(), v, MapDelayQueue.this);
							
						}
					} catch (Exception e) {
						LogU.e(this, "", e);
						break;
					}
				}
			}

		});
	}

	public V get(String key) {
		return cacheObjMap.get(key);
	}

	public V remove(String key) {
		V v = cacheObjMap.remove(key);
		delayQueue.remove(new DelayItem(key, 0));
		return v;
	}

	public void put(String key, V value, long time) {
		V oldValue = cacheObjMap.put(key, value);
		if (oldValue != null)
			delayQueue.remove(new DelayItem(key, 0));

		long nanoTime = TimeUnit.NANOSECONDS.convert(time, TimeUnit.MILLISECONDS);
		delayQueue.put(new DelayItem(key, nanoTime));
	}

	public static void main(String[] args) throws Exception {
		MapDelayQueue<String> queue = new MapDelayQueue();
		queue.check(new DelayQueueListener<String>() {

			@Override
			public void timeExpired(String key, String v, MapDelayQueue<String> queue) {
				System.out.println(key + ", " + v);
				queue.remove(String.valueOf(Integer.parseInt(key) + 2));
			}
			
		});
		
		for(int i = 0; i < 100; i++)
		queue.put("1111" + i, "vvvvvvvvvvvv " + i, 1000 * i);
	}

}
