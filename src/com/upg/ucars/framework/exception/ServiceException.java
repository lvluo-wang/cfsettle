package com.upg.ucars.framework.exception;

import com.upg.ucars.framework.exception.core.UserConcemedException;



/**
 * 功能说明：业务服务层异常，业务逻辑处理相关的异常信息，使用此类封装，
 * 
 * @author shentuwy
 * @date 2012-8-9
 *
 */
public class ServiceException extends UserConcemedException {

	 ServiceException() {
		super();
	}

	 ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	 ServiceException(String message) {
		super(message);
	}

	 ServiceException(Throwable cause) {
		super(cause);
	}


}
