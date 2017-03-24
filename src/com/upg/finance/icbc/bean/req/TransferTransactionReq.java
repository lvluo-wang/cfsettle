package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 转让成交请求
 * 
 */
@ReqPubConfig("X0403")
@XmlRootElement(name = "req")
public class TransferTransactionReq extends BaseRequest {
	/** 购买用户标识 */
	@XmlElement(name = "buy_uid")
	private String	buyUid;
	/** 购买用户托管子账户 */
	@XmlElement(name = "buy_account_no")
	private String	buyAccountNo;
	/** 购买证件类型 */
	@XmlElement(name = "buy_idtype")
	private String	buyIdtype;
	/** 购买证件号码 */
	@XmlElement(name = "buy_idno")
	private String	buyIdno;
	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;
	/** 支付金额 */
	private String	amount;
	/** 购买份额 */
	@XmlElement(name = "buy_lot")
	private String	buyLot;
	/** 转让成交订单号 */
	@XmlElement(name = "order_no")
	private String	orderNo;
	/** 划入方用户托管子账户 */
	@XmlElement(name = "in_account_no")
	private String	inAccountNo;
	/** 划入方账户名称 */
	@XmlElement(name = "in_account_name")
	private String	inAccountName;

	public String getBuyUid() {
		return buyUid;
	}

	public void setBuyUid(String buyUid) {
		this.buyUid = buyUid;
	}

	public String getBuyAccountNo() {
		return buyAccountNo;
	}

	public void setBuyAccountNo(String buyAccountNo) {
		this.buyAccountNo = buyAccountNo;
	}

	public String getBuyIdtype() {
		return buyIdtype;
	}

	public void setBuyIdtype(String buyIdtype) {
		this.buyIdtype = buyIdtype;
	}

	public String getBuyIdno() {
		return buyIdno;
	}

	public void setBuyIdno(String buyIdno) {
		this.buyIdno = buyIdno;
	}

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBuyLot() {
		return buyLot;
	}

	public void setBuyLot(String buyLot) {
		this.buyLot = buyLot;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getInAccountNo() {
		return inAccountNo;
	}

	public void setInAccountNo(String inAccountNo) {
		this.inAccountNo = inAccountNo;
	}

	public String getInAccountName() {
		return inAccountName;
	}

	public void setInAccountName(String inAccountName) {
		this.inAccountName = inAccountName;
	}

}
