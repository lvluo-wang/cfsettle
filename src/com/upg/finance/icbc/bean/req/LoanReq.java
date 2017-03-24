package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 放款交易请求
 * 
 * 
 */
@ReqPubConfig("X0302")
@XmlRootElement(name = "req")
public class LoanReq extends BaseRequest {
	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;
	/** 用款人证件类型 */
	@XmlElement(name = "id_type")
	private String	idType;
	/** 用款人证件类型 */
	@XmlElement(name = "id_no")
	private String	idNo;
	/** 用款人结算账户 */
	@XmlElement(name = "loan_account")
	private String	loanAccount;
	/** 用款人结算账户名称 */
	@XmlElement(name = "loan_account_name")
	private String	loanAccountName;
	/** 放款总金额 */
	@XmlElement(name = "loan_amount")
	private String	loanAmount;
	/** 放款总份额 */
	@XmlElement(name = "loan_lot")
	private String	loanLot;

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
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

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanLot() {
		return loanLot;
	}

	public void setLoanLot(String loanLot) {
		this.loanLot = loanLot;
	}

}
