package com.upg.ucars.framework.exception;

import com.upg.ucars.framework.exception.core.UserNotConcemedException;

/**
 * 
 * 功能说明：流程服务异常
 * 工作流在使用中，产生的异常，使用此类封装，
 * 抛出方式参考ExceptionManager
 * @author shentuwy  
 * @date 2011-8-3 下午6:49:25 
 *
 */
public class ProcessException extends UserNotConcemedException {

	 ProcessException() {
		super();		
	}

	 ProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	 ProcessException(String message) {
		super(message);
	}

	 ProcessException(Throwable cause) {
		super(cause);
	}


}
