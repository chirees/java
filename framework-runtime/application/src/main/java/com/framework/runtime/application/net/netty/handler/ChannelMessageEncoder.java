package com.framework.runtime.application.net.netty.handler;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.util.NumberUtil;

public class ChannelMessageEncoder extends ChannelOutboundHandlerAdapter  {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
				
		if(msg instanceof byte[]) {
		
			byte[] bytes = (byte[])msg;
			byte[] data = new byte[2 + bytes.length];
			byte[] lenBytes = NumberUtil.low2Bytes(bytes.length);
			
			data[0] = lenBytes[0];
			data[1] = lenBytes[1];
			
			System.arraycopy(bytes, 0, data, 2, bytes.length);
			
			logger.info("发送数据：" + NumberUtil.toHex(data));
			
			ByteBuf sendBuf = Unpooled.copiedBuffer(data);
			ctx.write(sendBuf, promise);
		}
		else if(msg instanceof ByteBuf) {
			ByteBuf buf = (ByteBuf)msg;
			
			ctx.write(buf, promise);
		}
	}

}
