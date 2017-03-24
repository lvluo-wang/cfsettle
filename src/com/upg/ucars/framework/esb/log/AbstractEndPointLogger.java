package com.upg.ucars.framework.esb.log;

import org.apache.commons.logging.Log;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;

import com.upg.ucars.util.LogUtil;

/**
 * 端点日志器 记录出入端点的请求，响应及报文信息
 * 
 * @author shentuwy
 * @date 2011-9-19
 **/
public abstract class AbstractEndPointLogger implements MessageProcessor,Initialisable {
	public static final String LOG_PROP_LIMIT_ALL = "ALL";
	public static final String LOG_PROP_LIMIT_SIMPLE = "SIMPLE";
	public static final String LOG_PROP_LIMIT_CUSTOM = "CUSTOM";
	
	protected transient Log logger;
	
	private boolean mayLog = false;

	private String level = "INFO";
	
	private String logLimit = "SIMPLE";
	
	/**
	 * 需要记录日志的属性列表，以","隔开
	 * */
	private String logProps;

	public void initialise() throws InitialisationException {
		logger = LogUtil.getESBCommLog();
		if ("ERROR".equals(level)) {
			if (logger.isErrorEnabled()) {
				mayLog = true;
			}
		} else if ("WARN".equals(level)) {
			if(logger.isWarnEnabled()){
				mayLog = true;
			}
		} else if ("INFO".equals(level)) {
			if (logger.isInfoEnabled()) {
				mayLog = true;
			}
		} else if ("DEBUG".equals(level)) {
			if (logger.isDebugEnabled()) {
				mayLog = true;
			}
		} else if ("TRACE".equals(level)) {
			if (logger.isTraceEnabled()) {
				mayLog = true;
			}
		}
	}

	public MuleEvent process(MuleEvent event) throws MuleException {
		if(mayLog)
			log(event);
		return event;
	}

	public abstract void log(MuleEvent event);

	protected void logWithLevel(Object object) {
		if ("ERROR".equals(level)) {
			if (logger.isErrorEnabled()) {
				logger.error(object);
			}
		} else if ("WARN".equals(level)) {
			if(logger.isWarnEnabled()){
				logger.warn(object);
			}
		} else if ("INFO".equals(level)) {
			if (logger.isInfoEnabled()) {
				logger.info(object);
			}
		} else if ("DEBUG".equals(level)) {
			if (logger.isDebugEnabled()) {
				logger.debug(object);
			}
		} else if ("TRACE".equals(level)) {
			if (logger.isTraceEnabled()) {
				logger.trace(object);
			}
		}
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLogLimit() {
		return logLimit;
	}

	public void setLogLimit(String logLimit) {
		this.logLimit = logLimit;
	}

	public String getLogProps() {
		return logProps;
	}

	public void setLogProps(String logProps) {
		this.logProps = logProps;
	}
}
