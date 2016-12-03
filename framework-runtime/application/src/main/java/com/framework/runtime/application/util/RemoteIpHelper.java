package com.framework.runtime.application.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class RemoteIpHelper {
	 private static final String UNKNOWN = "unknown";
	 
	    public static String getRemoteIpFrom(HttpServletRequest request) {
	        String ip = null;
	        int tryCount = 1;
	 
	        while (!isIpFound(ip) && tryCount <= 6) {
	            switch (tryCount) {
	                case 1:
	                    ip = request.getHeader(HttpHeader.X_FORWARDED_FOR.key());
	                    break;
	                case 2:
	                    ip = request.getHeader(HttpHeader.PROXY_CLIENT_IP.key());
	                    break;
	                case 3:
	                    ip = request.getHeader(HttpHeader.WL_PROXY_CLIENT_IP.key());
	                    break;
	                case 4:
	                    ip = request.getHeader(HttpHeader.HTTP_CLIENT_IP.key());
	                    break;
	                case 5:
	                    ip = request.getHeader(HttpHeader.HTTP_X_FORWARDED_FOR.key());
	                    break;
	                default:
	                    ip = request.getRemoteAddr();
	            }
	 
	            tryCount++;
	        }
	 
	        if(ip != null && ip.equals("0:0:0:0:0:0:0:1")) {
		    	ip = "127.0.0.1";
		    }
	        
	        return ip;
	    }
	    
	   
	    
	    public static List<String> getRemoteIpsFrom(HttpServletRequest request) {
	        List<String> ips = new ArrayList();
	        String ip = request.getHeader(HttpHeader.X_FORWARDED_FOR.key());
	        
	        if(isIpFound(ip)) {
	        	ips.add(ip);
	        }
	        
            ip = request.getHeader(HttpHeader.PROXY_CLIENT_IP.key());
            
             
            
            if(isIpFound(ip)) {
	        	ips.add(ip);
	        }
	        
            ip = request.getHeader(HttpHeader.WL_PROXY_CLIENT_IP.key());
            
            if(isIpFound(ip)) {
	        	ips.add(ip);
	        }
	        
            ip = request.getHeader(HttpHeader.HTTP_CLIENT_IP.key());
            
            if(isIpFound(ip)) {
	        	ips.add(ip);
	        }
	        
            ip = request.getHeader(HttpHeader.HTTP_X_FORWARDED_FOR.key());
            
            if(isIpFound(ip)) {
	        	ips.add(ip);
	        }
	        
            ip = request.getRemoteAddr();
            
            if(isIpFound(ip)) {
	        	ips.add(ip);
	        }
	        
	 
	        return ips;
	    }
	 
	    private static boolean isIpFound(String ip) {
	        return ip != null && ip.length() > 0 && !UNKNOWN.equalsIgnoreCase(ip);
	    }
}
