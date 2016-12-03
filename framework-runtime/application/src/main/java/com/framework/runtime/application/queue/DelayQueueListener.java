package com.framework.runtime.application.queue;

public interface DelayQueueListener<V> {

	void timeExpired(String key, V v, MapDelayQueue<V> queue);
}
