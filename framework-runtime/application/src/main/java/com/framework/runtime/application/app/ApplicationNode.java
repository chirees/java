package com.framework.runtime.application.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApplicationNode  implements java.io.Serializable {
	private String name;
	private String code;
	private int id;
	private int count;
	private boolean alive;
	private int aliveCount;
	
	private Map<String, InstanceNode> instanceNodes = new HashMap();
	
	public void check(String code, String ip, long time) {
		long curTime = System.currentTimeMillis();
		aliveCount = 0;
		boolean isApp = code.equals(this.code);
		for(InstanceNode node:instanceNodes.values()) {
			if(isApp && ip.equals(node.getIp())) {
				node.setLoginTime(System.currentTimeMillis());
				if(!node.isAlive()) {
					node.setStartTime(new Date());
				}
				node.setAlive(true);
			} else if(curTime - node.getLoginTime() > time) {
				node.setAlive(false);
				node.setStopTime(new Date());
			}
			
			if(node.isAlive()) {
				aliveCount = aliveCount + 1;
			}

			if(aliveCount > 0) {
				alive = true;
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getAliveCount() {
		return aliveCount;
	}

	public void setAliveCount(int aliveCount) {
		this.aliveCount = aliveCount;
	}

	public Map<String, InstanceNode> getInstanceNodes() {
		return instanceNodes;
	}

	public void setInstanceNodes(Map<String, InstanceNode> instanceNodes) {
		this.instanceNodes = instanceNodes;
	}

	public void putInstanceNode(InstanceNode node) {
		instanceNodes.put(node.getIp(), node);
		count = count + 1;
		node.setId(count);
	}
	
	public InstanceNode getInstanceNode(String key) {
		return instanceNodes.get(key);
	}
	
}
