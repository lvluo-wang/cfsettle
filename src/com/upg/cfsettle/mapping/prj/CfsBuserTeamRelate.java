package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.util.Date;

/**
 * CfsBuserTeamRelate entity. @author MyEclipse Persistence Tools
 */

public class CfsBuserTeamRelate implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 2512315354900521045L;
	// Fields

	private Long id;
	private Long teamId;
	private Long sysId;
	private Long csysid;
	private Date ctime;

	// Constructors

	/** default constructor */
	public CfsBuserTeamRelate() {
	}

	/** full constructor */
	public CfsBuserTeamRelate(Long teamId, Long sysId, Long csysid,
			Date ctime) {
		this.teamId = teamId;
		this.sysId = sysId;
		this.csysid = csysid;
		this.ctime = ctime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Long getSysId() {
		return this.sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public Long getCsysid() {
		return this.csysid;
	}

	public void setCsysid(Long csysid) {
		this.csysid = csysid;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}