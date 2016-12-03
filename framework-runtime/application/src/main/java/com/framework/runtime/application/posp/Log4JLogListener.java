package com.framework.runtime.application.posp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.jpos.util.LogEvent;
import org.jpos.util.LogListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;

public class Log4JLogListener implements LogListener {
	private Logger log = LoggerFactory.getLogger(Application.getInstance().getCoreLogger()); 
	private PrintStream p;
    private ByteArrayOutputStream bas;
    private Charset charset = Charset.forName("UTF-8");

    public Log4JLogListener () {
        super();
        p = System.out;
    }
    public Log4JLogListener (ByteArrayOutputStream bas) {
        this ();
        setPrintStream (new PrintStream(bas));
        this.bas = bas;
    }
    public synchronized void setPrintStream (PrintStream p) {
        this.p = p;
    }
    public synchronized void close() {
        if (p != null) {
            p.close();
            p = null;
        }
    }
    public synchronized LogEvent log (LogEvent ev) {
        if (p != null) {
            ev.dump (p, "");
            p.flush();
            if(bas != null) {
            	
            	log.info("\n" + new String(bas.toByteArray(), charset));
            	bas.reset();
            }
        }
        return ev;
    }
}
