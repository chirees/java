package com.framework.runtime.application.msg;

public interface MsgConsumer {

	void accept(String key, String value);
}
