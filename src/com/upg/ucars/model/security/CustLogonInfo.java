/*
 * 源程序名称: ClientLogonInfo.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.model.security;


import com.upg.ucars.model.request.CustRegisterInfo;

/**
 * 客户登录信息
 * 功能说明：　用于记录网银客户登录信息
 * @author shentuwy
 * @date Jul 11, 2011 2:12:47 PM 
 *
 */
public class CustLogonInfo extends CustRegisterInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -707576019586876329L;
	
	/** 用户编号 */
	private String userNo;
	
	/** 网银登录ID  */
	private Long ibUserId;
	
	/** 子系统id */
	private Long subsystemId;
	
	/** 是否是监管 */
	private boolean isJianGuan = false;
	
	private Long acctId;

	public boolean isJianGuan() {
		return isJianGuan;
	}

	public void setJianGuan(boolean isJianGuan) {
		this.isJianGuan = isJianGuan;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Long getIbUserId() {
		return ibUserId;
	}

	public void setIbUserId(Long ibUserId) {
		this.ibUserId = ibUserId;
	}

	public Long getSubsystemId() {
		return subsystemId;
	}

	public void setSubsystemId(Long subsystemId) {
		this.subsystemId = subsystemId;
	}

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}
	
	
}
