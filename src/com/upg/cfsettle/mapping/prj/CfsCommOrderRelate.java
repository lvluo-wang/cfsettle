package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CfsCommOrderRelate entity. @author MyEclipse Persistence Tools
 */

public class CfsCommOrderRelate implements Serializable {

	// Fields

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -8089191680885668285L;
	private Long id;
	private Long sysid;
	private Long commId;
	private Long prjId;
	private Long prjOrderId;
	private BigDecimal commRate;
	private BigDecimal commAccount;
	private String remark;
	private Byte status;
	private Long csysid;
	private Date ctime;
	private Long msysid;
	private Date mtime;

	// Constructors

	/** default constructor */
	public CfsCommOrderRelate() {
	}

	/** full constructor */
	public CfsCommOrderRelate(Long sysid, Long prjId, Long prjOrderId,
			BigDecimal commRate, BigDecimal commAccount, String remark,
			Byte status, Long csysid, Date ctime, Long msysid,
			Date mtime) {
		this.sysid = sysid;
		this.prjId = prjId;
		this.prjOrderId = prjOrderId;
		this.commRate = commRate;
		this.commAccount = commAccount;
		this.remark = remark;
		this.status = status;
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

	public Long getSysid() {
		return this.sysid;
	}

	public void setSysid(Long sysid) {
		this.sysid = sysid;
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

	public BigDecimal getCommRate() {
		return this.commRate;
	}

	public void setCommRate(BigDecimal commRate) {
		this.commRate = commRate;
	}

	public BigDecimal getCommAccount() {
		return this.commAccount;
	}

	public void setCommAccount(BigDecimal commAccount) {
		this.commAccount = commAccount;
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

	public Long getCommId() {
		return commId;
	}

	public void setCommId(Long commId) {
		this.commId = commId;
	}

	@Override
	public String toString() {
		return "CfsCommOrderRelate{" +
				"id=" + id +
				", sysid=" + sysid +
				", commId=" + commId +
				", prjId=" + prjId +
				", prjOrderId=" + prjOrderId +
				", commRate=" + commRate +
				", commAccount=" + commAccount +
				", remark='" + remark + '\'' +
				", status=" + status +
				", csysid=" + csysid +
				", ctime=" + ctime +
				", msysid=" + msysid +
				", mtime=" + mtime +
				'}';
	}
}