package com.upg.ucars.framework.log.core;

public class SaaSLogInfo {	
	private String userNo;//平台客户号	
	private String info;//日志信息
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	@Override
	public String toString() {
		
		return info;
	}
	

}
