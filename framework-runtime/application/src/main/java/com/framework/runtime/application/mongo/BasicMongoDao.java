package com.framework.runtime.application.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.DAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.runtime.application.util.ReflectionUtil;
import com.mongodb.DBCollection;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public abstract class BasicMongoDao<T, K> implements DAO<T, K> {
	
	
	protected Class<T> entityClazz;
	@Autowired
	protected Datastore dataStore;

	public BasicMongoDao()  {
		initEntityClazz() ;
	}
	
	protected void initEntityClazz() throws RuntimeException {
		try {
			if(entityClazz == null) {
				entityClazz = ReflectionUtil.getParameterClass(this);
			}
			
			if(entityClazz == null) {
				throw new RuntimeException();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void main(String[] args) throws Exception{
	}

	

	public Datastore getDataStore() {
		return dataStore;
	}

	public void setDataStore(Datastore dataStore) {
		this.dataStore = dataStore;
	}

	@Override
	public long count() {
		return dataStore.getCount(entityClazz);
	}

	@Override
	public long count(final String key, final Object value) {
		return count(dataStore.find(entityClazz, key, value));
	}

	@Override
	public long count(final Query<T> query) {
		return dataStore.getCount(query);
	}

	@Override
	public Query<T> createQuery() {
		return dataStore.createQuery(entityClazz);
	}

	@Override
	public UpdateOperations<T> createUpdateOperations() {
		return dataStore.createUpdateOperations(entityClazz);
	}

	@Override
	public WriteResult delete(final T entity) {
		return dataStore.delete(entity);
	}

	@Override
	public WriteResult delete(final T entity, final WriteConcern wc) {
		return dataStore.delete(entity, wc);
	}

	@Override
	public WriteResult deleteById(final K id) {
		return dataStore.delete(entityClazz, id);
	}

	@Override
	public WriteResult deleteByQuery(final Query<T> query) {
		return dataStore.delete(query);
	}

	@Override
	public void ensureIndexes() {
		dataStore.ensureIndexes(entityClazz);
	}

	@Override
	public boolean exists(final String key, final Object value) {
		return exists(dataStore.find(entityClazz, key, value));
	}

	@Override
	public boolean exists(final Query<T> query) {
		return dataStore.getCount(query) > 0;
	}

	@Override
	public QueryResults<T> find() {
		return createQuery();
	}

	@Override
	public QueryResults<T> find(final Query<T> query) {
		return query;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<K> findIds() {
		return (List<K>) keysToIds(dataStore.find(entityClazz).asKeyList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<K> findIds(final String key, final Object value) {
		return (List<K>) keysToIds(dataStore.find(entityClazz, key, value).asKeyList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<K> findIds(final Query<T> query) {
		return (List<K>) keysToIds(query.asKeyList());
	}

	@Override
	public T findOne(final String key, final Object value) {
		return dataStore.find(entityClazz, key, value).get();
	}

	@Override
	public T findOne(final Query<T> query) {
		return query.get();
	}

	@Override
	public Key<T> findOneId() {
		return findOneId(dataStore.find(entityClazz));
	}

	@Override
	public Key<T> findOneId(final String key, final Object value) {
		return findOneId(dataStore.find(entityClazz, key, value));
	}

	@Override
	public Key<T> findOneId(final Query<T> query) {
		Iterator<Key<T>> keys = query.fetchKeys().iterator();
		return keys.hasNext() ? keys.next() : null;
	}

	@Override
	public T get(final K id) {
		return dataStore.get(entityClazz, id);
	}

	@Override
	public DBCollection getCollection() {
		return dataStore.getCollection(entityClazz);
	}

	@Override
	public Datastore getDatastore() {
		return dataStore;
	}

	@Override
	public Class<T> getEntityClass() {
		return entityClazz;
	}

	@Override
	public Key<T> save(final T entity) {
		return dataStore.save(entity);
	}

	@Override
	public Key<T> save(final T entity, final WriteConcern wc) {
		return dataStore.save(entity, wc);
	}

	@Override
	public UpdateResults update(final Query<T> query, final UpdateOperations<T> ops) {
		return dataStore.update(query, ops);
	}

	@Override
	public UpdateResults updateFirst(final Query<T> query, final UpdateOperations<T> ops) {
		return dataStore.updateFirst(query, ops);
	}

	public Class<T> getEntityClazz() {
		return entityClazz;
	}

	protected List<?> keysToIds(final List<Key<T>> keys) {
		final List<Object> ids = new ArrayList<Object>(keys.size() * 2);
		for (final Key<T> key : keys) {
			ids.add(key.getId());
		}
		return ids;
	}

}
