package com.upg.ucars.framework.exception;

import com.upg.ucars.framework.exception.core.UserNotConcemedException;



/**
 * 
 * 功能说明：系统产生的不可预见异常,在编码时，可能发生的非检查性异常，
 * 例如，数组越界，空指针，这样的异常封装为系统异常，
 * 抛出方式参考ExceptionManager
 * @author shentuwy  
 * @date 2011-8-3 下午6:50:45 
 *
 */
public class SysException extends UserNotConcemedException {

	 SysException() {
		super();
	}

	 SysException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	 SysException(String arg0) {
		super(arg0);
	}

	 SysException(Throwable arg0) {
		super(arg0);
	}
	


}
