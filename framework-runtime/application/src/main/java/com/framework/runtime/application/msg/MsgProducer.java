package com.framework.runtime.application.msg;


public interface MsgProducer {
	
	boolean send(String topic, String key, String value) ;
	
	boolean sendOrder(String topic, String key, String value, final long orderId);
}
