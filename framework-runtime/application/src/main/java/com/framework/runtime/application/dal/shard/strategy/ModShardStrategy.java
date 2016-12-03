package com.framework.runtime.application.dal.shard.strategy;

import com.framework.runtime.application.LogU;
import com.framework.runtime.application.dal.shard.ShardStrategy;


public class ModShardStrategy implements ShardStrategy {
	

	@Override
	public String map(String table, Object value, int shardCount) {
		LogU.r(this, "map", table + ", " + value);
		
		Number numberValue = getColumnValueAsNumber(value);
		long mod = numberValue.longValue() % shardCount;
		return table + "_" + mod;
	}
	
	public static void main(String[] argc) {
		System.out.println(863808 % 3);
	}
	
	public Number getColumnValueAsNumber(Object columnValue) {
        if (columnValue == null)
            return 0;
        if (columnValue instanceof String) {
        	if(((String) columnValue).length() > 6) {
        		columnValue = ((String) columnValue).substring(((String) columnValue).length() - 6);
        	}
            return Integer.parseInt((String)columnValue);
        }
        if (columnValue instanceof Number) {
            return (Number) columnValue;
        }
        
        return 0;
    }

	@Override
	public String getName() {
		return "mod";
	}

}
