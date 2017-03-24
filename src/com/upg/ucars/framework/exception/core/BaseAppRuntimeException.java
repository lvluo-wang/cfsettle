package com.upg.ucars.framework.exception.core;

import java.io.Serializable;


/**
 * 
 * @ClassName: BaseAppRuntimeException
 * @Description: TODO(继承自RuntimeException，增加错误码和参数字段，系统中所有自定义异常必须从此类
 * 或此类的子类继承。在实际开发中，编码人员只需要从UserConcemedException或UserNotConcemedException继承，
 * 这两个类已继承BaseAppRuntimeException,详细信息参考 UserConcemedException 和 UserNotConcemedException.)
 * @author cuckoo 
 * 
 * @date 2010-12-17 上午11:26:57
 *
 */
public class BaseAppRuntimeException extends RuntimeException implements
		Serializable {
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @Fields errCode (异常错误代码)
	 */
	private String errCode;
	/**
	 * @Fields message {异常错误信息}
	 */
	private String message;

	public BaseAppRuntimeException() {
		super();
	}
	public BaseAppRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	public BaseAppRuntimeException(String arg0) {
		super(arg0);
	}
	public BaseAppRuntimeException(Throwable arg0) {
		super(arg0);
	}
	/**
	 * 
	 * @Title: getErrCode
	 * @Description: TODO(获取异常码)
	 * @return  String
	 */
	public String getErrCode() {
		return errCode;
	}
	/**
	 * 
	 * @Title: setErrCode
	 * @Description: TODO(设置异常码)
	 * @param errCode  异常码，与异常常量类中定义的常量一致
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
