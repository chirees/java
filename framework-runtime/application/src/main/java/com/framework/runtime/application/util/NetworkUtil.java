package com.framework.runtime.application.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NetworkUtil {

	private static boolean isSpecialIp(String ip) {
		if (ip.contains(":"))
			return true;
		if (ip.startsWith("127."))
			return true;
		if (ip.startsWith("169.254."))
			return true;
		if (ip.equals("255.255.255.255"))
			return true;
		return false;
	}
	
	public static List<String> getLocalIps() throws Exception {
		Set<InetAddress> addrs = new HashSet<InetAddress>();
		Enumeration<NetworkInterface> e=NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
        	NetworkInterface net = e.nextElement();	
        	if(net.isUp() && !net.isVirtual()) {
        		
        		Enumeration<InetAddress> is = net.getInetAddresses();
                while (is.hasMoreElements()) {
                    InetAddress i = is.nextElement();
                    if (!i.isLoopbackAddress() && !i.isLinkLocalAddress() && !i.isMulticastAddress()
                        && !isSpecialIp(i.getHostAddress())) 
                    	addrs.add(i);
                }
        	}
        }
        
        List<String> ret = new ArrayList<String>();
        for (InetAddress addr : addrs) {
            ret.add(addr.getHostAddress());
        }
        
        return ret;
	}
	
	public static String getLocalIp(String start) {
		try {
			String[] starts = start.split(",");
			List<String> ips = getLocalIps();
			for(String ip:ips) {
				for(String s:starts) {
					if(ip.startsWith(s.trim())) {
						return ip;
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
}
