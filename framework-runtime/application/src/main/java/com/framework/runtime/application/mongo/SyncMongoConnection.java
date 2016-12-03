package com.framework.runtime.application.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.BsonBoolean;
import org.bson.BsonDateTime;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

public class SyncMongoConnection {
	
	private String host;
	private MongoClient mongoClient;
	private Map<String, MongoDatabase> databases = new ConcurrentHashMap();
	
	public SyncMongoConnection() {
		
	}
	
	public SyncMongoConnection(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void connect() {
		MongoClientURI connectionString = new MongoClientURI(host);
		mongoClient = new MongoClient(connectionString);
	}
	
	public void update(String dbName, String collectionName, Object id, String[] columnNames, Object[] columnValues) {
		
		if(columnNames.length == 0)
			return;
		
		MongoDatabase database = getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		
		
		Document filter = new Document();
		filter.append("_id", id);
		Document updateColumn = createDocument(columnNames, columnValues);
		Document update = new Document();
		update.append("$set", updateColumn);
		collection.updateOne(filter, update);
	}
	
	private Document createDocument(String[] columnNames, Object[] columnValues) {
		Document doc = new Document();
		for(int i = 0; i < columnNames.length; i++) {
			if(columnNames[i].equalsIgnoreCase("id")) {
				columnNames[i] = "_id";
			}
			
			doc.append(columnNames[i], columnValues[i]);
		}
		
		return doc;
	}
	
	private BsonValue convert(String name, Object value) {
		BsonValue bv = null;
		if(value instanceof String) {
			bv = new BsonString((String)value);
		}
		else if(value instanceof Integer) {
			bv = new BsonInt32((Integer)value);
		}
		else if(value instanceof Long) {
			bv = new BsonInt64((Long)value);
		}
		else if(value instanceof Date) {
			bv = new BsonDateTime(((Date)value).getTime());
		}
		else if(value instanceof Boolean) {
			bv = new BsonBoolean(((Boolean)value));
		}
		else if(value instanceof Double) {
			bv = new BsonDouble(((Double)value));
		}
		return bv;
	}
	
	public void insert(String dbName, String collectionName, Object id, String[] columnNames, Object[] columnValues) {
		Document doc = createDocument(columnNames, columnValues);
		MongoDatabase database = getDatabase(dbName);
		Document filter = new Document();
		filter.append("_id", id);
		Document update = new Document();
		update.append("$set", doc);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		UpdateOptions option = new UpdateOptions();
		collection.updateOne(filter, update, option.upsert(true));
	}
	
	private MongoDatabase getDatabase(String dbName) {
		MongoDatabase database = databases.get(dbName);
		
		if(database == null) {
			database = mongoClient.getDatabase(dbName);
			databases.put(dbName, database);
		}
		return database;
	}
	
	public void insertBatch(String dbName, String collectionName, String[][] columnNames, Object[][] columnValues) {
		
		List<Document> docs = new ArrayList();
		for(int i = 0; i < columnNames.length; i++) {
			Document doc = new Document();
			for(int j = 0; j < columnNames[i].length; j ++) {
				if(columnNames[i][j].equalsIgnoreCase("id")) {
					columnNames[i][j] = "_id";
				}
				doc.append(columnNames[i][j], columnValues[i][j]);
			}
			docs.add(doc);
		}
		
		MongoDatabase database = getDatabase(dbName);
		
		MongoCollection<Document> collection = database.getCollection(collectionName);
		collection.insertMany(docs);
	}
	
	public long count(String dbName, String collectionName) {
		MongoDatabase database = getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection.count();
	}
}
