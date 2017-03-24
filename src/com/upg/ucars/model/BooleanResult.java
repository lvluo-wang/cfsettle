
package com.upg.ucars.model;
/**
 * Boolean+结果信息
 *
 * @author shentuwy
 */
public class BooleanResult implements java.io.Serializable {	
	private static final long serialVersionUID = 1L;

	private boolean success;//是否成功
	
	private String info;//结果信息
	
	public BooleanResult(){
	}
	
	public BooleanResult(Boolean success){
		this.success = success.booleanValue();
	}
	
	public BooleanResult(boolean success){
		this.success = success;
	}
	
	public BooleanResult(boolean success, Object info){
		this.success = success;
		if (info != null){
			this.info = info.toString();
		}
		
	}
	
	public BooleanResult(Boolean success, Object info){		
		this.success = success.booleanValue();
		if (info != null){
			this.info = info.toString();
		}
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	

}
