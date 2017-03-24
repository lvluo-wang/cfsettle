package com.upg.ucars.framework.esb.core;

import org.mule.api.MuleContext;

import com.upg.ucars.framework.exception.ServiceException;

/**
 * esb server
 * @author hezw
 *
 */
public interface IESBServer {
	/**
	 * 启动 ESB server
	 * @throws ServiceException
	 */
	void start() throws ServiceException;
	/**
	 * 停止 ESB Server
	 * @throws ServiceException
	 */
	void stop() throws ServiceException;
	/**
	 * 获取 ESB 上下文
	 * @return	MuleContext
	 */
	MuleContext getMuleContext() throws ServiceException;

}
