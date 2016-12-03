package com.framework.runtime.application.net;

import java.net.InetSocketAddress;


public interface Endpoint extends Transport {
    
    void setTransportChain(TransportChain chain);
    
    void setNetMessageCodec(NetMessageCodec codec);
}