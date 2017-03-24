package com.upg.ucars.framework.esb.core;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;

public class ESBServer implements IESBServer,ApplicationContextAware{
	private ApplicationContext springContext;
	/*
	 * ESB 配置参数
	 */
	private String[] esbConfs;
	/*
	 * ESB 上下文
	 */
	private static MuleContext ctx;
	public void start() throws ServiceException {
		MuleContext ctx = getMuleContext();
		if (!ctx.isStarting() || !ctx.isStarted()) {
			try {
				ctx.start();
			} catch (MuleException e) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_SERVER_START_ERROR, e);
			}
		}
	}
	public void stop() throws ServiceException {
        if (ctx != null) {
            if (!ctx.isDisposing() || !ctx.isDisposed()) {
                ctx.dispose();
            }
        }
	}
	public MuleContext getMuleContext() throws ServiceException{
		if(ctx != null) {
			return ctx;
		} else {
			return createMuleContext();
		}
	}
	/**
	 * 创建mule上下文
	 * @return	MuleContext
	 * @throws ServiceException
	 */
	private MuleContext createMuleContext() throws ServiceException{
		MuleContext mc = null;
		try {
//			SpringXmlConfigurationBuilder builder = new SpringXmlConfigurationBuilder(esbConfs);
//			
//			MuleContextFactory ctxFactory = new DefaultMuleContextFactory();
//			
//			MuleConfiguration mConf = new DefaultMuleConfiguration();
//			MuleContextBuilder ctxBuilder = new DefaultMuleContextBuilder();
//			ctxBuilder.setMuleConfiguration(mConf);
//			
//			mc = ctxFactory.createMuleContext(builder, ctxBuilder);
			//下面是hehl修改的
			SpringXmlConfigurationBuilder builder = new SpringXmlConfigurationBuilder(esbConfs);
			builder.setParentContext(springContext);
			mc = new DefaultMuleContextFactory().createMuleContext(builder); 
		} catch (ConfigurationException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CONF_ERROR, e);
		} catch (InitialisationException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_SERVER_INIT_ERROR, e);
		} 
		return mc;
	}

	public String[] getEsbConfs() {
		return esbConfs;
	}
	
	public void setEsbConfs(String[] esbConfs) {
		this.esbConfs = esbConfs;
	}
	
	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		this.springContext = appContext;
	}

}
