package com.upg.ucars.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * 日志工具类
 *	系统中所有需要记录日志的地方,统一从LogUtil类中获取相关日志对象，记录日志
 * @author shentuwy
 */
public final class LogUtil {
	public final static String APPENDER_COMMON = "ASYNC_COMMON";
	public final static String APPENDER_EXCEPTION ="EXCEPTION";
	public final static String APPENDER_USER ="USER";
	public final static String APPENDER_WORKFLOW ="WORKFLOW";
	public final static String APPENDER_TRANSACTION ="TRANSACTION";
	public final static String APPENDER_INTERFACE_IO ="INTERFACEIO";
	public final static String APPENDER_ESB_COMM = "esbComm";	//ESB 通讯日志
	/**
	 * 获取通用的日志对象<br><br>
	 * 详细配置请查看log4j.xml文件
	 * @return 通用日志对象
	 */
	public static Log getCommonLog() {		
			Log log = LogFactory.getLog(APPENDER_COMMON);
			return log;		
	}
	/**
	 * 用户活动日志对象
	 * @return
	 */
	public static Log getUserLog(){
		Log log=LogFactory.getLog(APPENDER_USER);
		return log;
	}
	/**
	 * 工作流程日志对象
	 * @return
	 */
	public static Log getWorkFlowLog(){
		Log log=LogFactory.getLog(APPENDER_WORKFLOW);
		return log;
	}
	/**
	 * 事务相关日志对象
	 * @return
	 */
	public static Log getTransactionLog(){
		Log log=LogFactory.getLog(APPENDER_TRANSACTION);
		return log;
	}
	/**
	 * 接口及io相关日志对象
	 * @return
	 */
	public static Log getInterfaceLog(){
		Log log=LogFactory.getLog(APPENDER_INTERFACE_IO);
		return log;
	}
	/**
	 * 异常日志对象
	 * @return
	 */
	public static Log getExceptionLog(){
		Log log=LogFactory.getLog(APPENDER_EXCEPTION);
		return log;
	}
	/**
	 * 获取ESB 通讯日志记录器
	 * @return Log
	 */
	public static Log getESBCommLog() {
		Log log = LogFactory.getLog(APPENDER_ESB_COMM);
		return log;
	}
}

 