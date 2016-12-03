package com.framework.runtime.application.config;

/**
 * 
 * @author xiongliang
 *
 */
public class PropertyBean {
	private String env;
	private String key1;

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
		System.out.println("env = " + env);
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
		System.out.println("key1 = " + key1);
	}
	
	
	
	
	
}
