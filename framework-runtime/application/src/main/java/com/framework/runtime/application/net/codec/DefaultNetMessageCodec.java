package com.framework.runtime.application.net.codec;

import java.util.Collection;

import com.framework.runtime.application.net.ByteTransportMessage;
import com.framework.runtime.application.net.NetMessageCodec;
import com.framework.runtime.application.net.transport.TransportId;
import com.framework.runtime.application.util.NumberUtil;

public class DefaultNetMessageCodec implements NetMessageCodec {
	

	@Override
	public byte[] encode(ByteTransportMessage message) {

		StringBuffer sb = new StringBuffer();
		Collection<TransportId> ids = message.allIds();
		for (TransportId id : ids) {
			if(sb.length() > 0)
				sb.append(',');
			
			sb.append(id.getId());
			if (id.getChildId() != null) {
				sb.append(":");
				sb.append(id.getChildId());
			}
		}
		
		byte[] idbytes = sb.toString().getBytes();
		
		byte[] data = new byte[2 + idbytes.length + message.getData().length];
		
		byte[] idlens = NumberUtil.low2Bytes(idbytes.length);
		data[0] = idlens[0];
		data[1] = idlens[1];
		
		System.arraycopy(idbytes, 0, data, 2, idbytes.length);
		System.arraycopy(message.getData(), 0, data, idbytes.length + 2, message.getData().length);
		
		return data;
	}

	@Override
	public ByteTransportMessage decode(byte[] data) {
		ByteTransportMessage message = new ByteTransportMessage();
		int length = ((data[0] << 8) & 0xff) | (data[1] & 0xff);
		byte[] idbytes = new byte[length];
		byte[] otherdata = new byte[data.length - 2 - length];
		
		System.arraycopy(data, 2, idbytes, 0, idbytes.length);
		System.arraycopy(data, 2 + length, otherdata, 0, otherdata.length);
		
		String ids = new String(idbytes);
		String[] idarr = ids.split(",");
		for(String id:idarr) {
			String[] ida = id.split(":");
			message.push(new TransportId(ida[0], ida.length > 1 ? ida[1] : null));
		}
		
		
		message.setData(otherdata);
		
		return message;
	}
	
	public static void main(String[] args) {
//		ByteTransportMessage message = new ByteTransportMessage();
//		message.setData("FFFF".getBytes());
//		message.push(new TransportId("A", "B"));
//		message.push(new TransportId("C", null));
//		byte[] data = new DefaultNetMessageCodec().encode(message);
		
		byte[] arr = NumberUtil.fromHex("009E4E657474795365727665723A30343861656536312D363037662D343938632D626536382D3431363539363837616132332C506F7370436F6465635472616E73706F7274416461707465722C506F73705472616E73616374696F6E5472616E73706F72742C506F7370436F6465635472616E73706F7274416461707465722C4C6F616462616C616E63655472616E73706F72742C4E65747479436C69656E74600007000060310031FFFF08000020000000C0001600005830303030303030343330333331303039303030303030310011000000230040002253657175656E6365204E6F3039303030304D33303030004030352038393836303034303134313238313033393439312020202020303039333430303033393433");
		
		new DefaultNetMessageCodec().decode(arr);
	}

}
