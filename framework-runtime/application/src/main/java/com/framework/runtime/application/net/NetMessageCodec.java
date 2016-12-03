package com.framework.runtime.application.net;

public interface NetMessageCodec {

	byte[] encode(ByteTransportMessage message);
	
	ByteTransportMessage decode(byte[] data);
}
