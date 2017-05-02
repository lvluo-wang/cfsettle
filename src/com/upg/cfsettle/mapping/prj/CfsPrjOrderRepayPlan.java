package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CfsPrjOrderRepayPlan entity. @author MyEclipse Persistence Tools
 */

public class CfsPrjOrderRepayPlan implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 4186857198232275512L;
	private Long id;
	private Byte ptype;
	private Long prjId;
	private Long prjOrderId;
	private Long repayPeriods;
	private Date repayDate;
	private Long countDay;
	private BigDecimal priInterest;
	private BigDecimal principal;
	private BigDecimal yield;
	private BigDecimal restPrincipal;
	private Byte status;
	private Long prjRepayPlanId;
	private Long version;
	private Date ctime;
	private Date mtime;

	//显示所需
	private Integer totalPeriods;
	private Date paybackTime;
	private Integer paybackAuditSysid;
	private String paybackAuditName;
	private Integer repayPeriod;


	// Constructors

	/** default constructor */
	public CfsPrjOrderRepayPlan() {
	}

	/** full constructor */
	public CfsPrjOrderRepayPlan(Byte ptype, Long prjId, Long prjOrderId,
			Long repayPeriods, Date repayDate, BigDecimal priInterest,
			BigDecimal principal, BigDecimal yield, BigDecimal restPrincipal,
			Byte status, Long prjRepayPlanId, Long version,
			Date ctime, Date mtime) {
		this.ptype = ptype;
		this.prjId = prjId;
		this.prjOrderId = prjOrderId;
		this.repayPeriods = repayPeriods;
		this.repayDate = repayDate;
		this.priInterest = priInterest;
		this.principal = principal;
		this.yield = yield;
		this.restPrincipal = restPrincipal;
		this.status = status;
		this.prjRepayPlanId = prjRepayPlanId;
		this.version = version;
		this.ctime = ctime;
		this.mtime = mtime;
	}

	public CfsPrjOrderRepayPlan(Long id, Byte ptype, Long prjId, Long prjOrderId, Long repayPeriods, Date repayDate, BigDecimal priInterest, BigDecimal principal, BigDecimal yield, BigDecimal restPrincipal, Byte status, Long prjRepayPlanId, Long version, Date ctime, Date mtime, Date paybackTime, Integer paybackAuditSysid) {
		this.id = id;
		this.ptype = ptype;
		this.prjId = prjId;
		this.prjOrderId = prjOrderId;
		this.repayPeriods = repayPeriods;
		this.repayDate = repayDate;
		this.priInterest = priInterest;
		this.principal = principal;
		this.yield = yield;
		this.restPrincipal = restPrincipal;
		this.status = status;
		this.prjRepayPlanId = prjRepayPlanId;
		this.version = version;
		this.ctime = ctime;
		this.mtime = mtime;
		this.paybackTime = paybackTime;
		this.paybackAuditSysid = paybackAuditSysid;
	}

	public Integer getRepayPeriod() {
		return repayPeriod;
	}

	public void setRepayPeriod(Integer repayPeriod) {
		this.repayPeriod = repayPeriod;
	}

	public Integer getTotalPeriods() {
		return totalPeriods;
	}

	public void setTotalPeriods(Integer totalPeriods) {
		this.totalPeriods = totalPeriods;
	}

	public String getPaybackAuditName() {
		return paybackAuditName;
	}

	public void setPaybackAuditName(String paybackAuditName) {
		this.paybackAuditName = paybackAuditName;
	}

	public Date getPaybackTime() {
		return paybackTime;
	}

	public void setPaybackTime(Date paybackTime) {
		this.paybackTime = paybackTime;
	}

	public Integer getPaybackAuditSysid() {
		return paybackAuditSysid;
	}

	public void setPaybackAuditSysid(Integer paybackAuditSysid) {
		this.paybackAuditSysid = paybackAuditSysid;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getPtype() {
		return this.ptype;
	}

	public void setPtype(Byte ptype) {
		this.ptype = ptype;
	}

	public Long getPrjId() {
		return this.prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public Long getPrjOrderId() {
		return this.prjOrderId;
	}

	public void setPrjOrderId(Long prjOrderId) {
		this.prjOrderId = prjOrderId;
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

	public Long getPrjRepayPlanId() {
		return this.prjRepayPlanId;
	}

	public void setPrjRepayPlanId(Long prjRepayPlanId) {
		this.prjRepayPlanId = prjRepayPlanId;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
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

	public Long getCountDay() {
		return countDay;
	}

	public void setCountDay(Long countDay) {
		this.countDay = countDay;
	}
}