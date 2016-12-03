package com.framework.runtime.application.mongo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.mongodb.MongoClient;

public class MorphiaDataStoreFactoryBean
		implements FactoryBean<Datastore>, InitializingBean, PersistenceExceptionTranslator, DisposableBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(MorphiaDataStoreFactoryBean.class);

	
	private Datastore ds;
	private String dbName;
	private MongoClient mongoClient;
	
	public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
		return new DataAccessException("Exception occured", ex) {
		};
	}
	
	

	public MongoClient getMongoClient() {
		return mongoClient;
	}



	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}



	@Override
	public void afterPropertiesSet() throws Exception {
		ds = new Morphia().createDatastore(mongoClient, dbName);
		LOGGER.debug("create datastore {}", ds);
	}

	@Override
	public Datastore getObject() throws Exception {
		return ds;
	}

	@Override
	public Class<?> getObjectType() {
		return Datastore.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public void destroy() throws Exception {
	}
}
