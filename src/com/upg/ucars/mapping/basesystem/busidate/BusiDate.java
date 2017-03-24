package com.upg.ucars.mapping.basesystem.busidate;

import java.util.Date;

/**
 * BusiDate entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BusiDate implements java.io.Serializable {

	
	private static final long serialVersionUID = 7780655951701271393L;
	
	
	
	
	// Fields	
	/** 主键 */
	private Long id;
	/** 当前营业日 */
	private Date curDate;
	/** 上一个营业日 */
	private Date preDate;
	/** 下一个营业日 */
	private Date nextDate;
	/** 系统状态 */
	private String sysStatus;
	/** 接入点编号 */
	private String miNo;

	// Constructors

	/** default constructor */
	public BusiDate() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCurDate() {
		return curDate;
	}

	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}

	public Date getPreDate() {
		return preDate;
	}

	public void setPreDate(Date preDate) {
		this.preDate = preDate;
	}

	public Date getNextDate() {
		return nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}

	public String getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

}