package com.upg.ucars.model.security;

import java.util.List;
/**
 * 
 * 功能说明：TODO(用户登录信息)
 * @author shentuwy  
 * @date 2012-1-29 上午11:49:15 
 *
 */
public class UserLogonInfo implements java.io.Serializable{

	private static final long serialVersionUID = -6420863261986626239L;
	private Long sysUserId;//用户id
	private String userNo;//用户操作员号
	private String userName;//用户操作员
	private Long branchId;//机构id
	private String branchName;//机构名称
	private String branchTreeCode;//机构树形码
	private String branchParentTreeCode;//上级机构树形码
	private String branchNo;//机构编号
	private String userType;
	private List<Long> roleList;//用户的角色集合
	private Long lastOperTime;//上次操作时间
	private String lastLogonIP;//上次登录ip
	private String miNo;//接入编号
	private String theme="default";//主题
	private Long areaId;
	private Long deptId;
	private Long teamId;
	public UserLogonInfo() {}
	
	public UserLogonInfo(Long sysUserId, String userNo, String userName,
			Long branchId, String branchName,
			List<Long> roleList, Long lastOperTime,String lastLogonIP) {
		super();
		this.sysUserId = sysUserId;
		this.userNo = userNo;
		this.userName = userName;
		this.branchId = branchId;
		this.branchName = branchName;
		this.roleList = roleList;
		this.lastOperTime = lastOperTime;
		this.lastLogonIP=lastLogonIP;
	}

	public Long getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public List<Long> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Long> roleList) {
		this.roleList = roleList;
	}
	public Long getLastOperTime() {
		return lastOperTime;
	}
	public void setLastOperTime(Long lastOperTime) {
		this.lastOperTime = lastOperTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLastLogonIP() {
		return lastLogonIP;
	}

	public void setLastLogonIP(String lastLogonIP) {
		this.lastLogonIP = lastLogonIP;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public String getBranchTreeCode() {
		return branchTreeCode;
	}

	public void setBranchTreeCode(String branchTreeCode) {
		this.branchTreeCode = branchTreeCode;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getBranchParentTreeCode() {
		return branchParentTreeCode;
	}

	public void setBranchParentTreeCode(String branchParentTreeCode) {
		this.branchParentTreeCode = branchParentTreeCode;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
}