package com.framework.runtime.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.respcode.RespCodeable;
import com.framework.runtime.application.trace.TraceIdLocal;

public class LogU {
	private static Logger core = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());
	private static Logger error = LoggerFactory.getLogger(Application.getInstance().getErrorLogger());
	private static Logger runtime = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());
//	private static Logger cmd = LoggerFactory.getLogger(Application.getInstance().getCmdLogger());
//	private static Logger net = LoggerFactory.getLogger(Application.getInstance().getNetLogger());
	
	public static void e(String tag, String message, Throwable e) {
		String traceId = TraceIdLocal.getId();
		error.error(traceId + "-["  + tag + "]" + message, e);
	}
	
	public static void e(Object obj, String message, Throwable e) {
		e(obj.getClass().getSimpleName(), message, e);
	}
	
	public static void i(String tag, String message) {
		n(tag, "", message);
	}
	
	public static void i(Object obj, String where, String... message) {
		n(obj.getClass().getSimpleName(), where, message);
	}
	
	public static void i(Object obj, String where, Object... message) {
		n(obj.getClass().getSimpleName(), where, message);
	}
	
	public static void r(Object obj, String where, String... message) {
		r(obj.getClass().getSimpleName(), where, message);
	}
	
	public static void r(String tag, String message) {
		r(tag, "", message);
	}
	
	public static void r(String tag, RespCodeable rc) {
		r(tag, "", "S-" + rc.getSysCode(), ",L-" + rc.getLogicCode());
	}
	
	public static void r(Object obj, RespCodeable rc) {
		r(obj.getClass().getSimpleName(), "", "S-" + rc.getSysCode(), ",L-" + rc.getLogicCode());
	}
	
	
	public static void d(String tag, String message) {
		String traceId = TraceIdLocal.getId();
		core.debug(traceId + "-["  + tag + "]" + message);
	}
	
	public static void i(String tag, RespCodeable rc) {
		n(tag, "", "S-" + rc.getSysCode(), ",L-" + rc.getLogicCode());
	}
	
	public static void i(String tag, String where,  RespCodeable rc) {
		n(tag, where, "S-" + rc.getSysCode(), ",L-" + rc.getLogicCode());
	}
	
	public static void i(String tag, String where, String message) {
		n(tag, where, message);
	}
	
	public static void c(String tag, String where, String... message) {
		n(tag, where, message);
	}
	
	public static void n(Object obj, String where, String... message) {
		n(obj.getClass().getSimpleName(), where, message);
	}
	
	public  static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		Object i = null;
		sb.append(i);
		
		System.out.println(sb);
		
	}
	
	public static void n(String tag, String where, Object... message) {
		String traceId = TraceIdLocal.getId();
		StringBuffer sb = new StringBuffer();
		sb.append("tid:[");
		sb.append(traceId);
		sb.append("]-[");
		sb.append(tag);
		sb.append('-');
		sb.append(where);
		sb.append(']');
		sb.append("-------");
		
		if(message != null) {
			for(int i = 0; i < message.length; i++) {
				sb.append('[');
				sb.append(message[i]);
				sb.append(']');
			}
		}
		core.info(sb.toString());
	}
	
	public static void n(String tag, String where, String... message) {
		String traceId = TraceIdLocal.getId();
		StringBuffer sb = new StringBuffer();
		sb.append("tid:[");
		sb.append(traceId);
		sb.append("]-[");
		sb.append(tag);
		sb.append('-');
		sb.append(where);
		sb.append(']');
		sb.append("-------");
		
		if(message != null) {
			for(int i = 0; i < message.length; i++) {
				sb.append('[');
				sb.append(message[i]);
				sb.append(']');
			}
		}
		core.info(sb.toString());
	}
	
	public static void r(String tag, String where, String... message) {
		String traceId = TraceIdLocal.getId();
		StringBuffer sb = new StringBuffer();
		sb.append("tid:[");
		sb.append(traceId);
		sb.append("]-[");
		sb.append(tag);
		sb.append('-');
		sb.append(where);
		sb.append(']');
		sb.append("-------");
		
		if(message != null) {
			for(int i = 0; i < message.length; i++) {
				sb.append('[');
				sb.append(message[i]);
				sb.append(']');
			}
		}
		runtime.info(sb.toString());
	}
	
	public static void rn(Object obj, String where, String... message) {
		rn(obj.getClass().getSimpleName(), where, message);
	}
	
	public static void rn(String tag, String where, String... message) {
		String traceId = TraceIdLocal.getId();
		StringBuffer sb = new StringBuffer();
		sb.append("tid:[");
		sb.append(traceId);
		sb.append("]-[");
		sb.append(tag);
		sb.append('-');
		sb.append(where);
		sb.append(']');
		sb.append("-------\n");
		
		if(message != null) {
			for(int i = 0; i < message.length; i++) {
				sb.append('[');
				sb.append(message[i]);
				sb.append(']');
			}
		}
		runtime.info(sb.toString());
	}
	
}
