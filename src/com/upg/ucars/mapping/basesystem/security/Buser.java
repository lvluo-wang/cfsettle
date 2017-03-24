package com.upg.ucars.mapping.basesystem.security;

import java.util.Date;

/**
 * 用户
 * 
 * @author shentuwy
 * @date 2011-1-27 上午09:21:22
 *
 */
public class Buser implements java.io.Serializable {
	/**
	 * 实施人员
	 */
	public static final String TYPE_IMPLEMENTATION="1";
	/**
	 * SaaS运维
	 */
	public static final String TYPE_SAAS_MAINTENANCE="2";
	/**
	 * SaaS端管理
	 */
	public static final String TYPE_SAAS_MANAGER="3";	
	/**
	 * 机构总部管理
	 */
	public static final String TYPE_BRCH_GLOBAL_MANAGER="4";
	/**
	 * 机构分支管理
	 */
	public static final String TYPE_BRCH_LOCAL_MANAGER="5";
	
	/**
	 * 机构普通用户
	 */
	public static final String TYPE_BRCH_NOMAL_USER="6";
	/**
	 * 在线-登录成功后运行中的状态
	 */
	public static final String STATUS_ON_LINE="1";
	/**
	 * 离线—用户退出后的状态
	 */
	public static final String STATUS_OUT_LINE="2";
	/**
	 * 锁定-用户由于连续的密码错误造成锁定状态，锁定时间为1天
	 */
	public static final String STATUS_LOCK="3";
	/**
	 * 关闭-由管理人员手动的关闭某个用户，该用户永远不能登录
	 */
	public static final String STATUS_CLOSE="4";
	/**关联角色状态-未复核*/
	public static final String RE_ROLE_STATUS_UNCHECK="0";
	/**关联角色状态-复核*/
	public static final String RE_ROLE_STATUS_CHECK="1";
	/**关联角色状态-复核中*/
	public static final String RE_ROLE_STATUS_CHECKING="2";
	public static final String ERROR_NOT_ADD_MANAGER_FOR_OWN="SECURITY_112";
	/** 角色状态未分配  */
	public static final String ROLE_STATUS_UN_ASSIGN="0";
	/** 角色状态分配待审核  */
	public static final String ROLE_STATUS_ASSIGN_AUDITING="1";
	/** 角色状态已分配已审核 */
	public static final String ROLE_STATUS_ASSIGNED="2";
	/** 角色状态审核中  */
	public static final String ROLE_STATUS_AUDITING="3";
	private static final long serialVersionUID = -4150706669850781298L;
	public static final String CODE_ROLE_STATUS = "B016";
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 用户编号
	 */
	private String userNo;
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * Email
	 */
	private String email;
	/**
	 * 
	 * 密码
	 */
	private String password;
	
	private String userType;//用户类型 ,常量见UserType.*
	/**
	 * 归属机构id
	 */
	private Long brchId;
	/**
	 * 用户状态（0：离线，1：在线，2：锁定）
	 */
	private String status;
	/**
	 * 最后一次登录时间
	 */
	private Date lastLoginTm;
	/**
	 * 最后一次登录IP
	 */
	private String lastLoginIP;
	/**
	 * 最近一次密码修改日期
	 */
	private Date pwdChgDt;
	/**
	 * 密码连续错误次数
	 */
	private Long pwdErrNum;
	
	private Integer version;
	/**
	 * 接入编号
	 */
	private String miNo;
	
	/** 接入者名称 */
	private String miName;
	
	private transient String brchIdDesc;//字段的描述
	
	private String roleStatus;//角色状态：0-未分配，1-分配待审核，2-已分配
	public Buser() {
	}
	
	
	public Buser(Long userId, String userName, String userNo, String password,
			String userType, Long brchId, String status, Date lastLoginTm,
			String lastLoginIP, Date pwdChgDt, Long pwdErrNum) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userNo = userNo;
		this.password = password;
		this.userType = userType;
		this.brchId = brchId;
		this.status = status;
		this.lastLoginTm = lastLoginTm;
		this.lastLoginIP = lastLoginIP;
		this.pwdChgDt = pwdChgDt;
		this.pwdErrNum = pwdErrNum;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getBrchId() {
		return brchId;
	}
	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getLastLoginTm() {
		return lastLoginTm;
	}
	public void setLastLoginTm(Date lastLoginTm) {
		this.lastLoginTm = lastLoginTm;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public Date getPwdChgDt() {
		return pwdChgDt;
	}
	public void setPwdChgDt(Date pwdChgDt) {
		this.pwdChgDt = pwdChgDt;
	}
	public Long getPwdErrNum() {
		return pwdErrNum;
	}
	public void setPwdErrNum(Long pwdErrNum) {
		this.pwdErrNum = pwdErrNum;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getBrchIdDesc() {
		return brchIdDesc;
	}
	public void setBrchIdDesc(String brchIdDesc) {
		this.brchIdDesc = brchIdDesc;
	}
	public String getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}
	public String getMiNo() {
		return miNo;
	}
	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}
	public String getMiName() {
		return miName;
	}
	public void setMiName(String miName) {
		this.miName = miName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Buser other = (Buser) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}


	
}