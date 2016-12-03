package com.framework.runtime.application.dal.shard;

import com.framework.runtime.application.dal.shard.sqlparser.SqlParser;

public class ShardStatement {
	private String tableName;
	private String shardTableName;
	private String shardColumn;
	private String shardStrategy;
	private int shardCount;
	
	private SqlParser sqlParser;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getShardTableName() {
		return shardTableName;
	}
	public void setShardTableName(String shardTableName) {
		this.shardTableName = shardTableName;
	}
	public String getShardColumn() {
		return shardColumn;
	}
	public void setShardColumn(String shardColumn) {
		this.shardColumn = shardColumn;
	}
	public SqlParser getSqlParser() {
		return sqlParser;
	}
	public void setSqlParser(SqlParser sqlParser) {
		this.sqlParser = sqlParser;
	}
	public String getShardStrategy() {
		return shardStrategy;
	}
	public void setShardStrategy(String shardStrategy) {
		this.shardStrategy = shardStrategy;
	}
	public int getShardCount() {
		return shardCount;
	}
	public void setShardCount(int shardCount) {
		this.shardCount = shardCount;
	}
	
	
	
	
}
