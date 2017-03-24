package com.upg.cfsettle.mapping.org;

import java.io.Serializable;
import java.util.Date;

/**
 * 投资机构用户类
 * @author renzhuolun
 * @date 2016年8月22日 下午4:11:05
 * @version <b>1.0.0</b>
 */

public class OrgUser implements Serializable {
	/**
	 * 序号
	 */
	private static final long serialVersionUID = -1290871448705955166L;
	private Long id;
	private Long orgId;
	private String name;
	private String mobile;
	private String password;
	private String deptName;
	private String position;
	private Date endActiveTime;
	private Date loginTime;
	private Long parentId;
	private Long centerUid;
	private Long createdBy;
	private Long lastUpdatedBy;
	private Date ctime;
	private Date mtime;
	private String lastLoginIp;
	private Date lastLoginTm;

	// Constructors

	/** default constructor */
	public OrgUser() {
	}

	/** minimal constructor */
	public OrgUser(Long parentId) {
		this.parentId = parentId;
	}

	/** full constructor */
	public OrgUser(Long orgId, String name, String mobile, String password,
			String deptName, String position, Date endActiveTime,
			Date loginTime, Long parentId, Long centerUid,
			Long createdBy, Long lastUpdatedBy, Date ctime,
			Date mtime) {
		this.orgId = orgId;
		this.name = name;
		this.mobile = mobile;
		this.password = password;
		this.deptName = deptName;
		this.position = position;
		this.endActiveTime = endActiveTime;
		this.loginTime = loginTime;
		this.parentId = parentId;
		this.centerUid = centerUid;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.ctime = ctime;
		this.mtime = mtime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getEndActiveTime() {
		return this.endActiveTime;
	}

	public void setEndActiveTime(Date endActiveTime) {
		this.endActiveTime = endActiveTime;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getCenterUid() {
		return centerUid;
	}

	public void setCenterUid(Long centerUid) {
		this.centerUid = centerUid;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return this.mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTm() {
		return lastLoginTm;
	}

	public void setLastLoginTm(Date lastLoginTm) {
		this.lastLoginTm = lastLoginTm;
	}
}