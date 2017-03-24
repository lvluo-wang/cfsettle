package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.annotation.ReqPubConfig;

/**
 * 投资收益分配请求
 * 
 * 
 */
@ReqPubConfig("X0502")
@XmlRootElement(name = "req")
public class DistributeInvestmentIncomeReq extends BaseRequest {
	/** 用户标识 */
	private String	uid;
	/** 用户托管子账户 */
	@XmlElement(name = "account_no")
	private String	accountNo;
	/** 证件类型 */
	private String	idtype;
	/** 证件号码 */
	private String	idno;
	/** 项目编号 */
	@XmlElement(name = "prj_no")
	private String	prjNo;
	/** 还款期数 */
	@XmlElement(name = "repay_periods")
	private String	repayPeriods;
	/** 还款金额 */
	@XmlElement(name = "repay_amount")
	private String	repayAmount;
	/** 还款份额 */
	@XmlElement(name = "repay_lot")
	private String	repayLot;
	/** 还款订单号 */
	@XmlElement(name = "order_no")
	private String	orderNo;

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

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public String getRepayPeriods() {
		return repayPeriods;
	}

	public void setRepayPeriods(String repayPeriods) {
		this.repayPeriods = repayPeriods;
	}

	public String getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(String repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getRepayLot() {
		return repayLot;
	}

	public void setRepayLot(String repayLot) {
		this.repayLot = repayLot;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
