package com.upg.ucars.framework.exception.core;
/**
 * 
 * 功能说明：用户不关心的异常基类，继承自BaseAppRuntimeException，
 * 所有不需要明确展示给用户看的异常，由此类继承。
 * @author shentuwy  
 * @date 2011-8-3 下午6:53:40 
 *
 */
public class UserNotConcemedException extends BaseAppRuntimeException {

	public UserNotConcemedException() {
		super();
	}

	public UserNotConcemedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UserNotConcemedException(String arg0) {
		super(arg0);
	}

	public UserNotConcemedException(Throwable arg0) {
		super(arg0);
	}

	

}
