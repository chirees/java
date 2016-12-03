package com.framework.runtime.application.app;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.framework.runtime.application.exception.ApplicationException;

public class ZookeeperApplicationRegister implements ApplicationRegister {

	private static final String PATH = "/running_application_/";
	
	private CuratorFramework client;
	
	private String registerUrl;
	
	@Override
	public void login(String code, String name, String ip) throws ApplicationException {
		try {
			client.create().creatingParentsIfNeeded().forPath(PATH, (code + "-" + name).getBytes());
		} catch (Exception e) {
			throw new ApplicationException(code, name + "启动注册到zookeeper[" + registerUrl + "]错误", e);
		}
	}
	
	private CuratorFramework createClient() {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(registerUrl, retryPolicy);
		return client;
	}

	@Override
	public void start(String ip) {
		// TODO Auto-generated method stub
		
	}

}
