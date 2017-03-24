package com.upg.ucars.framework.exception;

import com.upg.ucars.framework.exception.core.UserConcemedException;



/**
 * 
 * 功能说明：登录异常，登录时相关的错误和失败信息使用此类封装抛出，
 * 抛出异常方式参考ExceptionManager
 * @author shentuwy  
 * @date 2011-8-3 下午6:48:46 
 *
 */
public class LoginException extends UserConcemedException{
	 LoginException() {
		super();
	}

	 LoginException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	 LoginException(String arg0) {
		super(arg0);
	}

	 LoginException(Throwable arg0) {
		super(arg0);
	}
	


}
