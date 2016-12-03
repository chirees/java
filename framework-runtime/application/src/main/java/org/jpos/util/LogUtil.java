package org.jpos.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;

/**
 * 
 * @author xiongliang
 * 
 */
public class LogUtil {
	private static Logger bizlog = LoggerFactory
			.getLogger(Application.getInstance().getCoreLogger());
	private static ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private static PrintStream ps = new PrintStream(baos);

	public synchronized static void bizinfo(String where, String connectionId,
			Throwable cause) {
		cause.printStackTrace(ps);
		bizlog.info("[" + where + ", " + connectionId + "] "
				+ new String(baos.toByteArray()));
		baos.reset();
	}

	public synchronized static void bizerror(String where, Throwable cause) {
		cause.printStackTrace(ps);
		bizlog.info("[" + where + "] " + new String(baos.toByteArray()));
		baos.reset();
	}

	public static void bizinfo(String where, String message) {
		bizlog.info("[" + where + "] " + message);
	}

	public static void bizinfo(String where, String connectionId, String message) {
		bizlog.info("[" + where + ", " + connectionId + "] " + message);
	}

	public static void bizdebug(String where, String connectionId,
			String message) {
		bizlog.debug("[" + where + ", " + connectionId + "] " + message);
	}

	public static void bizerror(String where, String connectionId,
			String message) {
		bizlog.error("[" + where + ", " + connectionId + "] " + message);
	}

	public static void bizerror(String where, String message) {
		bizlog.error("[" + where + "] " + message);
	}

	public static void bizinfo(String where, String connectionId,
			String localAddress, String remoateAddress, String message) {
		bizlog.info("[" + where + ", " + connectionId + ", L:" + localAddress
				+ ", R:" + remoateAddress + "] " + message);
	}

	public static void bizdebug(String where, String connectionId,
			String localAddress, String remoateAddress, String message) {
		bizlog.debug("[" + where + ", " + connectionId + ", L:" + localAddress
				+ ", R:" + remoateAddress + "] " + message);
	}

	public static void bizerror(String where, String connectionId,
			String localAddress, String remoateAddress, String message) {
		bizlog.error("[" + where + ", " + connectionId + ", L:" + localAddress
				+ ", R:" + remoateAddress + "] " + message);
	}
}
