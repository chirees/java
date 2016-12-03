package com.framework.runtime.application.app;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.redis.RedisService;
import com.framework.runtime.application.util.NetworkUtil;


public class ApplicationStarter implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
	private String network;
	
	private RedisApplicationRegister applicationRegister;
	
	private String ip;
	
	
	private RedisService redisService;
	
	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		applicationRegister = new RedisApplicationRegister();
		applicationRegister.setRedisService(redisService);
		ip = NetworkUtil.getLocalIp(network);
		int port = event.getEmbeddedServletContainer().getPort();
		Application.getInstance().setIp(ip);
		Application.getInstance().setPort(port);
		ip = ip + ":" + port;
		applicationRegister.start(ip);
		
		event.getApplicationContext().publishEvent(new AppStartedEvent(ip));
		
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}


	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	
	
	

}
