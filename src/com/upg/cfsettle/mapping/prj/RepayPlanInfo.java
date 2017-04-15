package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.util.Date;

public class RepayPlanInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Byte ptype;
	
	private Date lastRepayDate;
	
	private Date repayDate;
	
	private Long repayPeriods;

	public Byte getPtype() {
		return ptype;
	}

	public void setPtype(Byte ptype) {
		this.ptype = ptype;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public RepayPlanInfo(Byte ptype, Date lastRepayDate,Date repayDate,Long repayPeriods) {
		this.ptype = ptype;
		this.lastRepayDate = lastRepayDate;
		this.repayDate = repayDate;
		this.repayPeriods = repayPeriods;
	}
	
	public RepayPlanInfo() {
	}

	public Long getRepayPeriods() {
		return repayPeriods;
	}

	public void setRepayPeriods(Long repayPeriods) {
		this.repayPeriods = repayPeriods;
	}

	public Date getLastRepayDate() {
		return lastRepayDate;
	}

	public void setLastRepayDate(Date lastRepayDate) {
		this.lastRepayDate = lastRepayDate;
	}
}