package com.framework.runtime.application.dal.shard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.ContextRefreshedEvent;

public class ShardConfig  {

	private static ShardConfig instance;
	
	private Map<String, TableConfig> tables = new HashMap();
	
	private Map<String, ShardStrategy> strategys = new HashMap();
	
	private ShardConfig() {
		
	}
	


	public static ShardConfig getInstance() {
		if(instance == null) {
			instance = new ShardConfig();
		}
		
		return instance;
	}
	
	public void put(String tableName, TableConfig table) {
		tables.put(tableName, table);
	}
	
	public TableConfig get(String tableName) {
		return tables.get(tableName);
	}
	
	public ShardStrategy getStrategy(String key) {
		return strategys.get(key);
	}
	
	public void put(String key, ShardStrategy strategy) {
		strategys.put(key, strategy);
	}

	
}
