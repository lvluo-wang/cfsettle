package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 支付划款请求
 * 
 */
@ReqPubConfig("X0301")
@XmlRootElement(name = "req")
public class PaymentTransferReq extends BaseRequest {

	/** 用户标识 */
	private String	uid;
	/** 用户托管子账户 */
	@XmlElement(name = "account_no")
	private String	accountNo;
	/** 证件类型 */
	private String	idtype;
	/** 证件号码 */
	private String	idno;
	/** 投标订单号 */
	@XmlElement(name = "order_no")
	private String	orderNo;
	/** 支付金额 */
	private String	amount;
	/** 购买份额 */
	private String	lot;
	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;
	/** 用款人结算账户 */
	@XmlElement(name = "loan_account")
	private String	loanAccount;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(String loanAccount) {
		this.loanAccount = loanAccount;
	}

}
