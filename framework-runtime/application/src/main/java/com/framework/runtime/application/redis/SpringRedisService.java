package com.framework.runtime.application.redis;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.framework.runtime.application.LogU;
import com.framework.runtime.application.mvc.UserWrapper;
import com.github.knightliao.apollo.utils.data.GsonUtils;
import com.google.gson.Gson;

public class SpringRedisService implements RedisService {
	protected RedisTemplate<String, Serializable> redisTemplate = null;
	

	@Override
	public void set(String key, Serializable value, long expiredTime) {
		BoundValueOperations valueOper = redisTemplate.boundValueOps(key);
		
		if (expiredTime <= 0) {
            valueOper.set(value);
        } else {
            valueOper.set(value, expiredTime, TimeUnit.MILLISECONDS);
        }
	}
	

	@Override
	public void setJson(String key, Serializable value, long expiredTime) {
		Gson gson = new Gson();
		String gvalue = gson.toJson(value);
		set(key, gvalue, expiredTime);
	}  
	

	@Override
	public Serializable get(String key) {
		BoundValueOperations<String, Serializable> valueOper = redisTemplate.boundValueOps(key);
		return valueOper.get();
	}
	

	@Override
	public Serializable getJson(String key, Class clazz) {
		Gson gson = new Gson();
		String gvalue = (String)get(key);
		if(gvalue == null)
			return null;
		
		
		Serializable value = (Serializable)gson.fromJson(gvalue, clazz);
		return value;
	}

	
	public void del(String key) {
        if (redisTemplate.hasKey(key)) {
        	redisTemplate.delete(key);
        }
    }

	public RedisTemplate<String, Serializable> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void push(String channel, Serializable message) {
		redisTemplate.convertAndSend(channel, message);
	}

	
	public void lpush(String channel, Serializable message) {
		redisTemplate.opsForList().leftPush(channel, message);
	}
	
	public List<Serializable> range(String key, int start, int end) {  
        return redisTemplate.opsForList().range(key, start, end);  
    }
	
	public void remove(String key, long i, Serializable value) {  
		redisTemplate.opsForList().remove(key, i, value);  
    }


	@Override
	public UserWrapper getUserWrapper(String key, Class clazz) {
		UserWrapper uw= new UserWrapper();
		Object user = this.getJson(key, clazz);
		uw.setUser(uw);
		uw.setToken(key);
		return uw;
	}


	@Override
	public boolean lock(final String key, final long time) {
		
		return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
				Boolean nx = connection.setNX(key.getBytes(), "locked".getBytes());
				if(nx) {
					connection.expire(key.getBytes(), time);
				}
				
				return nx;
				
			}}, true);
	}


	@Override
	public void unlock(String key) {
		del(key);
	}


	@Override
	public void set(String key, Serializable value) {
		BoundValueOperations valueOper = redisTemplate.boundValueOps(key);
        valueOper.set(value);
	}



}
