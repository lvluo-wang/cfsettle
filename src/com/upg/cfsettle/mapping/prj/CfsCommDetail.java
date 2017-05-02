package com.upg.cfsettle.mapping.prj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CfsCommDetail implements Serializable{

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 7604780518439008120L;
	
	private String contractNo;
	
	private Date investTime;
	
	private String realName;
	
	private String prjName;
	
	private BigDecimal money;
	
	private BigDecimal totalRate;
	
	private BigDecimal areaRate;
	
	private BigDecimal deptRate;
	
	private BigDecimal teamRate;
	
	private BigDecimal sysuserRate;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getInvestTime() {
		return investTime;
	}

	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}

	public BigDecimal getAreaRate() {
		return areaRate;
	}

	public void setAreaRate(BigDecimal areaRate) {
		this.areaRate = areaRate;
	}

	public BigDecimal getDeptRate() {
		return deptRate;
	}

	public void setDeptRate(BigDecimal deptRate) {
		this.deptRate = deptRate;
	}

	public BigDecimal getTeamRate() {
		return teamRate;
	}

	public void setTeamRate(BigDecimal teamRate) {
		this.teamRate = teamRate;
	}

	public BigDecimal getSysuserRate() {
		return sysuserRate;
	}

	public void setSysuserRate(BigDecimal sysuserRate) {
		this.sysuserRate = sysuserRate;
	}
}