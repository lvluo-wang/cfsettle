package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用户开户响应 
 *
 */
@XmlRootElement(name="ans")
public class OpenAccountAns extends BaseAnswer {
	/** 用户标识 */
	private String	uid;
	/** 用户托管子账户 */
	@XmlElement(name = "account_no")
	private String	accountNo;
	/** 手机号验证标志 */
	private String	mobileValid;

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

	public String getMobileValid() {
		return mobileValid;
	}

	public void setMobileValid(String mobileValid) {
		this.mobileValid = mobileValid;
	}
}
