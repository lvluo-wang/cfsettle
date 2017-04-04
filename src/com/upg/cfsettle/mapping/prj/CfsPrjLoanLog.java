package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CfsPrjLoanLog entity. @author MyEclipse Persistence Tools
 */

public class CfsPrjLoanLog implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -7348296545864183010L;
	private Long id;
	private Long prjId;
	private String prjName;
	private Long loanTimes;
	private Date loanTime;
	private BigDecimal loanAmount;
	private String loanBankName;
	private String loanAccountNo;
	private String loanAccountName;
	private String loanSerialNum;
	private String remark;
	private Long csysid;
	private Date ctime;
	private Long msysid;
	private Date mtime;

	// Constructors

	/** default constructor */
	public CfsPrjLoanLog() {
	}

	/** full constructor */
	public CfsPrjLoanLog(Long prjId, String prjName, Long loanTimes,
			Date loanTime, BigDecimal loanAmount, String loanBankName,
			String loanAccountNo, String loanSerialNum, String remark,
			Long csysid, Date ctime, Long msysid, Date mtime) {
		this.prjId = prjId;
		this.prjName = prjName;
		this.loanTimes = loanTimes;
		this.loanTime = loanTime;
		this.loanAmount = loanAmount;
		this.loanBankName = loanBankName;
		this.loanAccountNo = loanAccountNo;
		this.loanSerialNum = loanSerialNum;
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

	public Long getLoanTimes() {
		return this.loanTimes;
	}

	public void setLoanTimes(Long loanTimes) {
		this.loanTimes = loanTimes;
	}

	public Date getLoanTime() {
		return this.loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanBankName() {
		return this.loanBankName;
	}

	public void setLoanBankName(String loanBankName) {
		this.loanBankName = loanBankName;
	}

	public String getLoanAccountNo() {
		return this.loanAccountNo;
	}

	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
	}

	public String getLoanSerialNum() {
		return this.loanSerialNum;
	}

	public void setLoanSerialNum(String loanSerialNum) {
		this.loanSerialNum = loanSerialNum;
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

	public String getLoanAccountName() {
		return loanAccountName;
	}

	public void setLoanAccountName(String loanAccountName) {
		this.loanAccountName = loanAccountName;
	}
}