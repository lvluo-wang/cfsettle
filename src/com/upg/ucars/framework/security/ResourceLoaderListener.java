package com.upg.ucars.framework.security;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;




/**
 * (权限资源加载器，系统启动时将需要进行资源加载内存中，供后续使用)
 * 
 * @date 2011-1-12 下午07:53:48
 * @version V1.0
 */
public class ResourceLoaderListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		//加载权限相关资源
		SecurityResources.initResources();
		
	}
}
