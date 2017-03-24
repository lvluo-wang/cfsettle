package com.upg.cfsettle.mapping.filcs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 基金管理者管理基金信息表
 * @author renzhuolun
 * @date 2016年6月3日 下午3:25:50
 * @version <b>1.0.0</b>
 */

public class FiLcsFundManagerExt implements Serializable {

	/**
	 * 序号
	 */
	private static final long serialVersionUID = 8488170986997523253L;
	private Long id;
	private Long fundManagerId;
	private String fundName;
	private Date startTime;
	private Date endTime;
	private BigDecimal rate;
	private Date ctime;
	private Date mtime;

	// Constructors

	/** default constructor */
	public FiLcsFundManagerExt() {
	}
	
	public FiLcsFundManagerExt(Long fundManagerId) {
		this.fundManagerId = fundManagerId;
	}


	/** full constructor */
	public FiLcsFundManagerExt(Long fundManagerId, String fundName,
			Date startTime, Date endTime, BigDecimal rate, Date ctime,
			Date mtime) {
		this.fundManagerId = fundManagerId;
		this.fundName = fundName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.rate = rate;
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

	public Long getFundManagerId() {
		return this.fundManagerId;
	}

	public void setFundManagerId(Long fundManagerId) {
		this.fundManagerId = fundManagerId;
	}

	public String getFundName() {
		return this.fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
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

	@Override
	public String toString() {
		return "FiLcsFundManagerExt [id=" + id + ", fundManagerId="
				+ fundManagerId + ", fundName=" + fundName + ", startTime="
				+ startTime + ", endTime=" + endTime + ", rate=" + rate
				+ ", ctime=" + ctime + ", mtime=" + mtime + "]";
	}
}