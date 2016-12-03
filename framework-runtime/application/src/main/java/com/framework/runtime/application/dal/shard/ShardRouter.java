package com.framework.runtime.application.dal.shard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.runtime.application.dal.shard.sqlparser.ColumnValue;


public class ShardRouter {
	
	private Map<String, ShardStatement> shardStatements = new HashMap();
	
	

	public String[] route(String sqlId, String sql, List args) throws Exception {
		ShardStatement ss = ShardStatementBuilder.build(sql);
//		if(ss == null) {
//			ss = ShardStatementBuilder.build(sql);
//			shardStatements.put(sqlId, ss);
//		}
		
		if(ss == null)
			return null;
		
		ShardStrategy strategy = ShardConfig.getInstance().getStrategy(ss.getShardStrategy());
		String[] shardSqls = null;
		Object shardColumnValue = findColumnValue(ss.getShardColumn(), ss, args);
		
		if(shardColumnValue == null) {
			shardColumnValue = ShardColumnHint.getValue();
		}
		
		if (shardColumnValue != null) {
			if (shardColumnValue instanceof List) {
				shardSqls = new String[((List)shardColumnValue).size()];
				int i = 0;
				for(Object value:(List)shardColumnValue) {
					String shardTable = strategy.map(ss.getTableName(), value, ss.getShardCount());
					ss.getSqlParser().setTableName(shardTable);
					shardSqls[i++] = ss.getSqlParser().toSql();
				}
			}
			else {
				shardSqls = new String[1];
				String shardTable = strategy.map(ss.getTableName(), shardColumnValue, ss.getShardCount());
				ss.getSqlParser().setTableName(shardTable);
				shardSqls[0] = ss.getSqlParser().toSql();
				
			}
		}
		
		return shardSqls;
			
	}
	
	public static Object findColumnValue(String column, ShardStatement ss, List args) {
        List<ColumnValue> columnValues = ss.getSqlParser().getColumns();
        for (int i = 0; i < columnValues.size(); i++) {
            ColumnValue columnValue = columnValues.get(i);
            if (columnValue.column.equals(column)) {
                
                int start = countStart(columnValues, 0, i);
                
                if (columnValue.placeHolderCount == 1)
                    return args.get(start);
                return args.subList(start, start + columnValue.placeHolderCount);
            }
        }
        return null;
    }
    
    private static int countStart(List<ColumnValue> columnValues, int start, int end) {
    	int result = 0;
    	for(int i = start; i < end; i++) {
    		result += columnValues.get(i).placeHolderCount;
    	}
    	return result;
    }
}
