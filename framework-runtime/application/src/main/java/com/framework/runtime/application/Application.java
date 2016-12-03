package com.framework.runtime.application;

import java.util.List;

import com.framework.runtime.application.util.IdWorker;
import com.framework.runtime.application.util.SeqWorker;

public class Application {
	private String name = "NONE_DEFINE";
	private String code = "NONE_DEFINE";
	
	private static Application instance;
	
	private IdWorker idWorker;
	private SeqWorker seqWorker;
	
	private String ip;
	
	private int port;
	
	
	public IdWorker getIdWorker() {
		return idWorker;
	}

	public void setIdWorker(IdWorker idWorker) {
		if(this.idWorker == null)
		this.idWorker = idWorker;
	}
	
	

	public SeqWorker getSeqWorker() {
		return seqWorker;
	}

	public void setSeqWorker(SeqWorker seqWorker) {
		if(this.seqWorker == null)
		this.seqWorker = seqWorker;
	}

	public static Application getInstance() {
		if(instance == null) {
			instance = new Application();
		}
		
		return instance;
	}
	
	private Application() {
		
	}
	
	
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
//		System.out.println(Enum.valueOf(Application.RETURN_CODE.class, "SUCCESS"));
	}
	
	public void saveReturnCodes(List<ReturnCode> codes) {
		
	}
	
	public String getCoreLogger() {
		return this.getName() + "-core";
	}
	
	public String getCmdLogger() {
		return this.getName() + "-cmd";
	}
	
	public String getNetLogger() {
		return this.getName() + "-net";
	}
	
	public String getRuntimeLogger() {
		return this.getName() + "-runtime";
	}
	
	public String getErrorLogger() {
		return this.getName() + "-error";
	}
	
	
	public ReturnCode getMappedReturnCode(ReturnCode code) {
		return code;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
