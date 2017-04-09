package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CfsPrjOrderPaybackLog entity. @author MyEclipse Persistence Tools
 */

public class CfsPrjOrderPaybackLog implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 6168789607985758121L;
	private Long id;
	private Long prjId;
	private String prjName;
	private Long prjOrderId;
	private Long prjOrderRepayPlanId;
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
	
	//辅助查询字段
	private String realName;
	
	private String mobile;
	
	private Byte prjStatus;
	
	private Date startDate;
	
	private Date endDate;
	
	private Byte isPeriod;
	
	

	// Constructors

	/** default constructor */
	public CfsPrjOrderPaybackLog() {
	}

	/** full constructor */
	public CfsPrjOrderPaybackLog(Long prjId, String prjName,
			Long prjOrderId, Long prjOrderRepayPlanId,
			Long paybackTimes, Date paybackTime,
			BigDecimal paybackAmount, String paybackBankName,
			String paybackAccountNo, String paybackSerialNum, String remark,
			Byte status, Long paybackAuditSysid, Date paybackAuditTime,
			Long csysid, Date ctime, Long msysid, Date mtime) {
		this.prjId = prjId;
		this.prjName = prjName;
		this.prjOrderId = prjOrderId;
		this.prjOrderRepayPlanId = prjOrderRepayPlanId;
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

	public Long getPrjOrderId() {
		return this.prjOrderId;
	}

	public void setPrjOrderId(Long prjOrderId) {
		this.prjOrderId = prjOrderId;
	}

	public Long getPrjOrderRepayPlanId() {
		return this.prjOrderRepayPlanId;
	}

	public void setPrjOrderRepayPlanId(Long prjOrderRepayPlanId) {
		this.prjOrderRepayPlanId = prjOrderRepayPlanId;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Byte getPrjStatus() {
		return prjStatus;
	}

	public void setPrjStatus(Byte prjStatus) {
		this.prjStatus = prjStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Byte getIsPeriod() {
		return isPeriod;
	}

	public void setIsPeriod(Byte isPeriod) {
		this.isPeriod = isPeriod;
	}
}