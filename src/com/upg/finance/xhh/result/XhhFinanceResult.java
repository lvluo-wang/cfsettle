package com.upg.finance.xhh.result;

/**
 * 返回给鑫合汇的结果类
 * 
 */
public class XhhFinanceResult {

	/**
	 * 调用是否成功
	 * 
	 */
	private boolean	isSuccess	= true;

	/**
	 * 错误信息
	 * 
	 */
	private String	errorMsg;

	/**
	 * 返回的具体信息
	 * 
	 */

	private Object	result;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
