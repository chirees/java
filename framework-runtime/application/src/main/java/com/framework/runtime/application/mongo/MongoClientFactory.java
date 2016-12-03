package com.framework.runtime.application.mongo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoClientFactory implements FactoryBean<MongoClient>, InitializingBean {
	
	private String host;
	
	
	private MongoClient mongoClient;

	@Override
	public MongoClient getObject() throws Exception {
		return mongoClient;
	}

	@Override
	public Class<?> getObjectType() {
		return MongoClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		MongoClientURI connectionString = new MongoClientURI(host);
		mongoClient = new MongoClient(connectionString);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	
	
	

}
