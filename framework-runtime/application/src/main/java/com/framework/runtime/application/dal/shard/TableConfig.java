package com.framework.runtime.application.dal.shard;

public class TableConfig {
	private String table;
	private String shardColumn;
	private String strategy;
	private int shardCount;
	
	public TableConfig() {
	}
	
	
	
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getShardColumn() {
		return shardColumn;
	}
	public void setShardColumn(String shardColumn) {
		this.shardColumn = shardColumn;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public int getShardCount() {
		return shardCount;
	}

	public void setShardCount(int shardCount) {
		this.shardCount = shardCount;
	}
	
	
}
