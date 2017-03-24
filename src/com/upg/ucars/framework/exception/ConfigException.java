package com.upg.ucars.framework.exception;

import com.upg.ucars.framework.exception.core.UserNotConcemedException;

/**
 * 功能说明：系统配置文件配置异常，
 * 抛出方式参考ExceptionManager
 * @author shentuwy  
 * @date 2011-9-9 下午6:50:17 
 * */
public class ConfigException extends UserNotConcemedException {
	private static final long serialVersionUID = -4884382767360404084L;

	ConfigException() {
		super();
	}

	ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	ConfigException(String message) {
		super(message);
	}

	ConfigException(Throwable cause) {
		super(cause);
	}
}
