package com.framework.runtime.application.net;


public interface Client extends Endpoint, NetConnection {

    /**
     * reconnect.
     */
    void reconnect() throws RemotingException;
    
  
    
}