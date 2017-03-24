package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 用户开户请求
 * 
 */
@ReqPubConfig("X0101")
@XmlRootElement(name = "req")
public class OpenUserAccountReq extends BaseRequest {
	/** 用户标识 */
	private String	uid;
	/** 用户实名 */
	@XmlElement(name = "real_name")
	private String	realName;
	/** 证件类型 */
	private String	idtype;
	/** 证件号码 */
	private String	idno;
	/** 手机号码 */
	@XmlElement(name = "mobile_no")
	private String	mobileNo;
	/** 提现银行卡号 */
	private String	cashoutBankAccount;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCashoutBankAccount() {
		return cashoutBankAccount;
	}

	public void setCashoutBankAccount(String cashoutBankAccount) {
		this.cashoutBankAccount = cashoutBankAccount;
	}

}
