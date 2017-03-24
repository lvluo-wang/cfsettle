package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 项目开户请求
 * 
 */
@ReqPubConfig("X0103")
@XmlRootElement(name = "req")
public class OpenProjectAccountReq extends BaseRequest {
	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;
	/** 项目名称 */
	@XmlElement(name = "prj_name")
	private String	prjName;
	/** 项目总份额 */
	@XmlElement(name = "prj_lot")
	private String	prjLot;
	/** 募集起始日期 */
	@XmlElement(name = "start_bid_time")
	private String	startBidTime;
	/** 募集截止日期 */
	@XmlElement(name = "end_bid_time")
	private String	endBidTime;
	/** 放款日期 */
	@XmlElement(name = "start_loan_time")
	private String	startLoanTime;
	/** 用款人类型 */
	@XmlElement(name = "loan_man_type")
	private String	loanManType;
	/** 用款人名称 */
	@XmlElement(name = "loan_man_name")
	private String	loanManName;
	/** 用款人证件类型 */
	@XmlElement(name = "id_type")
	private String	idType;
	/** 用款人证件号码 */
	@XmlElement(name = "id_no")
	private String	idNo;
	/** 用款人结算账户 */
	@XmlElement(name = "loan_account")
	private String	loanAccount;
	/** 用款人结算账户名称 */
	@XmlElement(name = "loan_account_name")
	private String	loanAccountName;
	/** 借款用途 */
	@XmlElement(name = "money_using")
	private String	moneyUsing;

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

	public String getPrjLot() {
		return prjLot;
	}

	public void setPrjLot(String prjLot) {
		this.prjLot = prjLot;
	}

	public String getStartBidTime() {
		return startBidTime;
	}

	public void setStartBidTime(String startBidTime) {
		this.startBidTime = startBidTime;
	}

	public String getEndBidTime() {
		return endBidTime;
	}

	public void setEndBidTime(String endBidTime) {
		this.endBidTime = endBidTime;
	}

	public String getStartLoanTime() {
		return startLoanTime;
	}

	public void setStartLoanTime(String startLoanTime) {
		this.startLoanTime = startLoanTime;
	}

	public String getLoanManType() {
		return loanManType;
	}

	public void setLoanManType(String loanManType) {
		this.loanManType = loanManType;
	}

	public String getLoanManName() {
		return loanManName;
	}

	public void setLoanManName(String loanManName) {
		this.loanManName = loanManName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(String loanAccount) {
		this.loanAccount = loanAccount;
	}

	public String getLoanAccountName() {
		return loanAccountName;
	}

	public void setLoanAccountName(String loanAccountName) {
		this.loanAccountName = loanAccountName;
	}

	public String getMoneyUsing() {
		return moneyUsing;
	}

	public void setMoneyUsing(String moneyUsing) {
		this.moneyUsing = moneyUsing;
	}

}
