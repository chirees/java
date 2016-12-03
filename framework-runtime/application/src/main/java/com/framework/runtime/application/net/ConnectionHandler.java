package com.framework.runtime.application.net;

import com.framework.runtime.application.net.transport.TransportException;


public interface ConnectionHandler {

    void connected(NetConnection connection) throws TransportException;

    void disconnected(NetConnection connection) throws TransportException;

    void sent(NetConnection channel, byte[] message) throws TransportException;

    void received(NetConnection connection, byte[] message) throws TransportException;

    void caught(NetConnection connection, Throwable exception) throws TransportException;

}