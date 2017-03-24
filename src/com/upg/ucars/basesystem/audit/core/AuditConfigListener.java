/*
 * 源程序名称: AuditConfigListener.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(审批配置装载器)
 * 
 */

package com.upg.ucars.basesystem.audit.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 
 * 功能说明：审批配置文件资源加载器
 * @author shentuwy  
 * @date 2011-8-14 下午2:48:08 
 *
 */
public class AuditConfigListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {

	}
	/**
	 * 在服务器启动时，装载配置信息
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		//加载审批相关配置资源
		AuditConfig.initConfig();
	}

}
