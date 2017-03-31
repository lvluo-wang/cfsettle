package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.util.Date;

/**
 * CfsCustBuserRelate entity. @author MyEclipse Persistence Tools
 */

public class CfsCustBuserRelate implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -4589700009139947606L;
	private Long id;
	private Long custId;
	private Long sysId;
	private Long csysid;
	private Date ctime;

	// Constructors

	/** default constructor */
	public CfsCustBuserRelate() {
	}

	/** full constructor */
	public CfsCustBuserRelate(Long custId, Long sysId, Long csysid,
			Date ctime) {
		this.custId = custId;
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

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
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