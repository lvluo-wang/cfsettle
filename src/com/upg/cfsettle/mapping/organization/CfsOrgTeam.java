package com.upg.cfsettle.mapping.organization;
// Generated 2017-3-29 11:24:06 by Hibernate Tools 5.2.1.Final

import java.util.Date;

/**
 * CfsOrgTeam generated by hbm2java
 */
public class CfsOrgTeam implements java.io.Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -7413986776460890299L;
	private Long id;
	private String teamName;
	private Long ownedArea;
	private Long ownedDept;
	private Byte status;
	private Long csysid;
	private Date ctime;
	private Long msysid;
	private Date mtime;

	private String areaName;
	private String deptName;

	public CfsOrgTeam() {
	}

	public CfsOrgTeam(Byte status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Long getOwnedArea() {
		return ownedArea;
	}

	public void setOwnedArea(Long ownedArea) {
		this.ownedArea = ownedArea;
	}

	public Long getOwnedDept() {
		return ownedDept;
	}

	public void setOwnedDept(Long ownedDept) {
		this.ownedDept = ownedDept;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
