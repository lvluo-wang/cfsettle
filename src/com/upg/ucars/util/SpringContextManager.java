package com.upg.ucars.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 
 * Spring管理类
 * 
 * @author shentuwy
 * @date 2012-7-30
 * 
 */
public class SpringContextManager extends ContextLoaderListener {

	private static Logger					log	= LoggerFactory
														.getLogger(SpringContextManager.class);

	private static WebApplicationContext	applicationConext;
	/** 是否已经初始化 */
	private static boolean					initialized;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		long start = System.currentTimeMillis();
		log.info("spring container is starting to initialize...");
		super.contextInitialized(event);
		applicationConext = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		initialized = true;

		log.info("spring container has initialized finished,spend time:"
				+ (System.currentTimeMillis() - start) + "ms.");
	}
	
	public static BeanFactory getBeanFactory(){
		return applicationConext;
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		applicationConext = null;
		initialized = false;
	}

	/**
	 * 获取Spring配置的bean实例
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		if (applicationConext == null)
			return null;
		return applicationConext.getBean(beanName);
	}
	
	/**
	 * 根据bean类型获取
	 * 
	 * @param cls
	 * @return
	 */
	public static final <T> T getBean(Class<T> cls){
		T result = null;
		if (applicationConext == null && cls != null) {
			result = applicationConext.getBean(cls);
		}
		return result;
	}
	
	/**
	 * 获取指定类型的所有Bean
	 * 
	 * @param cls
	 * @return
	 */
	public static final <T> List<T> getBeanOfType(Class<T> cls){
		List<T> result = new ArrayList<T>();
		if (cls != null) {
			String[] beanNames = applicationConext.getBeanNamesForType(cls);
			if (beanNames != null) {
				for (String beanName : beanNames) {
					T bean = (T) applicationConext.getBean(beanName);
					if (bean != null) {
						result.add(bean);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 完成初始化
	 * 
	 * @return
	 */
	public static boolean isInitialized() {
		return initialized;
	}

}
