package com.upg.ucars.framework.exception;

import com.upg.ucars.framework.exception.core.UserConcemedException;



/**
 * 
 * 功能说明：安全相关异常，系统安全、权限管理方面的异常，使用此类封装，
 * 抛出方式参考ExceptionManager
 * @author shentuwy  
 * @date 2011-8-3 下午6:49:51 
 *
 */
public class RightsException extends UserConcemedException {

	 RightsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	 RightsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	 RightsException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	 RightsException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	


}
