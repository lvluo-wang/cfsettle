package com.upg.finance.mapping.yrzif;

import java.util.Date;

public class FiPrj {
	private Long	id;
	private Long	uid;
	private String	prjNo;
	private String	prjName;
	private Date	startBidTime;
	private Date	endBidTime;

	private Long	fundAccount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public Date getStartBidTime() {
		return startBidTime;
	}

	public void setStartBidTime(Date startBidTime) {
		this.startBidTime = startBidTime;
	}

	public Date getEndBidTime() {
		return endBidTime;
	}

	public void setEndBidTime(Date endBidTime) {
		this.endBidTime = endBidTime;
	}

	public Long getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(Long fundAccount) {
		this.fundAccount = fundAccount;
	}

}
