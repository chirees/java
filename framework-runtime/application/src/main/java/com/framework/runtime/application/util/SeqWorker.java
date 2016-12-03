package com.framework.runtime.application.util;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeqWorker {
    
    protected static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);
     
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
 
    private long workerIdBits = 5L;
    private long datacenterIdBits = 7L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
 
    private long lastTimestamp = -1L;
    
    public static void main(String[] args) throws Exception {
    	SeqWorker iw = new SeqWorker(2, 3);
    	StringBuffer sb = new StringBuffer();
    	for(int i = 0; i < 100; i++) {
    		
    		sb.append(iw.nextSeq());
    		sb.append('\n');
//    		Thread.currentThread().sleep(5);
    	}
    	System.out.println(sb);
    }
 
    public SeqWorker(long workerId, long datacenterId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        
        dcAndWorkNum = (long)(Math.pow(datacenterId, 25) + Math.pow(workerId, 23) );
//        LOG.info(this.maxWorkerId + ", " + this.maxDatacenterId);
//        LOG.info(String.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d", timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
    }
 
   
    
    private long dcAndWorkNum = 0;
    public synchronized String nextSeq() {
    	
    	
        long timestamp = timeGen();
 
        if (timestamp < lastTimestamp) {
            LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
 
        if (lastTimestamp == timestamp) {
            sequence += 1;
            if (sequence == 999) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
 
        lastTimestamp = timestamp;
        
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        
        // datacenterId * 10的17次方
        // workerId * 10的15次方
        int year = c.get(Calendar.YEAR) % 100; // 2位
        int month = c.get(Calendar.MONTH); // 2位
        int day = c.get(Calendar.DATE); // 2位
        int hour = c.get(Calendar.HOUR); // 2位
        int min = c.get(Calendar.MINUTE); // 2位
        int sec = c.get(Calendar.SECOND); // 2位
        int mil = c.get(Calendar.MILLISECOND); // 3位
        
        String id = String.format("%02d%02d%02d%02d%02d%02d%02d%02d%03d%03d", year, month, day, hour , min, sec, datacenterId, workerId, sequence, mil);
       
        return id;
    }
 
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
 
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}

