package com.upg.ucars.framework.log.core;

public class LogInfoModel {
	private String userNo;
	private String info;
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
