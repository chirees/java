
package com.framework.runtime.application.net;

import java.net.SocketAddress;


public interface NetConnection  {

	SocketAddress getRemoteAddress();
	
	SocketAddress getLocalAddress();

    boolean isConnected();
        
    void send(byte[] message) throws RemotingException;
    
    String getId();
    
    void close();
    
  

}