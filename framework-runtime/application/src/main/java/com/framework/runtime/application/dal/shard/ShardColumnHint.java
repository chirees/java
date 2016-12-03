package com.framework.runtime.application.dal.shard;

public class ShardColumnHint {
	private static final ThreadLocal columnHintLocal = new ThreadLocal();  
	
	
	public static void setValue(String traceId) {
		columnHintLocal.set(traceId);
	}
	
	public static String getValue() {
		return (String)columnHintLocal.get();
	}
}
