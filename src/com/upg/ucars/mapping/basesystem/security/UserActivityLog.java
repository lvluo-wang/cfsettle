package com.upg.ucars.mapping.basesystem.security;

import java.util.Date;

/**
 * UserActivityLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UserActivityLog implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8227699498125008809L;
	
	//登录
	public static final String LOGON="1";
	//退出
	public static final String LOGOUT="2";
	// Fields

	private Long logId;
	private Long sysUserId;
	private Long brchId;
	private String miNo;
	private Date logTm;
	private String logInfo;
	private String activityType;
	/**
	 * 查询域
	 */
	private Date logTmBegin;
	private Date logTmEnd;
	/**
	 * 显示域
	 */
	private String sysUserName;
	private String brchName;
	private String miName;
	/*
	 * 版本号
	 */
	private Integer version;

	// Constructors

	/** default constructor */
	public UserActivityLog() {
	}

	/** full constructor */
	public UserActivityLog(Long sysUserId, Long brchId, String miNo,
			Date logTm, String logInfo, String activityType, Integer version) {
		this.sysUserId = sysUserId;
		this.brchId = brchId;
		this.miNo = miNo;
		this.logTm = logTm;
		this.logInfo = logInfo;
		this.activityType = activityType;
		this.version = version;
	}

	// Property accessors

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Long getBrchId() {
		return this.brchId;
	}

	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}

	public String getMiNo() {
		return this.miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Date getLogTm() {
		return this.logTm;
	}

	public void setLogTm(Date logTm) {
		this.logTm = logTm;
	}

	public String getLogInfo() {
		return this.logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getActivityType() {
		return this.activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Date getLogTmBegin() {
		return logTmBegin;
	}

	public void setLogTmBegin(Date logTmBegin) {
		this.logTmBegin = logTmBegin;
	}

	public Date getLogTmEnd() {
		return logTmEnd;
	}

	public void setLogTmEnd(Date logTmEnd) {
		this.logTmEnd = logTmEnd;
	}

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public String getBrchName() {
		return brchName;
	}

	public void setBrchName(String brchName) {
		this.brchName = brchName;
	}

	public String getMiName() {
		return miName;
	}

	public void setMiName(String miName) {
		this.miName = miName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}