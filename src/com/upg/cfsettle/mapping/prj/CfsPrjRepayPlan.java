package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CfsPrjRepayPlan entity. @author MyEclipse Persistence Tools
 */

public class CfsPrjRepayPlan implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -3188188350686457544L;
	private Long id;
	private Long prjId;
	private Long repayPeriods;
	private Date repayDate;
	private BigDecimal priInterest;
	private BigDecimal principal;
	private BigDecimal yield;
	private BigDecimal restPrincipal;
	private Byte status;
	private Date ctime;
	private Long csysid;
	private Date mtime;
	private Long msysid;
	
	/**
	 * 辅助查询字段
	 */
	 private String prjName;
	 
	 private Date startDate;
	 
	 private Date endDate;
	 
	 private String queryType;
	// Constructors

	/** default constructor */
	public CfsPrjRepayPlan() {
	}

	/** full constructor */
	public CfsPrjRepayPlan(Long prjId,
			Long repayPeriods, Date repayDate, BigDecimal priInterest,
			BigDecimal principal, BigDecimal yield, BigDecimal restPrincipal,
			Byte status, Date ctime, Date mtime) {
		this.prjId = prjId;
		this.repayPeriods = repayPeriods;
		this.repayDate = repayDate;
		this.priInterest = priInterest;
		this.principal = principal;
		this.yield = yield;
		this.restPrincipal = restPrincipal;
		this.status = status;
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

	public Long getPrjId() {
		return this.prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public Long getRepayPeriods() {
		return this.repayPeriods;
	}

	public void setRepayPeriods(Long repayPeriods) {
		this.repayPeriods = repayPeriods;
	}

	public Date getRepayDate() {
		return this.repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public BigDecimal getPriInterest() {
		return this.priInterest;
	}

	public void setPriInterest(BigDecimal priInterest) {
		this.priInterest = priInterest;
	}

	public BigDecimal getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getYield() {
		return this.yield;
	}

	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}

	public BigDecimal getRestPrincipal() {
		return this.restPrincipal;
	}

	public void setRestPrincipal(BigDecimal restPrincipal) {
		this.restPrincipal = restPrincipal;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Long getCsysid() {
		return this.csysid;
	}

	public void setCsysid(Long csysid) {
		this.csysid = csysid;
	}

	public Date getMtime() {
		return this.mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Long getMsysid() {
		return this.msysid;
	}

	public void setMsysid(Long msysid) {
		this.msysid = msysid;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
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

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
}