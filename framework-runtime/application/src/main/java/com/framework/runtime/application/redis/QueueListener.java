package com.framework.runtime.application.redis;

public interface QueueListener {

	void onMessage(String topic, Object message);
}
