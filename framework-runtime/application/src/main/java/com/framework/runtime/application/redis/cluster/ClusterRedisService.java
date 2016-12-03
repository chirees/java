package com.framework.runtime.application.redis.cluster;

import java.io.Serializable;
import java.util.List;

import redis.clients.jedis.JedisCluster;

import com.framework.runtime.application.LogU;
import com.framework.runtime.application.mvc.UserWrapper;
import com.framework.runtime.application.redis.RedisService;
import com.framework.runtime.application.util.BeanCopyUtil;
import com.google.gson.Gson;

public class ClusterRedisService implements RedisService {
	
	private JedisCluster jedisCluster;
	
	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@Override
	public void set(String key, Serializable value, long expiredTime) {
//		LogU.n(this, "set", key, value.toString());
		String result = jedisCluster.set(key, value.toString());//, "NX|XX", "PX", expiredTime);
		Long time = jedisCluster.expire(key, (int)(expiredTime / 1000));
		
//		LogU.i(this, "set result", result + ", " + time);
	}

	@Override
	public void set(String key, Serializable value) {
		jedisCluster.set(key, value.toString());
	}

	@Override
	public synchronized void setJson(String key, Serializable value, long expiredTime) {
		Gson gson = new Gson();
		String gvalue = gson.toJson(value);
		set(key, gvalue, expiredTime);
	}

	@Override
	public Serializable get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public synchronized Serializable getJson(String key, Class clazz) {
		Gson gson = new Gson();
		String gvalue = (String)get(key);
//		LogU.i(this, "getJson", key + ", " + gvalue);
		if(gvalue == null)
			return null;
		
		
		Serializable value = (Serializable)gson.fromJson(gvalue, clazz);
		return value;
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
	public void del(String key) {
		if(jedisCluster.exists(key)){
			jedisCluster.del(key);
		}
	}

	@Override
	public void push(String channel, Serializable message) {
		jedisCluster.rpush(channel, message.toString());
	}

	@Override
	public void lpush(String channel, Serializable message) {
		jedisCluster.lpush(channel, message.toString());
	}

	@Override
	public List<Serializable> range(String key, int start, int end) {
		List<String> list = jedisCluster.lrange(key, (long)start, (long)end);
		List<Serializable> clist = BeanCopyUtil.copyTo(list, Serializable.class);
		return clist;
	}

	@Override
	public void remove(String key, long i, Serializable value) {
		jedisCluster.lrem(key, (long)i, value.toString());
	}

	@Override
	public boolean lock(String key, long time) {
		// TODO Auto-generated method stub
		jedisCluster.setnx(key, "locked");
		jedisCluster.expire(key, (int)time);
		return true;
	}

	@Override
	public void unlock(String key) {
		if(jedisCluster.exists(key)){
			jedisCluster.del(key);
		}
	}

}
