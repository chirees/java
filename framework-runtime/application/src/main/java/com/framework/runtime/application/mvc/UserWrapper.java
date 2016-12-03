package com.framework.runtime.application.mvc;

public class UserWrapper<T> implements java.io.Serializable{
	public static final String KEY = "USER_WRAPPER";
	
	private T user;
	private String token;
	private long time;
	
	public UserWrapper(T user) {
		this.user = user;
	}
	
	public UserWrapper(T user, String token, long time) {
		this.user = user;
		this.token = token;
		this.time = time;
	}
	
	public UserWrapper() {
		
	}
	
	public T getUser() {
		return user;
	}
	public void setUser(T user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
