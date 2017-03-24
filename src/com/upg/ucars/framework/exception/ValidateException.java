package com.upg.ucars.framework.exception;

import com.upg.ucars.framework.exception.core.UserConcemedException;
/**
 * 
 * 功能说明：数据校验异常，来自界面的用户输入数据在校验时不合法，使用此异常封装，
 * 抛出方式参考ExceptionManager
 * @author shentuwy  
 * @date 2011-8-3 下午6:51:07 
 *
 */
public class ValidateException extends UserConcemedException {

	 ValidateException() {
		super();
	}

	 ValidateException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	 ValidateException(String arg0) {
		super(arg0);
	}

	 ValidateException(Throwable arg0) {
		super(arg0);
	}


}
