package com.framework.runtime.application.net.codec;

import com.framework.runtime.application.net.ByteTransportMessage;
import com.framework.runtime.application.net.NetMessageCodec;

public class SimpleNetMessageCodec implements NetMessageCodec {

	@Override
	public byte[] encode(ByteTransportMessage message) {
		return message.getData();
	}

	@Override
	public ByteTransportMessage decode(byte[] data) {
		
		ByteTransportMessage message = new ByteTransportMessage();
		message.setData(data);
		
		return message;
	}

}
