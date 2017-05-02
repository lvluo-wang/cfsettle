package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.util.Date;

/**
 * CfsPrjOrderAuditLog entity. @author MyEclipse Persistence Tools
 */

public class CfsPrjOrderAuditLog implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 8852649765662216728L;
	private Long id;
	private Long prjId;
	private Long prjOrderId;
	private String prjName;
	private String contractNo;
	private Byte status;
	private Long auditSysid;
	private Date auditTime;
	private String remark;
	private Long csysid;
	private Date ctime;
	private Long msysid;
	private Date mtime;

	// Constructors

	/** default constructor */
	public CfsPrjOrderAuditLog() {
	}

	/** full constructor */
	public CfsPrjOrderAuditLog(Long prjId, String prjName,
			String contractNo, Byte status, Long auditSysid,
			Date auditTime, String remark, Long csysid, Date ctime,
			Long msysid, Date mtime) {
		this.prjId = prjId;
		this.prjName = prjName;
		this.contractNo = contractNo;
		this.status = status;
		this.auditSysid = auditSysid;
		this.auditTime = auditTime;
		this.remark = remark;
		this.csysid = csysid;
		this.ctime = ctime;
		this.msysid = msysid;
		this.mtime = mtime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrjId() {
		return this.prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public String getPrjName() {
		return this.prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getAuditSysid() {
		return this.auditSysid;
	}

	public void setAuditSysid(Long auditSysid) {
		this.auditSysid = auditSysid;
	}

	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getMsysid() {
		return this.msysid;
	}

	public void setMsysid(Long msysid) {
		this.msysid = msysid;
	}

	public Date getMtime() {
		return this.mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Long getPrjOrderId() {
		return prjOrderId;
	}

	public void setPrjOrderId(Long prjOrderId) {
		this.prjOrderId = prjOrderId;
	}
}