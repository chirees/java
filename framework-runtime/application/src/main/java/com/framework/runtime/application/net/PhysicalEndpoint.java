package com.framework.runtime.application.net;

import java.net.InetSocketAddress;

public interface PhysicalEndpoint extends Endpoint {

	InetSocketAddress getLocalAddress();

	void send(String connectionId, byte[] message) throws RemotingException;

	void send(byte[] message) throws RemotingException;

	String getIp();

	int getPort();

	void setHeartbeatTime(int seconds);

	int getHeartbeatTime();

	int getIdleCloseTime();

	void setIdleCloseTime(int idleCloseTime);
	
	void setService(String service);
	
	String getService();
}
