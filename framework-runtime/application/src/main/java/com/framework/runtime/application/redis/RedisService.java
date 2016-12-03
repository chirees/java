package com.framework.runtime.application.redis;

import java.io.Serializable;
import java.util.List;

import com.framework.runtime.application.mvc.UserWrapper;

public interface RedisService {
	
	void set(String key, Serializable value, long expiredTime);
	
	void set(String key, Serializable value);
	
	void setJson(String key, Serializable value, long expiredTime);
	
	Serializable get(String key);
	
	Serializable getJson(String key, Class clazz);
	
	UserWrapper getUserWrapper(String key, Class clazz);
	
	void del(String key);
	
	void push(String channel, Serializable message);
	
	void lpush(String channel, Serializable message);
	
	List<Serializable> range(String key, int start, int end) ;

	void remove(String key, long i, Serializable value) ;
	
	boolean lock(String key, long time);
	
	void unlock(String key);
}
