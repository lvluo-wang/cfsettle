/*
 * 源程序名称: BaseResult.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：　外部服务接口
 * 
 */

package com.upg.ucars.model.response;

public class BaseResult {
	
	/*
	 * 成功码常量
	 */
	public static final String successCode="AAA";   
	/*
	 * 返回码。如果成功返回AAA,否则，返回具体的错误码。
	 */
	private String returnCode;
	/*
	 * 提示信息
	 */
	private String returnMsg;
	
	public String getReturnCode() {
		return returnCode;
	}
	
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	public String getReturnMsg() {
		return returnMsg;
	}
	
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	/**
	 * 是否成功
	 * @return
	 */
	public boolean isSuccess(){
		if(successCode.equals(returnCode)){
			return true;
		}else{
			return false;
		}
	}

}
