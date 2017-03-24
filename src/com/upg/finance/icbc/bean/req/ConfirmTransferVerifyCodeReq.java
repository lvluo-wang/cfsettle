package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 转让申请-确认手机验证码请求
 * 
 */
@ReqPubConfig("X0402")
@XmlRootElement(name = "req")
public class ConfirmTransferVerifyCodeReq extends BaseRequest {
	/** 用户标识 */
	private String	uid;
	/** 用户托管子账户 */
	@XmlElement(name = "account_no")
	private String	accountNo;
	/** 手机号码 */
	@XmlElement(name = "mobile_no")
	private String	mobileNo;
	/** 转让申请订单号 */
	@XmlElement(name = "order_no")
	private String	orderNo;
	/** 手机验证码 */
	@XmlElement(name = "mobile_validate_code")
	private String	mobileValidateCode;

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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMobileValidateCode() {
		return mobileValidateCode;
	}

	public void setMobileValidateCode(String mobileValidateCode) {
		this.mobileValidateCode = mobileValidateCode;
	}

}
