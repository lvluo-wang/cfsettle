package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 银行卡绑定/解除绑定请求
 * 
 * 
 */
@ReqPubConfig("X0602")
@XmlRootElement(name = "req")
public class BindBankCardReq extends BaseRequest {

	/** 用户标识 */
	private String	uid;
	/** 用户托管子账户 */
	@XmlElement(name = "account_no")
	private String	accountNo;
	/** 证件类型 */
	private String	idtype;
	/** 证件号码 */
	private String	idno;

	/** 手机验证码 */
	@XmlElement(name = "mobile_validate_code")
	private String	mobileValidateCode;
	/** 开户行结算账户 */
	@XmlElement(name = "bank_cardno")
	private String	bankCardno;
	/** 操作类型 */
	@XmlElement(name = "opt_type")
	private String	optType;

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

	public String getMobileValidateCode() {
		return mobileValidateCode;
	}

	public void setMobileValidateCode(String mobileValidateCode) {
		this.mobileValidateCode = mobileValidateCode;
	}

	public String getBankCardno() {
		return bankCardno;
	}

	public void setBankCardno(String bankCardno) {
		this.bankCardno = bankCardno;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

}
