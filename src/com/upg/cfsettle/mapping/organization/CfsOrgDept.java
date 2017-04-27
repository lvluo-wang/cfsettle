package com.upg.cfsettle.mapping.organization;
// Generated 2017-3-29 11:23:48 by Hibernate Tools 5.2.1.Final

import java.util.Date;

/**
 * CfsOrgDept generated by hbm2java
 */
public class CfsOrgDept implements java.io.Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -6170028073205162943L;
	private Long id;
	private String deptName;
	private Long ownedArea;
	private String deptMobile;
	private String deptAddr;
	private Byte status;
	private Long csysid;
	private Date ctime;
	private Long msysid;
	private Date mtime;
	private String posCode;
	
	private String havBuser;

	private String areaName;

	public CfsOrgDept() {
	}

	public CfsOrgDept(Long ownedArea, Byte status) {
		this.ownedArea = ownedArea;
		this.status = status;
	}

	public CfsOrgDept(Byte status, Long id) {
		this.status = status;
		this.id = id;
	}

	public CfsOrgDept(Byte status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getOwnedArea() {
		return ownedArea;
	}

	public void setOwnedArea(Long ownedArea) {
		this.ownedArea = ownedArea;
	}

	public String getDeptMobile() {
		return deptMobile;
	}

	public void setDeptMobile(String deptMobile) {
		this.deptMobile = deptMobile;
	}

	public String getDeptAddr() {
		return deptAddr;
	}

	public void setDeptAddr(String deptAddr) {
		this.deptAddr = deptAddr;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getCsysid() {
		return csysid;
	}

	public void setCsysid(Long csysid) {
		this.csysid = csysid;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Long getMsysid() {
		return msysid;
	}

	public void setMsysid(Long msysid) {
		this.msysid = msysid;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPosCode() {
		return posCode;
	}

	public String getHavBuser() {
		return havBuser;
	}

	public void setHavBuser(String havBuser) {
		this.havBuser = havBuser;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}
}