package com.framework.runtime.application.dal.shard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ShardConfigInit implements ApplicationListener<ContextRefreshedEvent> {

	private List<TableConfig> tables = new ArrayList();
	private List<ShardStrategy> strategys = new ArrayList();
	
	
	
	public List<TableConfig> getTables() {
		return tables;
	}



	public void setTables(List<TableConfig> tables) {
		this.tables = tables;
	}



	public List<ShardStrategy> getStrategys() {
		return strategys;
	}



	public void setStrategys(List<ShardStrategy> strategys) {
		this.strategys = strategys;
	}



	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		for(ShardStrategy strategy:this.strategys) {
			ShardConfig.getInstance().put(strategy.getName(), strategy);
		}
		
		for(TableConfig table:tables) {
			ShardConfig.getInstance().put(table.getTable(), table);
		}
	}

	
}
