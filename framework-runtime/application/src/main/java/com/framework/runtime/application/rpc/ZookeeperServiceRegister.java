package com.framework.runtime.application.rpc;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookeeperServiceRegister implements ServiceRegister {
	
	private static final String PATH = "/commonrpc/";
	
	private CuratorFramework client;
	
	private String registerUrl;

	@Override
	public void regist(String address, String serviceName) throws Exception {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(registerUrl, retryPolicy);
		client.create().creatingParentsIfNeeded().forPath(PATH, serviceName.getBytes());
	}

}
