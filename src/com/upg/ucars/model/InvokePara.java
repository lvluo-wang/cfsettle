package com.upg.ucars.model;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author jacke_yang
 *  所有的blo服务需要以此为参数，唯一参数
 */
public class InvokePara implements java.io.Serializable {
	private String beanName;//blo定义在spring容器中的名称
	private String operName;//相关业务服务名称，对应于blo中的方法
	private Map  trans=new HashMap();  //调用服务的数据包载体，服务响应后返回的数据包载体
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public boolean isCallSuccess() {
		return isCallSuccess;
	}
	public void setCallSuccess(boolean isCallSuccess) {
		this.isCallSuccess = isCallSuccess;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg=errMsg==null?"":errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Map getTrans() {
		return trans;
	}
	public void addPara(String key,Object value){
		trans.put(key, value);
	}
	public Object getValue(String key){
		return trans.get(key);
	}
	
	
	/**
	 * 异常信息处理
	 */
	private boolean isCallSuccess=true;
	private String errCode;
	private String errMsg;
	
	
}
