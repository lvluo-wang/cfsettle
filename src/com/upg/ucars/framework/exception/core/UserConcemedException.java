package com.upg.ucars.framework.exception.core;
/**
 * 
 * 功能说明：用户关心异常基类，继承自BaseAppRuntimeException；
 * 若有需要展示给用户看的异常信息，都必须由此类继承，
 * 在前台异常拦截器中会根据此异常类别决定如何展示。
 * @author shentuwy  
 * @date 2011-8-3 下午6:51:56 
 *
 */
public class UserConcemedException extends BaseAppRuntimeException {

	public UserConcemedException() {
		super();
	}

	public UserConcemedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UserConcemedException(String arg0) {
		super(arg0);
	}

	public UserConcemedException(Throwable arg0) {
		super(arg0);
	}

}
