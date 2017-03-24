package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 提现交易请求
 * 
 * 
 */
@ReqPubConfig("X0603")
@XmlRootElement(name = "req")
public class WithdrawCashReq extends BaseRequest {
	/** 用户标识 */
	private String	uid;
	/** 用户托管子账户 */
	@XmlElement(name = "account_no")
	private String	accountNo;
	/** 证件类型 */
	private String	idtype;
	/** 证件号码 */
	private String	idno;
	/** 开户行 */
	@XmlElement(name = "bank_name")
	private String	bankName;
	/** 开户行结算账户 */
	@XmlElement(name = "bank_cardno")
	private String	bankCardno;
	/** 提现金额 */
	@XmlElement(name = "cashout_amount")
	private String	cashoutAmount;

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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardno() {
		return bankCardno;
	}

	public void setBankCardno(String bankCardno) {
		this.bankCardno = bankCardno;
	}

	public String getCashoutAmount() {
		return cashoutAmount;
	}

	public void setCashoutAmount(String cashoutAmount) {
		this.cashoutAmount = cashoutAmount;
	}

}
