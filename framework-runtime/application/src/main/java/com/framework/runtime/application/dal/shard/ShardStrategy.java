package com.framework.runtime.application.dal.shard;

public interface ShardStrategy {

	
	String map(String table, Object shardColumnValue, int shardCount);
	
	String getName();
}
