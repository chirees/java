package com.framework.runtime.application.posp;

import java.io.ByteArrayOutputStream;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.LogListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.ByteTransportMessage;
import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.net.transport.CodecTransportAdapter;
import com.framework.runtime.application.net.transport.TransportException;
import com.framework.runtime.application.util.NumberUtil;

public class PospCodecTransportAdapter extends CodecTransportAdapter {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());
	
	private ISOPackager packager;
	
	public PospCodecTransportAdapter(String name) {
		super(name);
	}
	
	public PospCodecTransportAdapter() {
		
	}

	@Override
	protected TransportMessage codec(TransportMessage message) throws TransportException {
		try {
			if (message instanceof PospTransportMessage) {
				logger.info("[" + getName() + "] 编码 " + message);	
				
				ByteTransportMessage encoded = new ByteTransportMessage();
				PospTransportMessage pospMessage = (PospTransportMessage)message;
				pospMessage.getMessage().setPackager(packager);
				byte[] data = pospMessage.getMessage().pack();
				byte[] allData = new byte[11 + data.length];
				byte[] tpduBytes = NumberUtil.fromHex(pospMessage.getTpdu());
				byte[] headerBytes = NumberUtil.fromHex(pospMessage.getHeader());
				System.arraycopy(tpduBytes, 0, allData, 0, 5);
				System.arraycopy(headerBytes, 0, allData, 5, 6);
				System.arraycopy(data, 0, allData, 11, data.length);
				
				ISOMsg msg = pospMessage.getMessage();
				String ukey = msg.getMTI() + msg.getString(41) + msg.getString(42) + msg.getString("60.2") + msg.getString(11);
				
				encoded.setData(allData);
				encoded.setUkey(ukey);
				return encoded;
			} else if(message instanceof ByteTransportMessage) {
				
				logger.info("[" + getName() + "] 解码 " + message);
				
				ByteTransportMessage byteMessage = (ByteTransportMessage)message;
				
				byte[] data = byteMessage.getData();
				ISOMsg isoMsg = new ISOMsg();
				isoMsg.setPackager(packager);
				
				byte[] tpduBytes = new byte[5];
				System.arraycopy(data, 0, tpduBytes, 0, 5);
				
				byte[] headerBytes = new byte[6];
				System.arraycopy(data, 5, headerBytes, 0, 6);
				
				byte[] messageData = new byte[data.length - 11];
				System.arraycopy(data, 11, messageData, 0, messageData.length);
				isoMsg.unpack(messageData);
				
				PospTransportMessage msg = new PospTransportMessage(isoMsg);
				msg.setTpdu(NumberUtil.toHex(tpduBytes));
				msg.setHeader(NumberUtil.toHex(headerBytes));
				
				String ukey = isoMsg.getMTI() + isoMsg.getString(41) + isoMsg.getString(42) + isoMsg.getString("60.2") + isoMsg.getString(11);
				msg.setUkey(ukey);
				
				return msg;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new TransportException(e);
		}
	}

	@Override
	public void startup() throws TransportException {
		try {
			packager = new GenericPackager("jar:posp-config.xml");
			org.jpos.util.Logger log = new org.jpos.util.Logger();
			packager.setLogger(log, "message");

			LogListener logListener = new Log4JLogListener(new ByteArrayOutputStream());
			log.addListener(logListener);
		} catch (Exception e) {
			throw new TransportException(e);
		}
	}

}
