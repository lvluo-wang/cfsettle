package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 转让申请-发送手机验证码请求
 * 
 */
@ReqPubConfig("X0401")
@XmlRootElement(name = "req")
public class SendTransferVerifyCodeReq extends BaseRequest {
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
	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;
	/** 转让份额 */
	@XmlElement(name = "trans_lot")
	private String	transLot;
	/** 委托金额 */
	@XmlElement(name = "transAmount")
	private String	transAmount;
	/** 短信发送编号 */
	@XmlElement(name = "msg_sn")
	private String	msgSn;

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

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getTransLot() {
		return transLot;
	}

	public void setTransLot(String transLot) {
		this.transLot = transLot;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getMsgSn() {
		return msgSn;
	}

	public void setMsgSn(String msgSn) {
		this.msgSn = msgSn;
	}

}
