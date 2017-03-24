package com.upg.ucars.framework.esb.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.upg.ucars.util.SpringContextManager;

public class ESBListener implements ServletContextListener {
	
	IESBServer esbServer;
	
	public void contextDestroyed(ServletContextEvent arg0) {
		esbServer.stop();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		setServer();
//		LogUtil.getESBCommLog().info("ESB start");
		esbServer.start();
//		LogUtil.getESBCommLog().info("ESB started");
	}
	private void setServer() {
		esbServer = (IESBServer)SpringContextManager.getBean("muleServer");
	}

}
