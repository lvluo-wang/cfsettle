package com.upg.ucars.model;

import java.io.Serializable;
/**
 * 
 * 功能说明：TODO(系统状态)
 * @author shentuwy  
 * @date 2012-1-29 上午11:42:58 
 *
 */
public class SysStatus implements Serializable {
	//正常
	public static final int NORMAL=1;
	//中断
	public static final int INTERCEPT=2;
	//级别，参考NORMAL，INTERCEPT
	private int level;
	//信息
	private String info;

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public SysStatus(){};
	public SysStatus(int level,String info){
		this.level=level;
		this.info=info;
	}
}
