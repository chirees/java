package com.framework.runtime.application.app;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.LogU;
import com.framework.runtime.application.exception.ApplicationException;
import com.framework.runtime.application.redis.RedisService;
import com.framework.runtime.application.util.IdWorker;
import com.framework.runtime.application.util.SecrectUtil;
import com.framework.runtime.application.util.SeqWorker;
import com.google.gson.Gson;

public class RedisApplicationRegister implements ApplicationRegister {
	private static final String APPTABLE = "app_table_monitor";
	private static final String LOCK = "app_status_monitor";
	private static final long LOCKTIME = 2L;
	private static final long LIMITTIME = 30000L;
	
	private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	
	private RedisService redisService;
	
	public void start(final String ip) {
		service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
//					LogU.n("runnable", "run", "");
					RedisApplicationRegister.this.login(Application.getInstance().getCode(), Application.getInstance().getName(), ip);
				} catch (Exception e) {
					LogU.e("runnable", "run", e);
				}
			}
			
		}, 0, 20, TimeUnit.SECONDS);	
	}

	@Override
	public void login(String code, String name, String ip) throws ApplicationException {
		boolean yes = redisService.lock(LOCK, LOCKTIME);
		while(!yes) {
			yes = redisService.lock(LOCK, LOCKTIME);
			try {
				Thread.currentThread().sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ApplicationTable table = (ApplicationTable)redisService.getJson(APPTABLE, ApplicationTable.class);
		if(table == null) {
			table = new ApplicationTable();
		}
		Gson gson = new Gson();
//		LogU.n("app table", "login", gson.toJson(table));
		ApplicationNode node = table.getApplicationNode(code);
		InstanceNode in = null;
		if(node == null) {
			node = new ApplicationNode();
			node.setAlive(true);
			node.setAliveCount(1);
			node.setCode(code);
			node.setName(name);
			node.setId(1);
			
			in = new InstanceNode();
			in.setAlive(true);
			in.setIp(ip);
			in.setStartTime(new Date());
			
			node.putInstanceNode(in);
			table.put(node);
		}
		else {
			in = node.getInstanceNode(ip);
			if(in == null) {
				in = new InstanceNode();
				in.setAlive(true);
				in.setIp(ip);
				in.setStartTime(new Date());
				
				node.putInstanceNode(in);
			}
		}
		
		table.check(code, ip, LIMITTIME);
//		LogU.n("app table", "login", gson.toJson(table));
		long time = 30L * 24 * 3600 * 1000;
//		LogU.i(this, "etime", time + "");
		redisService.setJson(APPTABLE, table, time);

		Application.getInstance().setIdWorker(new IdWorker(node.getId(), in.getId()));
		Application.getInstance().setSeqWorker(new SeqWorker(node.getId(), in.getId()));
		redisService.unlock(LOCK);
		
	}
	
	public static void main(String[] args) {
		System.out.println(SecrectUtil.randKey(32));
	}

	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	
	

}
