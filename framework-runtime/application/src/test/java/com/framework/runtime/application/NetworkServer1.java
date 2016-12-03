package com.framework.runtime.application;

import com.framework.runtime.application.net.NetworkEngine;
import com.framework.runtime.application.net.Server;
import com.framework.runtime.application.net.Transport;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.netty.NettyServer;
import com.framework.runtime.application.net.transport.LoadbalanceTransport;
import com.framework.runtime.application.net.transport.SimpleTransportChain;

public class NetworkServer1 {

	public static void main(String[] args) {
//		NetworkEngine engine = new NetworkEngine();
//		TransportChain chain = new SimpleTransportChain();
//		Server server = new NettyServer("0.0.0.0", 8888);
//		chain.addTransport(server);
//		
//		Transport loadbalance = new LoadbalanceTransport();
//		chain.addTransport(loadbalance);
//		
//		engine.addTransportChain(chain);
//		
//		engine.startup();
	}
}
