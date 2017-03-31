package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CfsPrjPaybackLog entity. @author MyEclipse Persistence Tools
 */

public class CfsPrjPaybackLog implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -4504412900860145634L;
	private Long id;
	private Long prjId;
	private String prjName;
	private Long prjRepayPlanId;
	private Long paybackTimes;
	private Date paybackTime;
	private BigDecimal paybackAmount;
	private String paybackBankName;
	private String paybackAccountNo;
	private String paybackSerialNum;
	private String remark;
	private Byte status;
	private Long paybackAuditSysid;
	private Date paybackAuditTime;
	private Long csysid;
	private Date ctime;
	private Long msysid;
	private Date mtime;

	// Constructors

	/** default constructor */
	public CfsPrjPaybackLog() {
	}

	/** full constructor */
	public CfsPrjPaybackLog(Long prjId, String prjName,
			Long prjRepayPlanId, Long paybackTimes, Date paybackTime,
			BigDecimal paybackAmount, String paybackBankName,
			String paybackAccountNo, String paybackSerialNum, String remark,
			Byte status, Long paybackAuditSysid, Date paybackAuditTime,
			Long csysid, Date ctime, Long msysid, Date mtime) {
		this.prjId = prjId;
		this.prjName = prjName;
		this.prjRepayPlanId = prjRepayPlanId;
		this.paybackTimes = paybackTimes;
		this.paybackTime = paybackTime;
		this.paybackAmount = paybackAmount;
		this.paybackBankName = paybackBankName;
		this.paybackAccountNo = paybackAccountNo;
		this.paybackSerialNum = paybackSerialNum;
		this.remark = remark;
		this.status = status;
		this.paybackAuditSysid = paybackAuditSysid;
		this.paybackAuditTime = paybackAuditTime;
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

	public Long getPrjRepayPlanId() {
		return this.prjRepayPlanId;
	}

	public void setPrjRepayPlanId(Long prjRepayPlanId) {
		this.prjRepayPlanId = prjRepayPlanId;
	}

	public Long getPaybackTimes() {
		return this.paybackTimes;
	}

	public void setPaybackTimes(Long paybackTimes) {
		this.paybackTimes = paybackTimes;
	}

	public Date getPaybackTime() {
		return this.paybackTime;
	}

	public void setPaybackTime(Date paybackTime) {
		this.paybackTime = paybackTime;
	}

	public BigDecimal getPaybackAmount() {
		return this.paybackAmount;
	}

	public void setPaybackAmount(BigDecimal paybackAmount) {
		this.paybackAmount = paybackAmount;
	}

	public String getPaybackBankName() {
		return this.paybackBankName;
	}

	public void setPaybackBankName(String paybackBankName) {
		this.paybackBankName = paybackBankName;
	}

	public String getPaybackAccountNo() {
		return this.paybackAccountNo;
	}

	public void setPaybackAccountNo(String paybackAccountNo) {
		this.paybackAccountNo = paybackAccountNo;
	}

	public String getPaybackSerialNum() {
		return this.paybackSerialNum;
	}

	public void setPaybackSerialNum(String paybackSerialNum) {
		this.paybackSerialNum = paybackSerialNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getPaybackAuditSysid() {
		return this.paybackAuditSysid;
	}

	public void setPaybackAuditSysid(Long paybackAuditSysid) {
		this.paybackAuditSysid = paybackAuditSysid;
	}

	public Date getPaybackAuditTime() {
		return this.paybackAuditTime;
	}

	public void setPaybackAuditTime(Date paybackAuditTime) {
		this.paybackAuditTime = paybackAuditTime;
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
}