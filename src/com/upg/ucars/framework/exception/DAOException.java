package com.upg.ucars.framework.exception;

import com.upg.ucars.framework.exception.core.UserNotConcemedException;




/**
 * 
 * 功能说明：数据访问层异常，在数据访问层中产生的异常信息，使用此异常进行封装抛出，
 * 抛出方式参考ExceptionManager
 * @author shentuwy  
 * @date 2011-8-3 下午6:45:59 
 *
 */
public class DAOException extends UserNotConcemedException {

	 DAOException() {
		super();
	}

	 DAOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	 DAOException(String arg0) {
		super(arg0);
	}

	 DAOException(Throwable arg0) {
		super(arg0);
	}
	


}
