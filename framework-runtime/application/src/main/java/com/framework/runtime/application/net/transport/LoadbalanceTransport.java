package com.framework.runtime.application.net.transport;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.Endpoint;
import com.framework.runtime.application.net.NetMessageCodec;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.net.loadbalance.AlgorithmManager;
import com.framework.runtime.application.net.loadbalance.LoadbalanceNode;
import com.framework.runtime.application.net.netty.NettyClient;

public class LoadbalanceTransport extends AbstractTransport implements Endpoint {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());
	
	private List<LoadbalanceNode> nodes = new ArrayList();
	
	private AlgorithmManager algorithmManager = new AlgorithmManager();
	
	private String nodeIps;
	
	private TransportChain chain;
	
	public LoadbalanceTransport(String name) {
		super(name);
	}
	
	public LoadbalanceTransport() {
		
	}
	
	
	

	public String getNodeIps() {
		return nodeIps;
	}

	public void setNodeIps(String nodeIps) {
		this.nodeIps = nodeIps;
	}

	@Override
	protected void handleMessage(TransportMessage message, TransportChain chain) throws TransportException {
		LoadbalanceNode node = algorithmManager.calculate(message.getUkey());
		if(node != null) {
			node.handle(message, chain);
		}
	}

	@Override
	protected void handleBackMessage(TransportMessage message, TransportChain chain) throws TransportException {
		
		this.chain.back(this, message);
	}

	@Override
	public void startup() throws TransportException {
		if(nodeIps != null) {
			String[] configNodes = nodeIps.split(",");
			for(int i = 0; i < configNodes.length; i++) {
				String[] ipAndPort = configNodes[i].split(":");
				LoadbalanceNode node = new NettyClient("L" + i, "L" + i, ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				node.setLoadbalanceTransport(this);
				node.startup();
				nodes.add(node);
			}
		}
		
	}

	@Override
	public void shutdown() throws TransportException {
		for(LoadbalanceNode node:this.nodes) {
			node.shutdown();
		}
	}
	
	public void nodeStatusChanged(LoadbalanceNode node, LoadbalanceNode.Status status) {
		if(status == LoadbalanceNode.Status.disable) {
			algorithmManager.remove(node);
		}
		else {
			algorithmManager.add(node);
		}
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

	@Override
	public void setTransportChain(TransportChain chain) {
		this.chain = chain;
	}

	@Override
	public void setNetMessageCodec(NetMessageCodec codec) {
		
	}



}
