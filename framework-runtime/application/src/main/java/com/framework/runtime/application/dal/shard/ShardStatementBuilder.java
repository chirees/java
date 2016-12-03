package com.framework.runtime.application.dal.shard;

import com.framework.runtime.application.dal.shard.sqlparser.DruidSqlParser;
import com.framework.runtime.application.dal.shard.sqlparser.SqlParser;



public class ShardStatementBuilder {

	public static ShardStatement build(String sql) throws Exception {
		SqlParser sqlParser = new DruidSqlParser();
		sqlParser.init(sql);
		ShardStatement st = new ShardStatement();
		TableConfig tc = ShardConfig.getInstance().get(sqlParser.getTableName());
		
		if(tc == null)
			return null;
		
		st.setShardColumn(tc.getShardColumn());
		st.setTableName(tc.getTable());
		st.setSqlParser(sqlParser);
		st.setShardCount(tc.getShardCount());
		st.setShardStrategy(tc.getStrategy());
		return st;
	}
}
