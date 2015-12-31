package com.pte.liquid;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.AbstractApplicationContext;

@SpringBootApplication
@ImportResource({"broker-application-context.xml","storage-application-context.xml"})
public class Liquid implements Daemon{

	private static ConfigurableApplicationContext ctx;
	private final static Logger logger = Logger.getLogger(Liquid.class);	

	public static void main(String[] args) {		
		buildUp(args);		
	}			
	
	@Override
	public void destroy() {
		tearDown();
	}

	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		logger.info("Initializing liquid daemon");
		buildUp(context.getArguments());			
	}

	@Override
	public void start() throws Exception {
		logger.info("Start liquid daemon");
		
	}

	@Override
	public void stop() throws Exception {
		logger.info("Stopped liquid daemon");
		tearDown();
		
	}
	
	public static void startService(String[] args) throws Exception {
		logger.info("Start liquid serivce");
		buildUp(args);
	}


	
	public static void stopService(String[] args) throws Exception {
		logger.info("Stopped liquid serivce");
		tearDown();
	}

	private static void buildUp(String[] args) {		
		logger.info("Starting context...");
		logger.info("Working Directory = " + System.getProperty("user.dir"));
		ctx = SpringApplication.run(Liquid.class, args);
		logger.info("Done starting context...");
	}

	
	private static void tearDown() {
		logger.info("Stopping context...");
		((AbstractApplicationContext) ctx).stop();		
		((AbstractApplicationContext) ctx).close();		
		((AbstractApplicationContext) ctx).destroy();
		logger.info("Context stopped...");
	}
	


}
